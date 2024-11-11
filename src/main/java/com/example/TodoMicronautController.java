package com.example;

import io.micronaut.http.annotation.*;

@Controller("/todoMicronaut")
public class TodoMicronautController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}