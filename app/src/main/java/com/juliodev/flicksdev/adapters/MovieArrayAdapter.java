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
    // view lookup cache
public class MovieArrayAdapter extends ArrayAdapter <Movie> {
    public static class ViewHolder{
        TextView title;
        TextView overview;
        ImageView movieImage;
    }
    public  MovieArrayAdapter(Context context, List<Movie> movies){
        super(context,android.R.layout.simple_list_item_1, movies);
    }
    @Override
    public View getView(int position , View convertView, ViewGroup parent){
        Movie movie = getItem(position);
        //view lookup stored data in tag
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            //find my image view
            viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);

            //clear out image vier
            viewHolder.movieImage.setImageResource(0);

            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            //i cache my view
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder)convertView.getTag();
        }
        //populate de if viewHolder is not null
        viewHolder.title.setText(movie.getOrginalTitle());
        viewHolder.overview.setText(movie.getOverview());

        ///GESTION DE PICASSO
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation== Configuration.ORIENTATION_PORTRAIT){
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .placeholder(R.drawable.vid)
                    .into(viewHolder.movieImage);
        }else if(orientation== Configuration.ORIENTATION_LANDSCAPE){
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .placeholder(R.drawable.vid)
                    .into(viewHolder.movieImage);
        }
        return  convertView;
    }
}
