package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private int listViewItemLayoutId;
    private List<CameraResponse> cameraResponses;
    private LayoutInflater inflater;
    public ListAdapter(Context context, int listViewItemLayoutId, List<CameraResponse> cameraResponses) {
        this.context=context;
        this.listViewItemLayoutId=listViewItemLayoutId;
        this.cameraResponses=cameraResponses;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cameraResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return cameraResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View givenView, ViewGroup parent) {
        CameraResponse playableObject = cameraResponses.get(position);
        View listItem = (givenView == null) ? inflater.inflate(this.listViewItemLayoutId, null) : givenView;
        TextView t1 = listItem.findViewById(R.id.spm_title);
        if(Static_Access.mode.equals("light"))
        {
            t1.setTextColor(Color.parseColor("#0d0d0d"));

        }
        else
        {
            t1.setTextColor(Color.parseColor("#f2f2f2"));
        }
        String title = playableObject.getTitle();
        int mark = 20;
        if(title.length()>=mark)
        {
            t1.setText(title.substring(0,mark-3)+"...");
        }
        else
        {
            t1.setText(title);
        }
        return listItem;
    }
}
