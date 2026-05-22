package ro.unibuc.prodeng.request;

import jakarta.validation.constraints.NotBlank;

public record EditTodoRequest(

    @NotBlank(message = "Subject is required")
    String subject,

    @NotBlank(message = "Task name is required")
    String taskName,

    @NotBlank(message = "Description is required")
    String description,

    @NotBlank(message = "Deadline is required")
    String deadline
) {}