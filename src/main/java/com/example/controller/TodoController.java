package com.example.controller;

import com.example.domain.Todo;
import com.example.service.TodoService;
import com.example.service.dto.TodoDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.UUID;

@Controller("/todo")
@Tag(name = "Todo", description = "Operaciones CRUD para gestionar tareas.")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Post
    @Operation(summary = "Crear una nueva tarea",
            description = "Crea una nueva tarea con los datos proporcionados.")
    @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Todo.class)))
    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
    @RequestBody(description = "Datos de la nueva tarea",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Todo.class),
                    examples = @ExampleObject(value = """
                                        {
                                            "title": "Estudiar Micronaut",
                                            "description": "Completar tutoriales y ejemplos de Micronaut",
                                            "completed": false
                                        }
                                        """)))
    public HttpResponse<Todo> createTodo(@Body Todo todo) {
        Todo saveTodo = todoService.todoSave(todo);
        return HttpResponse.created(saveTodo)
                .headers(headers -> headers.location(location(saveTodo.getId())));
    }

    @Put("/{id}")
    @Operation(summary = "Actualizar una tarea existente",
            description = "Actualiza una tarea con el ID especificado.")
    @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontró tarea para actualizar")
    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
    @RequestBody(description = "Datos actualizados de la tarea",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Todo.class),
                    examples = @ExampleObject(value = """
                        {
                            "title": "Estudiar Spring Boot",
                            "description": "Revisar ejemplos de Spring Boot para comparar con Micronaut",
                            "completed": true
                        }
                        """)))
    public HttpResponse<Todo> updateTodo(UUID id, @Body Todo todo) {
        try {
            Todo updatedTodo = todoService.todoUpdate(id, todo);
            if (updatedTodo != null) {
                return HttpResponse.ok(updatedTodo);  // Devolvemos el objeto actualizado
            } else {
                return HttpResponse.notFound();  // Retornamos 404 si no se encuentra
            }
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Delete("/{id}")
    @Operation(summary = "Eliminar una tarea",
            description = "Elimina la tarea con el ID especificado.")
    @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    public HttpResponse<?> deleteTodo(UUID id) {
        return todoService.deleteTodo(id) ? HttpResponse.noContent() : HttpResponse.notFound();
    }

    @Get("/{id}")
    @Operation(summary = "Obtener una tarea por ID",
            description = "Obtiene una tarea a partir de su ID.")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Todo.class)))
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    public HttpResponse<Todo> getById(UUID id) {
        return todoService.getById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Get
    @Operation(summary = "Listar todas las tareas",
            description = "Obtiene una lista de todas las tareas.")
    @ApiResponse(responseCode = "200", description = "Lista de tareas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TodoDTO.class))))
    public Iterable<TodoDTO> list() {
        Iterable<Todo> todos = todoService.allTodo();
        List<TodoDTO> todoDTOs = new ArrayList<>();

        for (Todo todo : todos) {
            todoDTOs.add(new TodoDTO(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getDescription(),
                    todo.getCompleted()
            ));
        }

        return todoDTOs;
    }

    private URI location(UUID id) {
        return URI.create("/todos/" + id);
    }
}
