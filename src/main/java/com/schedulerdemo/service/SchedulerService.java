package com.schedulerdemo.service;

import com.schedulerdemo.dao.SchedulerDAO;
import com.schedulerdemo.dto.SchedulerTaskDto;
import com.schedulerdemo.model.SchedulerTask;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mamatha Dec 12, 2019 3:19:53 PM ExchangeService.java
 */
@Service
public class SchedulerService {

  @Autowired
  private SchedulerDAO scheduleDAO;

  private ModelMapper modelMapper = new ModelMapper();

  public List<SchedulerTaskDto> findAll() {
    List<SchedulerTaskDto> schedulerTaskDtos = scheduleDAO.findAll().stream()
        .map(schedulerTask -> mapToSchedulerTaskDTO(schedulerTask)).collect(
            Collectors.toList());
    return schedulerTaskDtos;
  }

  public void save(SchedulerTask schedulerTask) {
    scheduleDAO.save(schedulerTask);
  }

  public SchedulerTaskDto mapToSchedulerTaskDTO(SchedulerTask schedulerTask) {
    return modelMapper.map(schedulerTask, SchedulerTaskDto.class);
  }
}
