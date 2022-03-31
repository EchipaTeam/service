package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTests {
    private static final String datePattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    TaskDTO myTask;

    public TaskTests() throws ParseException {
        myTask = new TaskDTO(1,"64353dsdstasd", dateFormat.parse("2022-03-31"),"Task",false,"foarte");
    }

    @Test
    void test_id(){
        Assertions.assertEquals(1,myTask.getId());
    }
    @Test
    void test_idTask(){
        Assertions.assertSame("64353dsdstasd",myTask.getIdTask());
    }
    @Test
    void test_dueDate() throws ParseException {
        Date date = dateFormat.parse("2022-03-31");
        Assertions.assertEquals(date,myTask.getDueDate());
    }
    @Test
    void test_title(){
        Assertions.assertSame("Task",myTask.getTitle());
    }
    @Test
    void test_isDone(){
        Assertions.assertSame(false,myTask.getIsDone());
    }
    @Test
    void test_importance(){
        Assertions.assertSame("foarte",myTask.getImportance());
    }
}
