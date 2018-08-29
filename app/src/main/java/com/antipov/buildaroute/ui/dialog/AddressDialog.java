package com.antipov.buildaroute.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.ui.base.BaseDialogFragment;

import java.util.Objects;
import java.util.function.Function;

import javax.inject.Inject;

public class AddressDialog extends BaseDialogFragment {

    @Inject
    AddressPresenter<AddressView, AddressInteractor> presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);

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

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }
}
