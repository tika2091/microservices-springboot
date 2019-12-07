package com.tika.moviecatalogservice.resources;


import com.tika.moviecatalogservice.models.CatalogItem;
import com.tika.moviecatalogservice.models.Movie;
import com.tika.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

@RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

    RestTemplate restTemplate = new RestTemplate();

    List<Rating> ratings = Arrays.asList(
            new Rating("123", 4),
            new Rating("456", 3)
    );

     return ratings.stream().map(rating -> {
         Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
             return new CatalogItem(movie.getName(),"Description", rating.getRating());
     })
             .collect(Collectors.toList());



    }
}
