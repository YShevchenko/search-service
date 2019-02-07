package com.oydipi.searchservice.feignclients;

import com.oydipi.searchservice.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "rating-service", fallback = RatingClientFallback.class)
public interface RatingClient {
    @GetMapping(value = "/ratings")
    List<Rating> getRatings(@RequestParam("thresholdStars") Integer stars);
}
