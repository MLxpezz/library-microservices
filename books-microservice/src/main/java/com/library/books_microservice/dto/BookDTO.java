package com.library.books_microservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BookDTO(String id,
                      @NotBlank(message = "El campo titulo es requerido.") String title,
                      @NotBlank(message = "El campo autor es requerido.") String author,
                      @NotBlank(message = "El campo isbn es requerido.") String isbn,
                      @Min(value = 1, message = "La cantidad de libros debe ser minimo 1") byte quantity) {
}
