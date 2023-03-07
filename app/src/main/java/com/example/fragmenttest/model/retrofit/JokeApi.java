package com.example.fragmenttest.model.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeApi {
    @GET("random_joke")
    Call<Joke> getRandomJoke();
}
