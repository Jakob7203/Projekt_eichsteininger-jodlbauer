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
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

public class CameraAudioButtonFragment extends Fragment {
    public final static String TAG = CameraAudioButtonFragment.class.getSimpleName();
    private Button play_pause;
    private MediaPlayer player;
    private LinearLayout linearLayout;
    CameraResponse cameraResponse;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_audio_button, container, false);
        linearLayout = view.findViewById(R.id.ll_cab);
        InflaterHelper.inflateLL(linearLayout);
        intializeViews(view);
        if(Static_Access.mode.equals("light"))
        {
            play_pause.setBackgroundResource(R.drawable.round_button_light);
            play_pause.setTextColor(Color.parseColor("#0d0d0d"));
        }
        else
        {
            play_pause.setBackgroundResource(R.drawable.round_button_dark);
            play_pause.setTextColor(Color.parseColor("#f2f2f2"));
        }
        return view;
    }

    private void intializeViews(View view) {
        play_pause = view.findViewById(R.id.pp_ca);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Static_Access.currentAudio!=null) {
                    if (player.isPlaying()) {
                        player.stop();
                        play_pause.setText("Play");
                    }
                    else
                    {
                        Uri u = Uri.parse(cameraResponse.getUri());
                        player = MediaPlayer.create(context, u);
                        player.start();
                        play_pause.setText("Pause");
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void play(CameraResponse item, Context c) {
        cameraResponse= item;
        context=c;
        Uri u = Uri.parse(item.getUri());
        player = MediaPlayer.create(c, u);
    }

}