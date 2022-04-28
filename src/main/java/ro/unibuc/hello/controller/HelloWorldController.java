package ro.unibuc.hello.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.dto.TaskDTO;
import ro.unibuc.hello.service.HelloWorldService;

@Controller
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @Autowired
    MeterRegistry metricsRegistry;
    private final AtomicLong counter = new AtomicLong();
    Logger logger = Logger.getLogger(String.valueOf(HelloWorldController.class));

    @GetMapping("/tasks")
    @ResponseBody
    @Timed(value = "hello.tasks.time", description = "Time taken to return tasks")
    @Counted(value = "hello.tasks.count", description = "Times tasks was returned")
    public ResponseEntity<List<TaskDTO>> listAll(@RequestParam(required = false, name = "search-by") String search,
                                                 @RequestParam(required = false, name = "value") String value) {

        metricsRegistry.counter("my_non_aop_metric", "endpoint", "hello").increment(counter.incrementAndGet());
        List<TaskDTO> list = helloWorldService.listAll(search, value);
        if (list != null)
        {
            logger.info("List of Tasks was returned!");
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        else
        {
            logger.info("List of Tasks is empty!");
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/task")
    @ResponseBody
    @Timed(value = "hello.task.time", description = "Time taken to return task")
    @Counted(value = "hello.task.count", description = "Times task was returned")
    public ResponseEntity<TaskDTO> showById(String id) {

        TaskDTO entity = helloWorldService.showById(id);
        if (entity == null)
        {
            logger.info("Task by id was returned!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            logger.warning("Task by id was not found!");
            return new ResponseEntity<>(entity , HttpStatus.OK);
        }
    }

    @PostMapping("/task")
    @ResponseBody
    @Timed(value = "hello.addtask.time", description = "Time taken to add task")
    @Counted(value = "hello.addtask.count", description = "Times task was added")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskEntity taskEntity) {
        TaskDTO taskDTO = helloWorldService.addTask(taskEntity);
        logger.info("Task was created!");
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @PutMapping("/task")
    @ResponseBody
    @Timed(value = "hello.updatetask.time", description = "Time taken to modifies task")
    @Counted(value = "hello.updatetask.count", description = "Times task was modified")
    public ResponseEntity<TaskDTO> endTask(String id) {
        TaskDTO entity = helloWorldService.endTask(id);
        if (entity == null)
        {
            logger.warning("Task was not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            logger.info("Task was modified!");
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
    }

    @DeleteMapping("/task")
    @ResponseBody
    @Timed(value = "hello.deletetask.time", description = "Time taken to delete task")
    @Counted(value = "hello.deletetask.count", description = "Times task was deleted")
    public ResponseEntity<TaskDTO> deleteTask(String id) {
        TaskDTO taskDTO = helloWorldService.deleteTask(id);
        if (taskDTO == null)
        {
            logger.warning("Task was not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            logger.info("Task was deleted!");
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        }
    }
}
