package com.oydipi.searchservice.feignclients;

import com.oydipi.searchservice.model.Book;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BookClientFallback implements BookClient {
    @Override
    public List<Book> getBooks(String author, String title) {
        return Collections.singletonList(new Book("sorry", "book service doesnt", "work"));
    }
}
