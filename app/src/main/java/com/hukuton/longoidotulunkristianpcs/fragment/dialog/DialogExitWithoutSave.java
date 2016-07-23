package com.hukuton.longoidotulunkristianpcs.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.hukuton.longoidotulunkristianpcs.interfaces.OnDialogItemClick;

/**
 * Created by Alixson on 16-Jul-16.
 */
public class DialogExitWithoutSave extends DialogFragment implements DialogInterface.OnClickListener{

    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Exit");
        builder.setMessage("Exit without saving?");
        builder.setPositiveButton("Yes", this);
        builder.setNegativeButton("Cancel", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if(which == AlertDialog.BUTTON_POSITIVE) {
            try{
                ((OnDialogItemClick) context).onPositiveButtonClick();
            }catch (ClassCastException cce){
                cce.printStackTrace();
            }
        } else if(which == AlertDialog.BUTTON_NEGATIVE) {
            try{
                ((OnDialogItemClick) context).onNegativeButtonClick();
            }catch (ClassCastException cce){
                cce.printStackTrace();
            }
        }

        dialogInterface.dismiss();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }
}
