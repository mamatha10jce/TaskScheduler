# TaskScheduler
Create app to Schedule task asynchronously  using Spring Boot API

Goal

Implement the backend for a simple application using the technologies listed above. The result should be a docker-compose file linking to the correct
images. In addition, the source code should be located in a git repo, and accessible for us.
The application simulates a simple Task Management System. Incoming tasks are pushed to a queue for further processing. In addition, tasks can be
postponed with a "remind me at" date for later.

Backend - Challenge

The backend application should implement the following features:
A scheduler should create new tasks at a random interval
Tasks should be persisted in the database and should have, at least, the following fields
    id (uuid)
    createdAt
    updatedAt
    dueDate
    resolvedAt
    title
    description
    priority
    status
    
REST Endpoints for communication with the Frontend
For communication with the frontend, DTOs should be used

Frontend - Challenge

The candidate is asked to create a quick-and-dirty frontend to showcase the task list loading, update, postpone and deletion. Graphics, design and
usability won't affect the overall challenge rating at all.

A pure HTML + Javascript will be enough, but feel free to add any js library which helps you. Only constraints are:
Load the tasks from the backend asynchronously and list them according to their dueDate and the priority (but ordering must be done on backend)
If new tasks come in, they should be automatically added to the list of tasks without the need to manually refresh the page (yes, this is pretty
much another backend requirement :)

Setup & Deliveries

    The frontend part can be served via the backend application, also the code can be located in the same repository.
    Feel free to add any needed dependencies
    Deliver the solution as source code located in a git repository. The Readme must contain at least:
    Building instructions and docker instructions to start up the entire system
    API documentation
    Screenshots of the frontend
  
Solution:
git clone https://github.com/mamatha10jce/TaskScheduler.git

cd into <folder name>
    
mvn spring-boot:run

mvn package && java -jar target/SchedulerApi.jar

Open Browser and paste this url
http://localhost:8888/index.html

Ouput:
Attached output image file.
Dockerfile has created to dockerise the application and run in local system.




