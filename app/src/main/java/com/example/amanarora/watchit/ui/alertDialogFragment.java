package com.example.amanarora.watchit.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.amanarora.watchit.R;

/**
 * Created by Aman's Laptop on 3/20/2016.
 */
public class alertDialogFragment extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.error_ok_button, null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
