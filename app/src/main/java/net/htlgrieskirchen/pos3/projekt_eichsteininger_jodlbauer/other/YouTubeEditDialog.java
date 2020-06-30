package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;

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
        ok = (Button) findViewById(R.id.dialog_button);
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
                //make a static YT-Download, change it, save it and update Adapter tomorrow in school
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}