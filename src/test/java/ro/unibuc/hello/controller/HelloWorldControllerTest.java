package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.service.HelloWorldService;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
public class HelloWorldControllerTest {
//
//    @Mock
//    private HelloWorldService helloWorldService;
//
//    @InjectMocks
//    private HelloWorldController helloWorldController;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    private static final String datePattern = "yyyy-MM-dd";
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

//    @Test
//    void test_sayHello() throws Exception {
//        // Arrange
//        Greeting greeting = new Greeting(1, "Hello, there!");
//
//        when(helloWorldService.hello(any())).thenReturn(greeting);
//
//        // Act
//        MvcResult result = mockMvc.perform(get("/hello-world?name=there")
//                        .content(objectMapper.writeValueAsString(greeting))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // Assert
//        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(greeting));
//    }
}
