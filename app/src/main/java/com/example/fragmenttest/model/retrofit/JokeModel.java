package com.example.fragmenttest.model.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeModel {
    final public static JokeModel instance = new JokeModel();

    final String BASE_URL = "https://official-joke-api.appspot.com/";
    Retrofit retrofit;
    JokeApi jokeApi;

    private JokeModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        jokeApi = retrofit.create(JokeApi.class);
    }

    public LiveData<Joke> getRandomJoke() {
        MutableLiveData<Joke> data = new MutableLiveData<>();
        Call<Joke> call = jokeApi.getRandomJoke();
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if(response.isSuccessful()){
                    Joke res = response.body();
                    data.postValue(res);
                }else {
                    Log.d("TAG", "-----get joke return response erorr");
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.d("TAG", "-----get joke faild");
            }
        });
        return data;
    }
}
