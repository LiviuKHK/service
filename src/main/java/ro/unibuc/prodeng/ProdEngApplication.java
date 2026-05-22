package ro.unibuc.prodeng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.CreateUserRequest;
import ro.unibuc.prodeng.service.TodoService;
import ro.unibuc.prodeng.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories
public class ProdEngApplication {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProdEngApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        if (userRepository.findByEmail("frodo@shire.me").isEmpty()) {

            CreateUserRequest userRequest =
                    new CreateUserRequest("Frodo Baggins", "frodo@shire.me");

            userService.createUser(userRequest);

            CreateTodoRequest todoRequest =
                    new CreateTodoRequest(
                            "Rings",
                            "Take the ring to Mordor",
                            "Deliver the ring safely to Mount Doom",
                            "2026-06-01",
                            "frodo@shire.me"
                    );

            todoService.createTodo(todoRequest);
        }
    }
}