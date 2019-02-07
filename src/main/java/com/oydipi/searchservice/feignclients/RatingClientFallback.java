package com.oydipi.searchservice.feignclients;

import com.oydipi.searchservice.model.Rating;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.singletonList;

@Component
public class RatingClientFallback implements RatingClient {
    @Override
    public List<Rating> getRatings(Integer stars) {
        return singletonList(new Rating("rating service down", -1));
    }
}
