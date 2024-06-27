package com.library.books_microservice.repository;

import com.library.books_microservice.entities.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<BookEntity, String> {

    @Query("{ 'title': { $regex: ?0, $options: 'i' }}")
    Optional<BookEntity> customFindByTitle(String title);
}
