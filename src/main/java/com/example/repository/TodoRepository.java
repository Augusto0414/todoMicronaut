package com.example.repository;

import com.example.domain.Todo;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface TodoRepository extends CrudRepository<Todo, UUID> {
}
