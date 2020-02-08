import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.evelyn.services.todo.api.model.CreateTodoRequest;
import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.web.WebApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WebApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {
  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  private Gson gson = new Gson();

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
  }

  @WithMockUser("test-user")
  @Test
  public void createAndGetTodo() throws Exception {
    var request = new CreateTodoRequest();
    request.setName("test todo");

    MvcResult mvcResult = mvc.perform(post("/todos").content(gson.toJson(request)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    Todo created = gson.fromJson(mvcResult.getResponse().getContentAsString(), Todo.class);
    assertEquals(request.getName(), created.getName());

    var createdId = created.getId();
    assertNotNull(createdId);

    MvcResult getResult = mvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    Type listType = new TypeToken<List<Todo>>() {
    }.getType();
    List<Todo> lookedUp = gson.fromJson(getResult.getResponse().getContentAsString(), listType);

    List<Todo> todoList = lookedUp.stream().filter(todo -> createdId.equals(todo.getId())).collect(Collectors.toList());
    assertEquals(1, todoList.size());
    assertEquals(request.getName(), todoList.get(0).getName());
  }
}
