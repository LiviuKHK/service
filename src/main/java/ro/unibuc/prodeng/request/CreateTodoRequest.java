package ro.unibuc.prodeng.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(

    @NotBlank(message = "Subject is required")
    String subject,

    @NotBlank(message = "Task name is required")
    String taskName,

    @NotBlank(message = "Description is required")
    String description,

    @NotBlank(message = "Deadline is required")
    String deadline,

    @Email(message = "Invalid email format")
    @NotBlank(message = "Assignee email is required")
    String assigneeEmail
) {}