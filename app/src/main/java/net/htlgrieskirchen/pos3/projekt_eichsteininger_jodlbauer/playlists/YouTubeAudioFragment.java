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
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.YouTubeDownload;

public class YouTubeAudioFragment extends Fragment {
    private ListView list;
    YouTubeAudioFragment.OnSelectionChangedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_you_tube_audio, container, false);
        initializeViews(view);
        registerForContextMenu(list);
        return view;
    }

    private void initializeViews(View view) {
        list = view.findViewById(R.id.lvya);
        list.setOnItemClickListener(
                (parent, view1, position, id) -> itemSelected(position));
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(YouTubeDownload item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof YouTubeAudioFragment.OnSelectionChangedListener) {
            listener = (YouTubeAudioFragment.OnSelectionChangedListener) context;
        } else {
        }
    }

    private void itemSelected(int position) {
        YouTubeDownload item = Static_Access.youTubeAudios.get(position);
        listener.onSelectionChanged(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        final ListAdapter adapter =
                new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.YouTubeAdapter(
                        getActivity(),
                        R.layout.single_playable_media,
                        Static_Access.youTubeAudios) {
                };
        list.setAdapter(adapter);
    }
    public ListView getList()
    {
        return list;
    }
}
