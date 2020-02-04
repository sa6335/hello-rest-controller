package com.galvanize.hellorestcontroller.controllers;

import com.galvanize.entities.Person;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class HelloRestController {

    //@RequestMapping("/hello")

    @GetMapping("/hello")
    public Person sayHello(@RequestParam String name,
                           @RequestParam Date birthDate,
                           @RequestParam String email) {
        return new Person(name,birthDate,email);
    }

    @PostMapping("/hello")
    public Person helloPerson(@RequestBody Person person) {
        return person;
    }

}
