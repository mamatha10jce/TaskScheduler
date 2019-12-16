package com.schedulerdemo.task.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schedulerdemo.dto.SchedulerTaskDto;
import com.schedulerdemo.model.SchedulerTask;
import com.schedulerdemo.dto.SchedulerTaskDto;
import com.schedulerdemo.model.SchedulerTask;
import com.schedulerdemo.service.SchedulerService;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
public class DataWriterTaskScheduler {

  @Autowired
  private SchedulerService schedulerService;

  @Autowired
  private ObjectMapper jackson2ObjectMapper;

  private List<SseEmitter> sseEmitters = new CopyOnWriteArrayList<>();

  public void add(SseEmitter sseEmitter) {
    sseEmitters.add(sseEmitter);
  }

  public void remove(SseEmitter sseEmitter) {
    sseEmitters.remove(sseEmitter);
  }

  public void removeAll() {
    sseEmitters.forEach(sseEmitter ->  {
      sseEmitter.complete();
    });
    sseEmitters = new CopyOnWriteArrayList<>();
  }

  @Scheduled(fixedDelayString = "#{${scheduler.task.fixedDelayString}}")
  public void recordCreatorTask() {
    try {
      if (!CollectionUtils.isEmpty(sseEmitters)) {
        sseEmitters.forEach(sseEmitter -> {
          try {
            sseEmitter.send(jackson2ObjectMapper.writeValueAsString(createSchedulerTask()));
          } catch (Exception e) {
          //  log.error("Exception while sending data", e);
            sseEmitter.completeWithError(e);
          }
        });
      } else {
        createSchedulerTask();
      }
    } catch (Exception e) {
     // log.error("Problem in emitting the event", e);
    }
  }

  private SchedulerTaskDto createSchedulerTask() {

    SchedulerTask schedulerTask = new SchedulerTask();
    schedulerTask.setCreatedAt(LocalDate.now());

    int randomNo = generateRandom(100, 1);
    schedulerTask.setDescription("Test Task Description-" + randomNo);
    schedulerTask.setPriority(randomNo);

    int randomDaysToAdd = generateRandom(10, 1);
    schedulerTask.setResolvedAt(LocalDate.now().plusDays(randomDaysToAdd));
    schedulerTask.setUuid(UUID.randomUUID());

    schedulerTask.setTitle("Test Task-" + randomNo);

    schedulerTask.setStatus("RESOLVED");
    randomDaysToAdd = generateRandom(10, 1);
    schedulerTask.setDueDate(LocalDate.now().plusDays(randomDaysToAdd));

    schedulerTask.setUpdatedAt(LocalDate.now().plusDays(randomDaysToAdd));

    log.info("Scheduled Task : {}", schedulerTask);
    schedulerService.save(schedulerTask);

    return schedulerService.mapToSchedulerTaskDTO(schedulerTask);
  }

  private int generateRandom(int max, int min) {
    Random rand = new Random();
    return rand.nextInt((max - min) + 1) + min;
  }
}
