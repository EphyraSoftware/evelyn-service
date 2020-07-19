package org.evelyn.services.task.web;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = {WebAppTests.Initializer.class})
class WebAppTests {
  @Container
  public static final KeycloakContainer keycloak = new KeycloakContainer()
          .withRealmImportFile("test-realm.json");
  @Container
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer();
  @Autowired
  private WebTestClient webTestClient;

  @BeforeAll
  public static void keycloakConfiguration() {
    Keycloak keycloakAdminClient = KeycloakBuilder.builder()
            .serverUrl(keycloak.getAuthServerUrl())
            .realm("master")
            .clientId("admin-cli")
            .username(keycloak.getAdminUsername())
            .password(keycloak.getAdminPassword())
            .build();

    var passwordCred = new CredentialRepresentation();
    passwordCred.setTemporary(false);
    passwordCred.setType(CredentialRepresentation.PASSWORD);
    passwordCred.setValue("test");

    var userRepresentation = new UserRepresentation();
    userRepresentation.setEnabled(true);
    userRepresentation.setUsername("evelyn");
    userRepresentation.setEmail("evelyn@evelyn.org");
    userRepresentation.setEmailVerified(true);
    userRepresentation.setCredentials(Collections.singletonList(passwordCred));
    keycloakAdminClient.realm("test").users().create(userRepresentation);

    ClientRepresentation clientRepresentation = new ClientRepresentation();
    clientRepresentation.setEnabled(true);
    clientRepresentation.setClientId("test");
    clientRepresentation.setProtocol("openid-connect");
    clientRepresentation.setBaseUrl("http://localhost:8080");
    clientRepresentation.setClientAuthenticatorType("client-secret");
    clientRepresentation.setPublicClient(false);
    clientRepresentation.setSecret("test");
    keycloakAdminClient.realm("test").clients().create(clientRepresentation);
  }

  @AfterEach
  public void clearMongo() {
    MongoClient mongoClient = MongoClients.create(mongoDBContainer.getReplicaSetUrl());
    mongoClient.getDatabase("evelyn").getCollection("taskModel").drop();
  }

  @Test
  public void getTasksReturnsEmptyList() {
    var authToken = getAuthToken();

    webTestClient
            .get()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(Task.class).hasSize(0);
  }

  @Test
  public void createAndGetTasks() {
    var authToken = getAuthToken();

    var task1 = new CreateTaskRequest();
    task1.setTitle("a task");
    task1.setDescription("something to do");

    webTestClient
            .post()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .body(Mono.just(task1), CreateTaskRequest.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful();

    var task2 = new CreateTaskRequest();
    task2.setTitle("a task 2");
    task2.setDescription("something to do 2");

    webTestClient
            .post()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .body(Mono.just(task2), CreateTaskRequest.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful();

    webTestClient
            .get()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(Task.class)
            .hasSize(2)
            .consumeWith(listEntityExchangeResult -> {
              var response = listEntityExchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(task1.getTitle(), response.get(0).getTitle());
              assertEquals(task1.getDescription(), response.get(0).getDescription());
              assertEquals(task2.getTitle(), response.get(1).getTitle());
              assertEquals(task2.getDescription(), response.get(1).getDescription());
            });
  }

  @Test
  public void createTasksAndDeleteOne() {
    var authToken = getAuthToken();

    var task1 = new CreateTaskRequest();
    task1.setTitle("a task");
    task1.setDescription("something to do");

    webTestClient
            .post()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .body(Mono.just(task1), CreateTaskRequest.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful();

    var task2 = new CreateTaskRequest();
    task2.setTitle("a task 2");
    task2.setDescription("something to do 2");

    AtomicReference<String> task2Id = new AtomicReference<>();
    webTestClient
            .post()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .body(Mono.just(task2), CreateTaskRequest.class)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Task.class)
            .consumeWith(taskEntityExchangeResult -> {
              var response = taskEntityExchangeResult.getResponseBody();
              assertNotNull(response);
              task2Id.set(response.getTaskId());
            });

    webTestClient
            .delete()
            .uri("/tasks/{tasKId}", task2Id.get())
            .header("Authorization", String.format("Bearer %s", authToken))
            .exchange()
            .expectStatus().is2xxSuccessful();

    webTestClient
            .get()
            .uri("/tasks")
            .header("Authorization", String.format("Bearer %s", authToken))
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(Task.class)
            .hasSize(1)
            .consumeWith(listEntityExchangeResult -> {
              var response = listEntityExchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(task1.getTitle(), response.get(0).getTitle());
              assertEquals(task1.getDescription(), response.get(0).getDescription());
            });
  }

  private String getAuthToken() {
    var webClient = WebClient.builder()
            .baseUrl(keycloak.getAuthServerUrl())
            .build();

    var response = webClient
            .post()
            .uri("/realms/test/protocol/openid-connect/token")
            .body(BodyInserters
                    .fromFormData("client_id", "test")
                    .with("grant_type", "password")
                    .with("client_secret", "test")
                    .with("scope", "openid email")
                    .with("username", "evelyn")
                    .with("password", "test")
            )
            .exchange()
            .flatMap(clientResponse -> {
              if (clientResponse.statusCode().is4xxClientError()) {
                clientResponse.body((inputMessage, context) -> inputMessage.getBody());
              }
              return clientResponse.bodyToMono(AuthResponse.class);
            })
            .block();

    assertNotNull(response);
    assertNull(response.getError());
    return response.getAuthToken();
  }

  static class Initializer
          implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
              "spring.profiles.active=test",
              String.format("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:%s/auth/realms/test", keycloak.getHttpPort()),
              String.format("org.evelyn.task.mongo-connection-string=%s", mongoDBContainer.getReplicaSetUrl())
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }
}
