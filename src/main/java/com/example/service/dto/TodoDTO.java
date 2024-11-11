package com.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Representación de datos de la tarea.")
public class TodoDTO {

    @Schema(description = "ID único de la tarea", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Título de la tarea", example = "Comprar provisiones")
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Comprar frutas, verduras y leche")
    private String description;

    @Schema(description = "Estado de la tarea, indica si está completada o no", example = "false")
    private boolean completed;
    public TodoDTO(UUID id, String title, String description, boolean completed) {
        this.id = id.toString();
        this.title = title;
        this.description = description;
        this.completed = completed;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
