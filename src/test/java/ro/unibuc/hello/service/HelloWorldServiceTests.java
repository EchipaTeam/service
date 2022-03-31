package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.dto.TaskDTO;

import javax.swing.text.html.parser.Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HelloWorldServiceTest {

    @Mock
    TaskRepository mockTaskRepository;

    @InjectMocks
    HelloWorldService helloWorldService = new HelloWorldService();

    private static final String datePattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
    private final AtomicLong counter = new AtomicLong();

    public TaskEntity createTaskEntity(Date date, String title, String importance, String id) {
        TaskEntity taskEntity = new TaskEntity(title, importance, date);
        taskEntity.id = id;
        return taskEntity;
    }

    @Test
    void testAddTask() {
        try {
            // Arrange

            String title = "Increase code coverage";
            String importance = "foarte";
            Date date = dateFormat.parse("2022-04-12");
            TaskEntity taskEntity = new TaskEntity(title, importance, date);

            // Act
            TaskDTO taskDTO = helloWorldService.addTask(taskEntity);

            // Assert
            Assertions.assertEquals(1, taskDTO.getId());
            Assertions.assertEquals("Increase code coverage", taskDTO.getTitle());
            Assertions.assertEquals("foarte", taskDTO.getImportance());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    void test_listAll_null() throws ParseException {
        List <TaskEntity> taskEntities = new ArrayList<>();

        Date date = dateFormat.parse("2022-04-12");
        TaskEntity  taskEntity = createTaskEntity(date, "Increase code coverage", "foarte",
                "6223117e5af19d7fdeaa4f9e");

        taskEntities.add(taskEntity);
        when(mockTaskRepository.findByImportance(any())).thenReturn(taskEntities);
        when(mockTaskRepository.findAll()).thenReturn(taskEntities);
        when(mockTaskRepository.findByIsDone(any())).thenReturn(taskEntities);


        List<TaskDTO> taskDTOList = helloWorldService.listAll(null, null);
        for (int index = 0; index < taskDTOList.size(); index++) {
            Assertions.assertEquals(taskDTOList.get(index).getIdTask(), taskEntities.get(index).id);
        }

        taskDTOList = helloWorldService.listAll("importance", "high");
        for (int index = 0; index < taskDTOList.size(); index++) {
            Assertions.assertEquals(taskDTOList.get(index).getIdTask(), taskEntities.get(index).id);
        }

        taskDTOList = helloWorldService.listAll("isDone", "false");
        for (int index = 0; index < taskDTOList.size(); index++) {
            Assertions.assertEquals(taskDTOList.get(index).getIdTask(), taskEntities.get(index).id);
        }

        taskDTOList = helloWorldService.listAll("invalid_name", "false");
        Assertions.assertNull(taskDTOList);


    }

    @Test
    void testShowById_invalidId() {
        try {
            TaskDTO taskDTO = helloWorldService.showById(null);
        }
        catch (Exception ex) {
            // Assert
            Assertions.assertEquals("java.lang.NullPointerException", ex.getClass().getName());

        }
    }
    @Test
    void endTask_validId() {
        try {
            Date date = dateFormat.parse("2022-04-12");
            TaskEntity taskEntity =  createTaskEntity(date, "Increase code coverage", "foarte",
                                                                            "6223117e5af19d7fdeaa4f9e");
            when(mockTaskRepository.findById(taskEntity.id)).thenReturn(Optional.of(taskEntity));
            helloWorldService.endTask(taskEntity.id);
            Assertions.assertEquals(true, taskEntity.isDone);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Assertions.fail();
        }
    }
    @Test
    void endTask_invalidId() {
        try {
            TaskDTO taskDTO = helloWorldService.endTask(null);
        }
        catch (Exception ex) {
            // Assert
            Assertions.assertEquals("java.lang.NullPointerException", ex.getClass().getName());
        }
    }

    @Test
    void deleteTask_validId() {
        Date date = null;
        try {
            date = dateFormat.parse("2022-04-12");
            TaskEntity taskEntity =  createTaskEntity(date, "Increase code coverage", "foarte",
                                                "6223117e5af19d7fdeaa4f9e");

            when(mockTaskRepository.findById(taskEntity.id)).thenReturn(Optional.of(taskEntity));

            TaskDTO taskDTO = helloWorldService.deleteTask(taskEntity.id);
            Assertions.assertEquals(taskEntity.title, taskDTO.getTitle());
        } catch (ParseException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    void deleteTask_invalidId() {
        TaskDTO taskDTO = helloWorldService.deleteTask(null);
        Assertions.assertNull(taskDTO);
    }
}