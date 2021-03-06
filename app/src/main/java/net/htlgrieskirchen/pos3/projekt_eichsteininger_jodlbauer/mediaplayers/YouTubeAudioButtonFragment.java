package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.YouTubeDownload;

import java.io.File;

public class YouTubeAudioButtonFragment extends Fragment {
    private Button play;
    private Button pause;
    private Button stop;
    private MediaPlayer player;
    private LinearLayout linearLayout;
    YouTubeDownload youTubeDownload;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_you_tube_audio_button, container, false);
        linearLayout = view.findViewById(R.id.ll_yab);
        InflaterHelper.inflateLL(linearLayout);
        intializeViews(view);
        if (Static_Access.mode.equals("light")) {
            play.setBackgroundResource(R.drawable.round_button_light);
            play.setTextColor(Color.parseColor("#0d0d0d"));
            pause.setBackgroundResource(R.drawable.round_button_light);
            pause.setTextColor(Color.parseColor("#0d0d0d"));
            stop.setBackgroundResource(R.drawable.round_button_light);
            stop.setTextColor(Color.parseColor("#0d0d0d"));
        } else {
            play.setBackgroundResource(R.drawable.round_button_dark);
            play.setTextColor(Color.parseColor("#f2f2f2"));
            pause.setBackgroundResource(R.drawable.round_button_dark);
            pause.setTextColor(Color.parseColor("#f2f2f2"));
            stop.setBackgroundResource(R.drawable.round_button_dark);
            stop.setTextColor(Color.parseColor("#f2f2f2"));
        }
        return view;
    }

    private void intializeViews(View view) {
        play = view.findViewById(R.id.play_ya);
        pause = view.findViewById(R.id.pause_ya);
        stop = view.findViewById(R.id.stop_ya);
        try {
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Static_Access.currentYTAudio != null) {
                        try {
                            if (player != null) {
                                player.start();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Static_Access.currentYTAudio != null) {
                        try {
                            if (player != null) {
                                player.pause();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Static_Access.currentYTAudio != null) {
                        try {
                            if (player != null) {
                                player.stop();
                                player.release();
                            }
                            Uri u = Uri.fromFile(new File(youTubeDownload.getPath() + youTubeDownload.getTitle() + ".mp3"));
                            player = MediaPlayer.create(context, u);
                        } catch (Exception e) {

                        }
                    }
                }
            });
        } catch (Exception e) {
            //son, just dont
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onPause()
    {
        super.onPause();
    }

    public void killMP() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public void play(YouTubeDownload item, Context c) {
        if (player != null) {
            if (player.isPlaying()) {
                killMP();
            }
        }
        youTubeDownload = item;
        context = c;
        File f = (new File(youTubeDownload.getPath() + youTubeDownload.getTitle() + ".mp3"));
        Uri u = Uri.fromFile(f);
        player = MediaPlayer.create(c, u);
    }
}
