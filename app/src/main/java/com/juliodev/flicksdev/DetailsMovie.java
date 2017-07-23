package com.juliodev.flicksdev;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.juliodev.flicksdev.models.Movie;

public class DetailsMovie extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        // txtRatingValue.setText(String.valueOf(rating));

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        // Get the views
        TextView title = (TextView) findViewById(R.id.tvTitle);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        TextView overview = (TextView) findViewById(R.id.tvOverview);
        TextView voteCount = (TextView) findViewById(R.id.tvVoteCount);
      //  ImageView imageView = (ImageView) findViewById(R.id.ivMovieImage);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rbMovie);
        ratingBar.setEnabled(false);

        // Set value to the Views
        title.setText(movie.getOrginalTitle());
        tvReleaseDate.append(movie.getRelease_date().toString());
        overview.setText(movie.getOverview());
        voteCount.append(movie.getVote_count().toString());

        ratingBar.setRating((float) movie.getVote_average());
        /*
        Picasso.with(this).load(movie.getBackdropPath())
                .placeholder(R.drawable.vid)
                .into(imageView);
        */


        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.ivMovieImage);

        youTubePlayerView.initialize("AIzaSyAaZL4f7MDBHVYozSqkI2166aUoG_usSaM",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        // youTubePlayer.cueVideo("5xVh-7ywKpE");
                        youTubePlayer.loadVideo("42KNwQ6u42U");
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
           finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
