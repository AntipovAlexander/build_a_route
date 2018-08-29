package com.antipov.buildaroute.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.ui.adapter.AutocompleteAdapter;
import com.antipov.buildaroute.ui.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressDialog extends BaseDialogFragment {

    @Inject
    AddressPresenter<AddressView, AddressInteractor> presenter;

    @BindView(R.id.actv_autocomplete) AutoCompleteTextView autoComplete;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    // todo:
                })
                .setView(getInflatedView())
                .setTitle(getString(R.string.add_address));

        AutocompleteAdapter adapter =
                new AutocompleteAdapter(getContext(),
                        android.R.layout.simple_dropdown_item_1line, getData());

        autoComplete.setAdapter(adapter);

        return builder.create();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_address_input;
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, getInflatedView());
    }

    @Override
    public void initListeners() {

    }


    // todo: remove it
    private List<String> getData(){
        List<String> dataList = new ArrayList<String>();
        dataList.add("Fashion Men");
        dataList.add("Fashion Women");
        dataList.add("Baby");
        dataList.add("Kids");
        dataList.add("Electronics");
        dataList.add("Appliance");
        dataList.add("Travel");
        dataList.add("Bags");
        dataList.add("FootWear");
        dataList.add("Jewellery");
        dataList.add("Sports");
        dataList.add("Electrical");
        dataList.add("Sports Kids");
        return dataList;
    }
}
