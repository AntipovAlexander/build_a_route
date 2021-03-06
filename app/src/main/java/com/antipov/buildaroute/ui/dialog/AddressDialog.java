package com.antipov.buildaroute.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;
import com.antipov.buildaroute.ui.adapter.AutocompleteAdapter;
import com.antipov.buildaroute.ui.base.BaseDialogFragment;
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;
import static io.reactivex.schedulers.Schedulers.newThread;

public class AddressDialog extends BaseDialogFragment implements AddressView, AutocompleteAdapter.OnAutocompleteSelected {

    @Inject
    AddressPresenter<AddressView, AddressInteractor> presenter;

    @BindView(R.id.actv_autocomplete) AutoCompleteTextView autoComplete;
    @BindView(R.id.pb_loader) ProgressBar loader;

    private AutocompleteAdapter adapter;
    private Observable<String> observable;
    private WayPoint selectedItem;

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
                    if (getTargetFragment() != null && selectedItem != null) {
                        Intent intent = new Intent();
                        intent.putExtra(Const.Args.SELECTED_ADDRESS, selectedItem);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                    }
                })
                .setView(getInflatedView())
                .setTitle(getString(R.string.add_address));

        adapter = new AutocompleteAdapter(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, this);

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
        observable = RxTextView
                .textChanges(autoComplete)                     // observing autocomplete field
                .skipInitialValue()                            // skip value when subscribed
                .filter(constraint -> constraint.length() > 3) // at least 4 chars
                .debounce(300, TimeUnit.MILLISECONDS)  // with 0.3 sec interval
                .map(CharSequence::toString)                    // mapping to string
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread());

        presenter.subscribeAutoComplete(observable);
    }

    @SuppressLint("CheckResult")
    @Override
    public void OnAutocompleteSelected(WayPoint item) {
        this.selectedItem = item;
        presenter.unSubscribeAutoComplete();
        autoComplete.setText(item.getFormattedAddress());
        presenter.subscribeAutoComplete(observable);
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
    public void setAutocomplete(List<WayPoint> results) {
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
    public void onDismiss(DialogInterface dialog) {
        hideKeyboard();
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
