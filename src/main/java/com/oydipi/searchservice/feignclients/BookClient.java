package com.oydipi.searchservice.feignclients;

import com.oydipi.searchservice.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("book-service")
public interface BookClient {
    @RequestMapping("/books")
    List<Book> getBooks(@RequestParam("author") String author, @RequestParam("title") String title);
}
