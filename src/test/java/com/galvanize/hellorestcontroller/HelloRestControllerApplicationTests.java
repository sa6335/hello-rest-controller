package com.galvanize.hellorestcontroller;

import com.galvanize.entities.Person;
import com.galvanize.hellorestcontroller.controllers.HelloRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest
@AutoConfigureMockMvc
class HelloRestControllerApplicationTests {

    @Autowired
    MockMvc mvc;

    @Test
    public void getPerson() {
        HelloRestController hrc = new HelloRestController();
        Person p = hrc.getPerson("Sibi Augustin",getTestDob(10),"sibi.augustine@gmail.com");
        assertEquals(p.getAge(),10);
    }

    @Test
    public void postPerson() {
        HelloRestController hrc = new HelloRestController();
        Person p = new Person("Sibi Augustin",getTestDob(10),"sibi.augustine@gmail.com");
        Person p2 = hrc.postPerson(p);
        assertEquals(p.getAge(),p2.getAge());
    }

    @Test
    void getPersonTest() throws Exception {
        String url = "/hello?name=rob&birthDate=11/16/1962&email=rob.wing@galvanize.com";
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("rob.wing@galvanize.com")))
                .andExpect(jsonPath("$.age").value(57));
    }

    @Test
    void postPersonTest() throws Exception {
        String json = "{\"name\":\"Rob\",\"birthDate\":\"1962-11-16\",\"email\":\"rob.wing@galvanize.com\"}";
        mvc.perform(post("/hello")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("rob.wing@galvanize.com")))
                .andExpect(jsonPath("$.age").value(57));
    }

    //Method to create a date in the past for testing
    private Date getTestDob(int years){
        LocalDate ld = LocalDate.now();
        ld.minusYears(1l);

        Calendar ci = Calendar.getInstance();
        ci.add(Calendar.YEAR, -years);
        return ci.getTime();
    }

}
