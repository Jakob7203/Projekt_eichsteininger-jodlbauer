package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.YouTubeAudioList;

import java.io.File;
import java.util.Collections;

public class YouTubeEditDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button ok;
    private EditText title_editor;
    private LinearLayout linearLayout;

    public YouTubeEditDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ok = findViewById(R.id.dialog_button);
        ok.setOnClickListener(this);
        title_editor=findViewById(R.id.dialog_edit);
        linearLayout=findViewById(R.id.ll_dialog);
        InflaterHelper.inflateLL(linearLayout);
        if (Static_Access.mode.equals("light")) {
            title_editor.setHintTextColor(Color.parseColor("#0d0d0d"));
            title_editor.setTextColor(Color.parseColor("#0d0d0d"));
            ok.setTextColor(Color.parseColor("#0d0d0d"));
            ok.setBackgroundColor(Color.parseColor("#f2f2f2f2"));
        } else {
            title_editor.setTextColor(Color.parseColor("#f2f2f2"));
            title_editor.setHintTextColor(Color.parseColor("#f2f2f2"));
            ok.setTextColor(Color.parseColor("#f2f2f2"));
            ok.setBackgroundColor(Color.parseColor("#0d0d0d0d"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_button:
                if(!title_editor.getText().toString().trim().equals(""))
                {
                    for (int i = 0; i < Static_Access.youTubeAudios.size(); i++) {
                        if(Static_Access.youTubeAudios.get(i).getLink().equals(Static_Access.currentYTAudio.getLink()))
                        {
                            String p1 = Static_Access.youTubeAudios.get(i).getPath()+Static_Access.youTubeAudios.get(i).getTitle()+".mp3";
                            Log.d("TAG", p1);
                            File f1 = new File(p1);
                            Log.d("TAG", ""+f1.exists());
                            Static_Access.youTubeAudios.get(i).setTitle(title_editor.getText().toString().trim());
                            String p2 = Static_Access.youTubeAudios.get(i).getPath()+Static_Access.youTubeAudios.get(i).getTitle()+".mp3";
                            File f2 = new File(p2);
                            f1.renameTo(f2);
                            Collections.sort(Static_Access.youTubeAudios);
                            if(c instanceof YouTubeAudioList)
                            {
                                ((YouTubeAudioList) c).setAdapter();
                            }
                            YouTubeAudioList.writeToFile();
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }
        dismiss();
    }
}