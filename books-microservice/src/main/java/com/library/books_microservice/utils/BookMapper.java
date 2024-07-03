package com.library.books_microservice.utils;

import com.library.books_microservice.dto.BookDTO;
import com.library.books_microservice.entities.BookEntity;

import java.util.List;

public class BookMapper {

    public static BookDTO entityToDto(BookEntity bookEntity) {
        return BookDTO
                .builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .isbn(bookEntity.getIsbn())
                .quantity(bookEntity.getQuantity())
                .author(bookEntity.getAuthor())
                .build();
    }

    public static BookEntity dtoToEntity(BookDTO bookDTO) {
        return BookEntity
                .builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .isbn(bookDTO.isbn())
                .quantity(bookDTO.quantity())
                .author(bookDTO.author())
                .build();
    }

    public static List<BookDTO> listEntityToDto(List<BookEntity> entityList) {
        return entityList
                .stream()
                .map(BookMapper::entityToDto)
                .toList();
    }
}
