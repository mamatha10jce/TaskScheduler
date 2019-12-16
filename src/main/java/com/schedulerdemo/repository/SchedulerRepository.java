package com.schedulerdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.schedulerdemo.model.SchedulerTask;

/**
 * @author Mamatha Dec 12, 2019 3:10:39 PM
 * SchedulerRepository.java
 */
@Repository
public interface SchedulerRepository extends CrudRepository<SchedulerTask, Long> {

}
