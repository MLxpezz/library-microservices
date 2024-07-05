package com.library.loans_microservice.dto;

import lombok.Builder;

@Builder
public record BookDTO(String id, String title, String author, String isbn, byte quantity) {
}
