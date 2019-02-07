package com.oydipi.searchservice.controller;

import com.oydipi.searchservice.feignclients.BookClient;
import com.oydipi.searchservice.feignclients.RatingClient;
import com.oydipi.searchservice.model.Book;
import com.oydipi.searchservice.model.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final RestTemplate restTemplate;
    private final BookClient bookClient;
    private final RatingClient ratingClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping
    public List<Book> searchBook(@RequestParam(required = false, defaultValue = "") String title,
                                 @RequestParam(required = false, defaultValue = "") String author,
                                 @RequestParam(required = false, defaultValue = "0") Integer stars) {
        List<Book> matchingBooks = bookClient.getBooks(author, title);
        log.info("fetched books matching: {}", matchingBooks);

        List<Rating> matchingRatings = ratingClient.getRatings(stars);
        log.info("fetching ratings matched {}", matchingRatings);

        List<String> matchingRatingBooksId = matchingRatings.stream().map(Rating::getBookId).collect(toList());
        return matchingBooks.stream()
                .filter(book -> matchingRatingBooksId.contains(book.getId()))
                .collect(toList());


    }
}
