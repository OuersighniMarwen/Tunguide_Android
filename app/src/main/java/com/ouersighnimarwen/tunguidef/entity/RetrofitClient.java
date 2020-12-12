package com.ouersighnimarwen.tunguidef.entity;

import com.ouersighnimarwen.tunguidef.Retrofit.INodeJs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public INodeJs getApi(){ return retrofit.create(INodeJs.class); }



}
