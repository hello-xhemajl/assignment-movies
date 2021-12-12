package helo.mali.movies.features.home.movie.repository.network;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.List;

import helo.mali.movies.Application;
import helo.mali.movies.features.home.movie.Movie;
import helo.mali.movies.network.RequestMaker;
import helo.mali.movies.network.ResponseListener;

public class MovieNetwork {
    private final static String MOVIES_ENDPOINT = "/Top250Movies/k_aaaaaaaa";

    private final static String BASE_URL = "https://imdb-api.com/en/API";

    private RequestMaker requestMaker;
    private ObjectMapper objectMapper;

    public MovieNetwork() {
        requestMaker = Application.getInstance().getRequestMaker();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public void requestMovies(ResponseListener<List<Movie>> responseListener) {
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + MOVIES_ENDPOINT,
                response -> {
                    try {
                        MovieListNetworkResponse movieListNetworkResponse = objectMapper.readValue(response, MovieListNetworkResponse.class);
                        responseListener.onResponse(movieListNetworkResponse.getItems());
                    } catch (JsonProcessingException e) {

                    }
                },
                error -> {
                    responseListener.onErrorResponse();
                }
        );

        requestMaker.addToRequestQueue(request);
    }


}
