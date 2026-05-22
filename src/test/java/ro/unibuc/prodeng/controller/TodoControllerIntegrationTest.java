package ro.unibuc.prodeng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.prodeng.IntegrationTestBase;
import ro.unibuc.prodeng.repository.TodoRepository;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.CreateUserRequest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TodoController Integration Tests")
class TodoControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void createUser(String name, String email) throws Exception {
        CreateUserRequest request = new CreateUserRequest(name, email);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    private String createTodo(String subject, String taskName, String description, String deadline, String email) throws Exception {

        CreateTodoRequest request =
                new CreateTodoRequest(subject, taskName, description, deadline, email);

        String response = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.done").value(false))
                .andExpect(jsonPath("$.assigneeEmail").value(email))
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("id").asText();
    }

    @Test
    void testCreateAndGetTodo_validTodoCreation_retrievesTodoSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");

        String todoId = createTodo(
                "Study",
                "Spring Boot",
                "Buy milk",
                "2026-06-10",
                "alice@example.com"
        );

        mockMvc.perform(get("/api/todos/" + todoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Buy milk"))
                .andExpect(jsonPath("$.done").value(false))
                .andExpect(jsonPath("$.assigneeName").value("Alice"))
                .andExpect(jsonPath("$.assigneeEmail").value("alice@example.com"));
    }

    @Test
    void testGetTodosByUser_multipleUsersWithDifferentTodos_filtersCorrectly() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");

        createTodo("S1","T1","Buy milk","2026-06-10","alice@example.com");
        createTodo("S2","T2","Walk dog","2026-06-10","alice@example.com");
        createTodo("S3","T3","Clean house","2026-06-10","bob@example.com");

        mockMvc.perform(get("/api/todos").param("assigneeEmail", "alice@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/api/todos").param("assigneeEmail", "bob@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testSetDone_toggleDoneStatus_updatesStatusCorrectly() throws Exception {
        createUser("Alice", "alice@example.com");
        String todoId = createTodo("S","T","Buy milk","2026-06-10","alice@example.com");

        mockMvc.perform(patch("/api/todos/" + todoId + "/done")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void testAssign_reassignToDifferentUser_updateAssigneeSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");

        String todoId = createTodo("S","T","Buy milk","2026-06-10","alice@example.com");

        mockMvc.perform(patch("/api/todos/" + todoId + "/assignee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newAssigneeEmail\":\"bob@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assigneeName").value("Bob"))
                .andExpect(jsonPath("$.assigneeEmail").value("bob@example.com"));
    }

    @Test
    void testEditDescription_validNewDescription_updatesDescriptionSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");

        String todoId = createTodo("S","T","Buy milk","2026-06-10","alice@example.com");

        mockMvc.perform(patch("/api/todos/" + todoId + "/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Buy oat milk\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Buy oat milk"));
    }

    @Test
    void testDeleteTodo_existingTodo_deletesSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");

        String todoId = createTodo("S","T","Buy milk","2026-06-10","alice@example.com");

        mockMvc.perform(delete("/api/todos/" + todoId))
                .andExpect(status().isNoContent());
    }
}