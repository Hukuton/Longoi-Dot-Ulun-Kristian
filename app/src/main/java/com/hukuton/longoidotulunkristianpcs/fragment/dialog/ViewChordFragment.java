package com.hukuton.longoidotulunkristianpcs.fragment.dialog;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hukuton.guitarlibrary.helper.DrawHelper;
import com.hukuton.longoidotulunkristianpcs.R;

/**
 * Created by Alixson on 06-Jul-16.
 */
public class ViewChordFragment extends Fragment {

    private static final String CHORD = "current_chord";
    private static final String POSITION = "position";

    public Fragment newInstance(String stringChord, int position) {
        ViewChordFragment instance = new ViewChordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putString(CHORD, stringChord);
        instance.setArguments(bundle);
        return (instance);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chord_pager_item, container, false);
        ImageView chordView = (ImageView) view.findViewById(R.id.chordView);
        int position = getArguments().getInt(POSITION);
        String chordString = getArguments().getString(CHORD);

        BitmapDrawable chord;
        try {
            chord = DrawHelper.getBitmapChord(getContext(), chordString, position, 0);
            chordView.setImageDrawable(chord);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
