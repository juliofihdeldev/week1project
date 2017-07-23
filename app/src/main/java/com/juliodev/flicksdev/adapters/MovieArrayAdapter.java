package com.juliodev.flicksdev.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliodev.flicksdev.R;
import com.juliodev.flicksdev.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Created by Julio on 7/12/2016.
 */

public class MovieArrayAdapter extends ArrayAdapter <Movie>  {
    public static class ViewHolder{
        TextView title;
        TextView overview;
        ImageView movieImage;
    }
    //Return int for the type according to popularity of movie (1 or 0)
    @Override
    public int getItemViewType(int position) {
        //return 1 if movie is popular, or 0 if not
        return getItem(position).getVote_average() >= 6.7 ? 1 : 0;
    }

    //Total number of type is 2 (popular an non-popular)
    @Override
    public int getViewTypeCount() {
        //For Movie popularity, either a Movie is popular, or it is not (2 possibilities)
        return 2;
    }

    public  MovieArrayAdapter(Context context, List<Movie> movies){
        super(context,android.R.layout.simple_list_item_1, movies);
    }
    @Override
    public View getView(int position , View convertView, ViewGroup parent){
        Movie movie = getItem(position);

        //view lookup stored data in tag
        //Get data item movie type for this position
        int type = getItemViewType(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //Inflate the XML layout based on the type
            convertView = getInflatedLayoutForType(type);
            /*
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            */
            //find my image view
            viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);

            //clear out image vier
            viewHolder.movieImage.setImageResource(0);

            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            //i cache my view

            //populate de if viewHolder is not null
           // viewHolder.title.setText(movie.getOrginalTitle().toString());
           // viewHolder.overview.setText(movie.getOverview().toString());

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder)convertView.getTag();
        }
        if(viewHolder.title != null)
            viewHolder.title.setText(movie.getOrginalTitle());
        if(viewHolder.overview != null)
            viewHolder.overview.setText(movie.getOverview());


        ///GESTION DE PICASSO
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation== Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(getContext()).load(movie.getPosterPath())
                        //.placeholder(R.drawable.ic_action_play)
                        .into(viewHolder.movieImage);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getBackdropPath())
                         // .placeholder(R.drawable.ic_action_play)
                        .into(viewHolder.movieImage);
            }

        return  convertView;
    }
    //Method to return the correct inflated XML Layout file depending on the item movie popularity
    private View getInflatedLayoutForType(int type) {

        //if movie is popular inflate item_movie_popular, else inflate item_movie
        if (type == 1) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);

        } else if (type == 0) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else {
            return null;
        }
    }
}
