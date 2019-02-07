package com.oydipi.searchservice.feignclients;

import com.oydipi.searchservice.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "book-service", fallback = BookClientFallback.class)
public interface BookClient {

    @GetMapping(value = "/books")
    List<Book> getBooks(@RequestParam("author") String author, @RequestParam("title") String title);
}
