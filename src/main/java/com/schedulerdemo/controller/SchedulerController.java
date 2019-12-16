package com.schedulerdemo.controller;

import com.schedulerdemo.task.scheduler.DataWriterTaskScheduler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.schedulerdemo.dto.SchedulerTaskDto;
import com.schedulerdemo.service.SchedulerService;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author Mamatha Dec 12, 2019 10:36:21 AM SchedulerController.java
 */
@Api(value = "Task Scheduler")
@RestController
@RequestMapping("/api/scheduler")
@Slf4j
public class SchedulerController {

  @Autowired
  private DataWriterTaskScheduler dataWriterTaskScheduler;

  @Autowired
  private SchedulerService schedulerService;

  @GetMapping(value = "load/tasks")
  @ApiOperation(value = "Get All tasks")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "A list of all existing tasks", response = SchedulerTaskDto.class, responseContainer = "List"),
      @ApiResponse(code = 204, message = "No Tasks available")
  })

  public ResponseEntity<List<SchedulerTaskDto>> getAllTasks() {
    List<SchedulerTaskDto> schedulerTaskDtos = schedulerService.findAll();
    if (CollectionUtils.isEmpty(schedulerTaskDtos)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    Comparator<SchedulerTaskDto> dueDatePCComparataor = Comparator
        .comparing(SchedulerTaskDto::getDueDate).thenComparing(SchedulerTaskDto::getPriority);
    Collections.sort(schedulerTaskDtos, dueDatePCComparataor);

    return new ResponseEntity<>(schedulerTaskDtos, HttpStatus.OK);
  }


  @ApiOperation(value = "Get current task asynchronously")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Get current task via event source emitter", response = SchedulerTaskDto.class)
  })
  @GetMapping(value = "load/async/tasks")
  public ResponseEntity<SseEmitter> loadTasksAsync() throws InterruptedException, IOException {
    final SseEmitter emitter = new SseEmitter();
    dataWriterTaskScheduler.add(emitter);
    handleCallbacks(emitter);
    return new ResponseEntity<>(emitter, HttpStatus.OK);
  }

  @GetMapping(value = "stop/async/tasks")
  public ResponseEntity<Void> stopTaskAsync() {
    dataWriterTaskScheduler.removeAll();
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  private void handleCallbacks(SseEmitter sseEmitter) {
    sseEmitter.onError((throwable) -> {
     log.error("Error while sending task", throwable);
    });
    sseEmitter.onCompletion(() -> {
      dataWriterTaskScheduler.remove(sseEmitter);
      log.debug("Event emitter completed");
    });
    sseEmitter.onTimeout(() -> {
      dataWriterTaskScheduler.remove(sseEmitter);
      log.debug("Event emitter timeout");
    });
  }
}
