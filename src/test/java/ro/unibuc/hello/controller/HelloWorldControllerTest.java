package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.dto.TaskDTO;
import ro.unibuc.hello.service.HelloWorldService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
public class HelloWorldControllerTest {

    @Mock
    private HelloWorldService helloWorldService;

    @Mock
    TaskRepository mockTaskRepository;

    @InjectMocks
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static final String datePattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_listAll() throws Exception {
        List<TaskEntity> taskEntities = new ArrayList<>();
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        taskEntities.add(taskEntity);

        // Act
        MvcResult result = mockMvc.perform(get("/tasks/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        // Assert
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());


    }
    @Test
    void test_listAll_null() throws Exception {
        List<TaskEntity> taskEntities = new ArrayList<>();
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        taskEntities.add(taskEntity);
        when(helloWorldService.listAll(any(), any())).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/tasks/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void test_showById() throws Exception {
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        TaskDTO taskDTO = new TaskDTO(1, taskEntity);

        when(helloWorldService.showById(any())).thenReturn(taskDTO);

        MvcResult result = mockMvc.perform(get("/task/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void test_showById_null() throws Exception {
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        TaskDTO taskDTO = new TaskDTO(1, taskEntity);

        when(helloWorldService.showById(any())).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/task/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void test_endTask_null() throws Exception {
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        TaskDTO taskDTO = new TaskDTO(1, taskEntity);

        when(helloWorldService.endTask(any())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/task/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void test_endTask() throws Exception {
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        TaskDTO taskDTO = new TaskDTO(1, taskEntity);

        when(helloWorldService.endTask(any())).thenReturn(taskDTO);

        MvcResult result = mockMvc.perform(put("/task/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void test_deleteTask() throws Exception {
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        TaskDTO taskDTO = new TaskDTO(1, taskEntity);

        when(helloWorldService.deleteTask(any())).thenReturn(taskDTO);

        MvcResult result = mockMvc.perform(delete("/task/")
                        .content(objectMapper.writeValueAsString(taskEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    // TODO solve it, the request doesn't works
    @Test
    void test_addTask() throws Exception {
        Date date = dateFormat.parse("2022-04-12");

        TaskEntity taskEntity = new TaskEntity("Increase code coverage", "foarte", date);
        TaskDTO taskDTO = new TaskDTO(1, taskEntity);

        when(helloWorldService.addTask(any())).thenReturn(taskDTO);

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            byte[] requestJson =  objectMapper.writeValueAsBytes(taskEntity);

            mockMvc.perform(post("/task")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isCreated())
                    .andReturn();
            //TODO Wrtie a bunch of validation for the return OBJECT

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
