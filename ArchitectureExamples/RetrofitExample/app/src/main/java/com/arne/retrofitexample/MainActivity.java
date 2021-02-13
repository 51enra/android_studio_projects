package com.arne.retrofitexample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        // Erzeuge ein Retrofit Objekt für REST Zugriffe auf die vorgegebene URL
        // Umwandlung von JSON in POJO mit der GsonConverterFactory (es gibt noch ein paar weitere Factories, auch für andere Formate, z.B. xml)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Nb.: Falls die baseUrl tiefer als bis zum 1. "/" qualifiziert ist, MUSS das letzte Zeichen weiterhin "/" sein!
        // Die im JsonPlaceholderApi definierten Endpoints (z.B. im @GET) ÜBERSCHREIBEN alle tieferen Qualifizierungen,
        // wenn sie mit "/" eingeleitet werden. Ohne "/" werden sie an die volle Qualifizierung angehängt.
        // Bsp.:
        // baseUrl "https://jsonplaceholder.typicode.com/v3/xyz/" + @GET("/posts")
        // --> Abruf von "https://jsonplaceholder.typicode.com/posts/"

        // Aufruf von Retrofit, um eine Implementierung des Interface zu erzeugen
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        //getComments();
        createPost();
    }

    private void getPosts() {
        // Erzeuge das REST Call Objekt
        //Call<List<Post>> call = jsonPlaceHolderApi.getPosts(4, "id", "desc");

        // Modification of Call to use @QueryMap:
        Map<String, String > parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        // Aufruf des REST calls nicht direkt sondern in einem Hintergrund-Thread; wird von Retrofit gesteuert
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) { // HTTP codes 200-300 are 'successful'
                    textViewResult.setText("Code:" + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // Anzeigen der Fehlermeldung
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        // Erzeuge das REST Call Objekt
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);

        // Aufruf des REST calls nicht direkt sondern in einem Hintergrund-Thread; wird von Retrofit gesteuert
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) { // HTTP codes 200-300 are 'successful'
                    textViewResult.setText("Code:" + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "ID: " + comment.getId() + "\n";
                    content += "User ID: " + comment.getName() + "\n";
                    content += "Title: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                // Anzeigen der Fehlermeldung
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        // Post as JSON:
        // Post post = new Post(23, "New Title", "New Text");
        // Call<Post> call = jsonPlaceHolderApi.createPost(post);

        // Post with @FormUrlEncoded (see JsonPlaceHolderApi):
        Call<Post> call = jsonPlaceHolderApi.createPost(23, "New Title", "New Text");

        // Aufruf des REST calls nicht direkt sondern in einem Hintergrund-Thread; wird von Retrofit gesteuert
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) { // HTTP codes 200-300 are 'successful'
                    textViewResult.setText("Code:" + response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
