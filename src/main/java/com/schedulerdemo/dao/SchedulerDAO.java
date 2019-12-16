/**
 * @author Mamatha
 * Last Modified on Dec 12, 2019
 */
package com.schedulerdemo.dao;

import com.schedulerdemo.model.SchedulerTask;
import com.schedulerdemo.repository.SchedulerRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mamatha Dec 12, 2019 3:20:32 PM
 * SchedulerDAO.java
 */
@Component
public class SchedulerDAO {

	@Autowired
	private SchedulerRepository schedulerRepo;

	public void save(SchedulerTask schedulerTask) {
		schedulerRepo.save(schedulerTask);
	}

	public List<SchedulerTask> findAll() {
		List<SchedulerTask> taskList = StreamSupport
				.stream(schedulerRepo.findAll().spliterator(), false).collect(Collectors.toList());
		return taskList;
	}
}
