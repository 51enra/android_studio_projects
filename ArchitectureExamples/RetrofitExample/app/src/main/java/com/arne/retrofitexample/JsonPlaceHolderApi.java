package com.arne.retrofitexample;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface JsonPlaceHolderApi {

// ********* GET **********************************************************

//    @GET("posts")   //Relative URL for posts
//    Call<List<Post>> getPosts(@Query("userId") Integer userId,  // use wrapper class to be able to null parameter if not needed in call
//                              @Query("_sort") String sort,
//                              @Query("_order") String order);

// Parameters can also be declared as Array (e.g. Integer[]), List (e.g. List<Integer>), or Varargs (e.g. Integer...)
// to pass a variable number of parameters. Any Vararg has to be the last in the list of parameters!


    // Alternative: Complete flexibility of parameters with @Querymap:
    // (But in the map every parameter can only appear once, i.e. not suitable to retrieve multiple userId's at once!)
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> params);


    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

// Other possibility:
// @GET
// Call<List<Comment>> getComments(@Url String url);
// The String url can be a relative path (e.g. "posts/1/comments") that will be appended to the url defined in the Retrofit object
// or it can be a fully qualified string that REPLACES the url in the Retrofit object

// ********* POST *********************************************************

//    @POST("posts")
//    Call<Post> createPost(@Body Post post);

    // @FormUrlEncoded: The post will still be sent in the http body, but in html format, i.e. with "&" between parameters, "%20" for spaces etc.
    // Whether to use this or the JSON format above depends on the addressed REST api.
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    // Other alternatives (compare @GET above):
    // Pass parameters as Array, List or Varargs for definition of multiple values in one post
    // Define a @FieldMap similar to @QueryMap.
    // A @FieldMap can't contain Lists/Arrays, so these would have to be passed in an additional @Field.
    // Also a @Path can be defined to post to a specific endpoint.
}
