package com.ouersighnimarwen.tunguidef.Retrofit;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface INodeJs
{
    @POST("register")
    @FormUrlEncoded
    Call<ResponseBody> registerUser (@Field("name") String name,
                                     @Field("lastName") String lastName,
                                     @Field("email") String email,
                                     @Field("phoneNumber") String phoneNumber,
                                     @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> loginUser (@Field("email") String email,
                                  @Field("password") String password);
    @POST("addHotel")
    Call<ResponseBody> AddHotel (@Query("name") String hotelName,
                                 @Query("adresse") String hotelAdresse,
                                 @Query("image") String hotelImage,
                                 @Query("star") String hotelStar,
                                 @Query("price") String hotelPrice);

    // Update user profile photo url
    @PATCH("updatePhoto")
    Call<ResponseBody> updateImageUrl(
            @Query("id") int id, @Query("ImageUrl") String imageUrl
    );

    // Upload user profile photo to server
    @Multipart
    @POST("upload_image")
    Call<ResponseBody> uploadPhoto(
            @Part MultipartBody.Part image
    );

    @GET("hotels")
    Call<ResponseBody> getHotels();

    @POST("addRestaurant")
    Call<ResponseBody> addRestaurant (@Query("name") String restaurantName,
                                      @Query("adresse") String restaurantAdresse,
                                      @Query("image") String restaurantImage,
                                      @Query("star") String restaurantStar);

    @GET("restaurants")
    Call<ResponseBody> getRestaurants();

    @POST("addActivity")
    Call<ResponseBody> addActivity (@Query("name") String actName,
                                    @Query("adresse") String actAdresse,
                                    @Query("image") String actImage,
                                    @Query("price") String actPrice);

    @GET("activities")
    Call<ResponseBody> getActivities();

}
