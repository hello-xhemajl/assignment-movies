package helo.mali.movies.features.home.tvshow.repository.network;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.List;

import helo.mali.movies.Application;
import helo.mali.movies.features.home.tvshow.TvShow;
import helo.mali.movies.network.RequestMaker;
import helo.mali.movies.network.ResponseListener;

public class TvShowNetwork {
    private final static String TV_SHOWS_ENDPOINT = "/Top250TVs/k_aaaaaaaa";

    private final static String BASE_URL = "https://imdb-api.com/en/API";

    private RequestMaker requestMaker;
    private ObjectMapper objectMapper;

    public TvShowNetwork() {
        requestMaker = Application.getInstance().getRequestMaker();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void requestTvShows(ResponseListener<List<TvShow>> responseListener) {
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + TV_SHOWS_ENDPOINT,
                response -> {
                    try {
                        TvShowListNetworkResponse tvShowListNetworkResponse = objectMapper.readValue(response, TvShowListNetworkResponse.class);
                        responseListener.onResponse(tvShowListNetworkResponse.getItems());
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
