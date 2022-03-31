package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.dto.TaskDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class HelloWorldService {

    private static final String datePattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    @Autowired
    private TaskRepository taskRepository;

    private final AtomicLong counter = new AtomicLong();
    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";

    public TaskDTO hello(String name, String idTask, Date dueDate, String title, Boolean isDone, String importance) {
        return new TaskDTO(counter.incrementAndGet(),idTask,dueDate,title,isDone,importance);
    }

    public TaskDTO buildGreetingFromInfo(String title) throws EntityNotFoundException {
        TaskEntity entity = taskRepository.findByTitle(title);
        if (entity == null) {
            throw new EntityNotFoundException(title);
        }
        return new TaskDTO(counter.incrementAndGet(), entity);
    }

    public List<TaskDTO> listAll(String search, String value){
        List<TaskDTO> entityList;
        if (search == null) {
            entityList = taskRepository.findAll().stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                    collect(Collectors.toList());
            return entityList;
        }

        switch (search)
        {
            case "importance":
                entityList = taskRepository.findByImportance(value).stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                        collect(Collectors.toList());
                return entityList;

            case "date":
                Date tmpDate;
                try {
                    tmpDate = dateFormat.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }

                entityList = taskRepository.findByDueDate(tmpDate).stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                        collect(Collectors.toList());
                return entityList;
            case "isDone":
                entityList = taskRepository.findByIsDone(Boolean.parseBoolean(value)).stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                        collect(Collectors.toList());
                return entityList;
        }
        return null;
    }
}
