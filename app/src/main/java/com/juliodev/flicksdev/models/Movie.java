package com.juliodev.flicksdev.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Julio on 7/12/2016.
 */
public class Movie {
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

    public Movie (JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.orginalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
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
