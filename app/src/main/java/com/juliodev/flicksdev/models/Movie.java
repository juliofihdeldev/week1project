package com.juliodev.flicksdev.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Julio on 7/12/2016.
 */
public class Movie implements Serializable {
    public String getPosterPath() {
        return  String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }
    public String getBackdropPath() {
        return  String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getOrginalTitle() {
        return orginalTitle;
    }
    public String getOverview() {
        return overview;
    }

    String posterPath;
    String backdropPath;
    String orginalTitle;
    String overview;

    public String getVote_count() {
        return vote_count;
    }

    String vote_count;

    public String getRelease_date() {
        return release_date;
    }

    String release_date;


    public boolean isAdult() {
        return adult;
    }

    boolean adult;

    public double getVote_average() {
        return vote_average;
    }

    double vote_average;

    public Movie (JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.orginalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.vote_average = jsonObject.getDouble("vote_average");
        this.adult = jsonObject.getBoolean("adult");
        this.release_date = jsonObject.getString("release_date");
        this.vote_count = jsonObject.getString("vote_count");
     }
    public static ArrayList<Movie> fromJSONArray(JSONArray array)  {
        ArrayList <Movie> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
              results.add(new Movie(array.getJSONObject(x)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
