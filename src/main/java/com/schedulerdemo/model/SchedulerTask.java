package com.schedulerdemo.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author Mamatha Dec 12, 2019 3:06:20 PM
 * SchedulerTask.java
 */
@Entity
@Table(name="TASKS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchedulerTask {
	@Id
	private UUID uuid;

	@Column(name = "createdAt")
	private LocalDate createdAt;

	@Column(name = "updatedAt")
	private LocalDate updatedAt;

	@Column(name = "dueDate")
	private LocalDate dueDate;

	@Column(name = "resolvedAt")
	private LocalDate resolvedAt;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "priority")
	private Integer priority;

	@Column(name = "status")
	private String status;

}
