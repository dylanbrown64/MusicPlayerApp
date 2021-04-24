package com.brown.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textArtist;
    private Button playBtn;

    private MediaPlayer mp;

    private MyAsyncTask myAsyncTask;

    private int selectedSong = R.raw.armor_ire_song;
    private boolean isPlaying = false;
    private boolean isPrepared = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = (TextView) findViewById(R.id.textViewSong);
        textArtist = (TextView) findViewById(R.id.textViewArtist);
        playBtn = (Button) findViewById(R.id.buttonPlayPause);

        textTitle.setText("The Armor of Ire");
        textArtist.setText("Eternal Champion");

        mp = new MediaPlayer();

        ArrayList<Song> songList = new ArrayList<Song>();
        songList.add(new Song("The Armor of Ire", "Eternal Champion", R.mipmap.armor_ire, R.raw.armor_ire_song));
        songList.add(new Song("The Global Cannibal", "Behind Enemy Lines", R.mipmap.global_cannibal, R.raw.global_cannibal_song));
        songList.add(new Song("Intro", "Unholy Grave", R.mipmap.revoltage, R.raw.intro_song));
        

        SongAdapter songAdapter = new SongAdapter(this, songList);

        ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(songAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mp.stop();
                isPlaying = false;
                isPrepared = false;
                playBtn.setText("Play");

                textTitle.setText(songAdapter.getItem(position).title);
                textArtist.setText(songAdapter.getItem(position).artist);
                selectedSong = songAdapter.getItem(position).songID;
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(!isPlaying){
                    if(!isPrepared) {
                        isPlaying = true;
                        playBtn.setText("Pause");

                        myAsyncTask = new MyAsyncTask();
                        myAsyncTask.execute();
                        isPrepared=true;
                    } else {
                        mp.start();
                        isPlaying = true;
                        playBtn.setText("Pause");
                    }
                } else {
                    mp.pause();

                    isPlaying = false;
                    playBtn.setText("Play");
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {
        protected Boolean doInBackground(Void... params) {
            boolean prepared = false;
            try{
                mp = MediaPlayer.create(MainActivity.this, selectedSong);
                mp.setVolume(100,100);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        isPlaying = false;
                        isPrepared = false;
                        playBtn.setText("Play");
                    }
                });
                mp.prepare();
                prepared = true;

            } catch(Exception e){
                prepared = false;
            }
            return prepared;
        }
        protected void onPostExecute(Boolean prepared) {
            super.onPostExecute(prepared);
            mp.start();
        }
    }


}