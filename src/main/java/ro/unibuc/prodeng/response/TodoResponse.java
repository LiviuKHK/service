package ro.unibuc.prodeng.response;

public record TodoResponse(
    String id,
    String subject,
    String taskName,
    String description,
    String deadline,
    boolean done,
    String assigneeName,
    String assigneeEmail
) {}