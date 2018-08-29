package com.antipov.buildaroute.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import com.antipov.buildaroute.R;
import java.util.Objects;

public class AddressDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    this.dismiss();
                })
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    // todo:
                })
                .setView(layoutInflater.inflate(R.layout.dialog_address_input, null))
                .setTitle(getString(R.string.add_address));

        return builder.create();
    }
}
