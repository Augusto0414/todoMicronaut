package com.example.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Schema(description = "Representa una tarea dentro del sistema TODO.")
public class Todo {

    @Id
    @GeneratedValue
    @Schema(description = "ID único de la tarea", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "Título de la tarea", example = "Comprar provisiones", required = true)
    private String title;

    @Column(nullable = false)
    @Schema(description = "Descripción detallada de la tarea", example = "Comprar frutas, verduras y leche", required = true)
    private String description;

    @Schema(description = "Indica si la tarea está completada o no", example = "false")
    private boolean completed = false;

    public Todo () {}

    public Todo(String description, String title, boolean  completada) {
        this.description = description;
        this.title = title;
        this.completed = completada;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
