package com.juliodev.flicksdev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.juliodev.flicksdev.adapters.MovieArrayAdapter;
import com.juliodev.flicksdev.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;


    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HaitiCine");
        // ...
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.idprogressBar);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        //load all movies
        loadMovies();
    }

    public void loadMovies() {
        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        loadRequest();

        //Listen for item click in ListView
        lvItems.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) lvItems.getItemAtPosition(position);
                //Toast.makeText(MoviesActivity.this, movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MovieActivity.this, DetailsMovie.class);
                i.putExtra("movie", movie);
                startActivity(i);
            }
        });
    }

    public void loadRequest() {

        // url ce qui contient le lien de api
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        // ou instancie asyncHttpClient
        AsyncHttpClient Client = new AsyncHttpClient();
        //fonction qui fait apelama fichier json
        Client.get(url, new JsonHttpResponseHandler() {
            @Override
            // fonction qui recuper ma json si tout va bien
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // decalrion de variable de types json movieJsonResults
                JSONArray movieJsonResults = null;
                // on gere une conditions
                try {
                    //je vais palcer mon code whiprefresh
                    //on recuper nos resultat
                    movieJsonResults = response.getJSONArray("results");
                    // on les ajoute
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    //  Log.d("DEBUG",movies.toString());
                    // Now we call setRefreshing(false) to signal refresh has finished

                    //Toast.makeText(getApplicationContext(),"Connexion réussi", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Connexion échoué", Toast.LENGTH_LONG).show();
                }
            }

            // fonction qui gere une erreu avec json
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                // a debug on fail
                Toast.makeText(getApplicationContext(), "Connexion échoué", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        loadRequest();
        swipeContainer.setRefreshing(false);
    }
}