package com.tika.moviecatalogservice.resources;


import com.tika.moviecatalogservice.models.CatalogItem;
import com.tika.moviecatalogservice.models.Movie;
import com.tika.moviecatalogservice.models.Rating;
import com.tika.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {



    // by creating bean in MoviecatalogSericeApplication we can use autowired annotation to apply RestTemplate....
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

@RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){


//it can be autowired by creating bean in MovieCatalogServiceApplication.........................
//    WebClient.Builder builder = WebClient.builder();

    UserRating ratings = restTemplate.getForObject("http://localhost:8082/ratingsdata/users/"+userId, UserRating.class);



     return ratings.getUserRating().stream().map(rating -> {

         //For each movie ID, call movie info service and get details.....
         Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);


//put them all together..........
             return new CatalogItem(movie.getName(),"Description", rating.getRating());
     })
             .collect(Collectors.toList());



    }
}
