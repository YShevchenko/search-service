package com.oydipi.searchservice.controller;

import com.oydipi.searchservice.model.Book;
import com.oydipi.searchservice.model.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final RestTemplate restTemplate;
    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping
    public List<Book> searchBook(@RequestParam(required = false, defaultValue = "") String title,
                                 @RequestParam(required = false, defaultValue = "") String author,
                                 @RequestParam(required = false, defaultValue = "0") Integer thresholdStars) {
        String booksUri = UriComponentsBuilder.fromHttpUrl(gatewayUrl + "/book-service/books")
                .queryParam("title", title)
                .queryParam("author", author)
                .toUriString();

        List<Book> matchingBooks = restTemplate.exchange(
                booksUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                }).getBody();
        log.info("fetched books matching: {}", matchingBooks);

        String ratingsUri = UriComponentsBuilder.fromHttpUrl(gatewayUrl + "/rating-service/ratings")
                .queryParam("title", title)
                .queryParam("author", author)
                .toUriString();

        List<Rating> matchingRatings = restTemplate.exchange(
                ratingsUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {
                }).getBody();
        log.info("fetching ratings matched {}", matchingRatings);

        List<String> matchingRatingBooksId = matchingRatings.stream().map(Rating::getBookId).collect(toList());
        return matchingBooks.stream()
                .filter(book -> matchingRatingBooksId.contains(book.getId()))
                .collect(toList());


    }
}
