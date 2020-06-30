package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

public class CameraAudioFragment extends Fragment {
    private ListView list;
    OnSelectionChangedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_audio, container, false);
        initializeViews(view);
        registerForContextMenu(list);
        return view;
    }

    private void initializeViews(View view) {
        list = view.findViewById(R.id.calv);
        list.setOnItemClickListener(
                (parent, view1, position, id) -> itemSelected(position));
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(CameraResponse item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectionChangedListener) {
            listener = (OnSelectionChangedListener) context;
        } else {
        }
    }

    private void itemSelected(int position) {
        CameraResponse item = Static_Access.cameraAudios.get(position);
        listener.onSelectionChanged(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        final ListAdapter adapter =
                new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter(
                        getActivity(),
                        R.layout.single_playable_media,
                        Static_Access.cameraAudios) {
                };
        list.setAdapter(adapter);
    }
    public ListView getList()
    {
        return list;
    }
}
