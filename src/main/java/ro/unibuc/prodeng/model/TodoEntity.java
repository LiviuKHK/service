package ro.unibuc.prodeng.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todos")
public record TodoEntity(
    @Id String id,
    String subject,
    String taskName,
    String description,
    String deadline,
    boolean done,
    String assignedUserId
) {}
