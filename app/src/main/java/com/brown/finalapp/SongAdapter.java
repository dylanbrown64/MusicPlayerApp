package com.brown.finalapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter(Activity context, ArrayList<Song> songList) {
        super(context, 0, songList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Song currentSong = getItem(position);

        View mySong = convertView;
        if (mySong == null){
            mySong = LayoutInflater.from(getContext()).inflate(R.layout.song, parent, false);
        }

        TextView title = (TextView) mySong.findViewById(R.id.textViewSong);
        TextView artist = (TextView) mySong.findViewById(R.id.textViewArtist);
        ImageView image = (ImageView) mySong.findViewById(R.id.imageView);

        title.setText(currentSong.title);
        artist.setText(currentSong.artist);
        image.setImageResource(currentSong.imageID);

        return mySong;
    }
}
