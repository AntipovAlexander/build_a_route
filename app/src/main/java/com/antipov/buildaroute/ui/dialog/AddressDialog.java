package com.antipov.buildaroute.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.data.pojo.AutocompleteItem;
import com.antipov.buildaroute.ui.adapter.AutocompleteAdapter;
import com.antipov.buildaroute.ui.base.BaseDialogFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static io.reactivex.schedulers.Schedulers.newThread;

public class AddressDialog extends BaseDialogFragment implements AddressView {

    @Inject
    AddressPresenter<AddressView, AddressInteractor> presenter;

    @BindView(R.id.actv_autocomplete) AutoCompleteTextView autoComplete;
    @BindView(R.id.pb_loader) ProgressBar loader;

    private AutocompleteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    // todo:
                })
                .setView(getInflatedView())
                .setTitle(getString(R.string.add_address));

        adapter = new AutocompleteAdapter(getContext(), android.R.layout.simple_dropdown_item_1line);

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
        Observable<String> observable = RxTextView
                .textChanges(autoComplete)                                // observing autocomplete field
                .filter(constraint -> constraint.length() > 3)            // at least 4 chars
                .debounce(300, TimeUnit.MILLISECONDS)             // with 0.3 sec interval
                .map(CharSequence::toString)                              // mapping to string
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread());

        presenter.loadAutoComplete(observable);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        loader.setVisibility(View.GONE);
    }

    @Override
    public void setAutocomplete(List<AutocompleteItem> results) {
        adapter.setAutocomplete(results);
    }

    @Override
    public void notifyAboutNoResults() {
        onError(getString(R.string.not_found));
    }

    @Override
    public void onError(String message) {
        super.onError(message);
        autoComplete.setError(message, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
