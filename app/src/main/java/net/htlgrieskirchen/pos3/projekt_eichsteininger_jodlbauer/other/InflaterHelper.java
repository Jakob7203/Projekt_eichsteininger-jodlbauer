package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;

public class InflaterHelper {
    public static void inflateLayout(ActionBar a, LinearLayout ll)
    {
        a.setTitle("");
        if(Static_Access.mode.equals("light"))
        {
            ll.setBackgroundResource(R.drawable.backround_light);
            a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f7fd5")));
        }
        else
        {
            ll.setBackgroundResource(R.drawable.backround_dark);
            a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b79a1")));
        }
    }
    public static void inflateHeader(ActionBar a)
    {
        a.setTitle("");
        if(Static_Access.mode.equals("light"))
        {
            a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f7fd5")));
        }
        else
        {
            a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4b79a1")));
        }
    }
    public static void inflateLL( LinearLayout ll)
    {
        if(Static_Access.mode.equals("light"))
        {
            ll.setBackgroundResource(R.drawable.backround_light);
        }
        else
        {
            ll.setBackgroundResource(R.drawable.backround_dark);
        }
    }
}
