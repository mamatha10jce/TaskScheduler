/**
 * @author Mamatha Shivanna
 * Last Modified on Dec 12, 2019
 */
package com.schedulerdemo.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mamatha Dec 10, 2019 3:06:20 PM
 * SchedulerTask.java
 */

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"uuid", "title", "description",  "status", "createdAt", "updatedAt",
		"resolvedAt", "dueDate", "priority"})
public class SchedulerTaskDto {

	private UUID uuid;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	private LocalDate dueDate;

	private LocalDate resolvedAt;

	private String title;

	private String description;

	private int priority;

	private String status;

}
