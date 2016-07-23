package com.hukuton.longoidotulunkristianpcs.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hukuton.longoidotulunkristianpcs.R;
import com.hukuton.longoidotulunkristianpcs.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristianpcs.interfaces.OnTranspose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alixson on 09-Jul-16.
 */
public class DialogTransposeFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener {

    private static final String[] KEY = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "Ab", "A", "Bb", "B"};
    private static final int[] ids = {R.id.radioButtonMinusFive, R.id.radioButtonMinusFour, R.id.radioButtonMinusThree, R.id.radioButtonMinusTwo, R.id.radioButtonMinusOne, R.id.radioButtonZero, R.id.radioButtonPlusOne, R.id.radioButtonPlusTwo, R.id.radioButtonPlusThree, R.id.radioButtonPlusFour, R.id.radioButtonPlusFive, R.id.radioButtonPlusSix,};

    private OnTranspose onTranspose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_transpose, container, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.transposeRadioGroup);

        String origChord = getArguments().getString(BookDatabaseHelper.ORIGINAL_CHORD);
        int transDistance = getArguments().getInt(BookDatabaseHelper.TRANSPOSED_CHORD_DISTANCE);

        //Toast.makeText(getContext(), origChord + ", " + transDistance,Toast.LENGTH_SHORT).show();

        radioButtonArraySetup(view, createArrangement(origChord), transDistance);
        radioGroup.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            onTranspose = (OnTranspose) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement OnTranspose interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setTitle("Choose Key");
        return dialog;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonMinusFive:
                onTranspose.transpose(-5);
                break;
            case R.id.radioButtonMinusFour:
                onTranspose.transpose(-4);
                break;
            case R.id.radioButtonMinusThree:
                onTranspose.transpose(-3);
                break;
            case R.id.radioButtonMinusTwo:
                onTranspose.transpose(-2);
                break;
            case R.id.radioButtonMinusOne:
                onTranspose.transpose(-1);
                break;
            case R.id.radioButtonZero:
                onTranspose.transpose(0);
                break;
            case R.id.radioButtonPlusOne:
                onTranspose.transpose(1);
                break;
            case R.id.radioButtonPlusTwo:
                onTranspose.transpose(2);
                break;
            case R.id.radioButtonPlusThree:
                onTranspose.transpose(3);
                break;
            case R.id.radioButtonPlusFour:
                onTranspose.transpose(4);
                break;
            case R.id.radioButtonPlusFive:
                onTranspose.transpose(5);
                break;
            case R.id.radioButtonPlusSix:
                onTranspose.transpose(6);
                break;
        }
        this.dismiss();
    }

    private List<String> createArrangement(String origChord) {
        int position = 0;
        int firstRow;

        for (int i = 0; i < KEY.length; i++) {
            if (origChord.equals(KEY[i])) {
                position = i;
                break;
            }
        }

        firstRow = position - 5;
        firstRow = (firstRow < 0) ? firstRow + KEY.length : firstRow;

        List<String> list = new ArrayList<>();

        for (int i = 0; i < KEY.length; i++) {
            int a = i + firstRow;
            if (a < KEY.length) {
                list.add(i, KEY[a]);
            } else {
                list.add(i, KEY[a % KEY.length]);
            }
        }
        return list;
    }

    private void radioButtonArraySetup(View view, List<String> arrangement, int transDistance) {
        RadioButton radioButton[] = new RadioButton[ids.length];
        for (int i = 0; i < ids.length; i++) {
            radioButton[i] = (RadioButton) view.findViewById(ids[i]);
        }

        radioButton[5+transDistance].setChecked(true);
        radioButton[0].setText("-5      " + arrangement.get(0));
        radioButton[1].setText("-4      " + arrangement.get(1));
        radioButton[2].setText("-3      " + arrangement.get(2));
        radioButton[3].setText("-2      " + arrangement.get(3));
        radioButton[4].setText("-1      " + arrangement.get(4));
        radioButton[5].setText("0       " + arrangement.get(5));
        radioButton[6].setText("+1      " + arrangement.get(6));
        radioButton[7].setText("+2      " + arrangement.get(7));
        radioButton[8].setText("+3      " + arrangement.get(8));
        radioButton[9].setText("+4      " + arrangement.get(9));
        radioButton[10].setText("+5     " + arrangement.get(10));
        radioButton[11].setText("+6     " + arrangement.get(11));
    }
}
