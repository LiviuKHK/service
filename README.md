# StudyPlanner

## Team

- **Student:** Andrei Liviu-Cristian

### Responsibilities

- User Management Module (create, update and view users)
- Study Task Management Module (CRUD tasks, assign tasks, mark tasks as completed)
- Statistics Module (task completion statistics and subject-based filtering)

---

## Project Description

StudyPlanner is a SaaS application designed to help students organize their academic activities and track their learning progress.

Users can create study tasks associated with different subjects, set deadlines, assign tasks to users, and monitor completion status. The system provides statistics regarding completed and pending tasks, helping students manage their workload more efficiently.

The application is inspired by productivity and task-management platforms, offering a simplified educational version suitable for academic purposes.

---

## Key Features

### User Management

- Create users
- Update user information
- View user profiles

### Study Task Management

- Create study tasks
- Edit study tasks
- Delete study tasks
- Assign tasks to users
- Mark tasks as completed
- View all tasks

### Subject Organization

- Associate tasks with subjects
- Search tasks by subject
- Filter completed and pending tasks

### Statistics

- Total number of tasks
- Completed tasks
- Pending tasks
- Completion rate calculation

---

## Technical Stack

- **Backend:** Spring Boot (Java 21)
- **Database:** MongoDB
- **API:** RESTful
- **Testing:** JUnit, Mockito, Cucumber
- **Monitoring:** Prometheus, Grafana
- **Deployment:** Docker

---

## Project Structure

### User Module

Handles all user-related operations:

- Create user
- Update user details
- Retrieve user information

### Study Task Module

Handles study task management:

- Create task
- Edit task
- Delete task
- Assign task to a user
- Mark task as completed
- Retrieve task information

### Statistics Module

Provides productivity insights:

- Total tasks
- Completed tasks
- Pending tasks
- Completion percentage

---

## Example Study Task

| Field | Example |
|---------|---------|
| Subject | Databases |
| Task Name | MongoDB Project |
| Description | Implement CRUD operations |
| Deadline | 2026-06-10 |
| Status | In Progress |

---

## API Examples

### Create Study Task

```http
POST /api/tasks
```

Request Body:

```json
{
  "subject": "Databases",
  "taskName": "MongoDB Project",
  "description": "Implement CRUD operations",
  "deadline": "2026-06-10"
}
```

### Get Statistics

```http
GET /api/tasks/stats
```

Response:

```json
{
  "totalTasks": 20,
  "completedTasks": 15,
  "pendingTasks": 5,
  "completionRate": 75
}
```

---

## Contributing

As a solo developer, the project follows a simplified trunk-based development workflow:

1. Create a feature branch from `main`
2. Implement the feature
3. Write or update tests
4. Commit changes with clear messages
5. Merge into `main`

---

## Prerequisites

For using GitHub Codespaces, no prerequisites are mandatory.

Follow the `./PREREQUISITES.md` instructions to configure a local environment with:

- Ubuntu
- Docker
- IntelliJ IDEA

---

## Future Improvements

- Authentication and authorization
- Study session tracking
- Calendar integration
- Email reminders
- Dashboard with visual statistics
- Subject performance analytics