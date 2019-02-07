package com.oydipi.searchservice.feignclients;

import com.oydipi.searchservice.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("rating-service")
public interface RatingClient {
    @RequestMapping("/ratings")
    List<Rating> getRatings(@RequestParam("thresholdStars") Integer stars);
}
