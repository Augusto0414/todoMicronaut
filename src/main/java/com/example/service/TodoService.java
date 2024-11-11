package com.example.service;

import com.example.domain.Todo;
import com.example.repository.TodoRepository;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo todoSave (Todo todo) {
        return todoRepository.save(todo);
    }

    public Iterable<Todo> allTodo () {
        return todoRepository.findAll();
    }

    public Todo todoUpdate(UUID id, Todo todo){
        return todoRepository.findById(id)
                .map(dataTodo -> {
                    dataTodo.setTitle(todo.getTitle());
                    dataTodo.setDescription(todo.getDescription());
                    dataTodo.setCompleted(todo.getCompleted());
                    return todoRepository.update(dataTodo);
                }).orElse(null);
    }

    public boolean deleteTodo (UUID id){
        if(todoRepository.existsById(id)){
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Todo> getById(UUID id) {
        return  todoRepository.findById(id);
    }

}
