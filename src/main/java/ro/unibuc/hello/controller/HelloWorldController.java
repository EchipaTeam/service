package ro.unibuc.hello.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.dto.TaskDTO;
import ro.unibuc.hello.service.HelloWorldService;

@Controller
public class HelloWorldController {

    @Autowired
    private TaskRepository taskRepository;
    private HelloWorldService helloWorldService;

    private static final String datePattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/tasks")
    @ResponseBody
    public ResponseEntity<List<TaskDTO>> listAll(@RequestParam(required = false, name = "search-by") String search,
                                                 @RequestParam(required = false, name = "value") String value) {
        List<TaskDTO> list = helloWorldService.listAll(search, value);
        if (list != null)
        {
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        else return new ResponseEntity<> (null,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> showById(String id) {

        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(new TaskDTO(counter.incrementAndGet(), entity), HttpStatus.OK);
        }
    }

    @PostMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskEntity taskEntity) {
        taskEntity.isDone = false;
        taskRepository.save(taskEntity);
        TaskDTO taskDTO = new TaskDTO(counter.incrementAndGet(), taskEntity);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @PutMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> endTask(String id) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            entity.isDone = true;
            taskRepository.save(entity);
            return new ResponseEntity<>(new TaskDTO(counter.incrementAndGet(), entity), HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> deleteTask(String id) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            taskRepository.delete(entity);
            return new ResponseEntity<>(new TaskDTO(counter.incrementAndGet(), entity), HttpStatus.OK);
        }
    }
}
