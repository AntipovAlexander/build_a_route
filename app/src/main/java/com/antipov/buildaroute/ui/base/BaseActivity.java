package com.antipov.buildaroute.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.antipov.buildaroute.R;
import com.antipov.buildaroute.app.Application;
import com.antipov.buildaroute.di.component.ActivityComponent;
import com.antipov.buildaroute.di.component.DaggerActivityComponent;
import com.antipov.buildaroute.di.module.ActivityModule;
import com.antipov.buildaroute.utils.DialogUtils;
import com.antipov.buildaroute.utils.NetworkUtils;

/**
 * Base class for activities.
 *
 * Extends:
 * @see AppCompatActivity - Base class for activities that use the support library action bar
 * features.
 *
 * Implements:
 * @see IBaseView - interface for all views in app
 *
 * @author AlexanderAntipov
 * @since 21.08.2018.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private MaterialDialog materialDialog;
    private ActivityComponent mActivityComponent;

    /**
     * Calling base methods to instantiate class.
     *
     * @param savedInstanceState - saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // some activities may be without content, e.g splash screen.
        // get layout id may return -1 in that case
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        }
        initViews();
        initToolbar();
        initListeners();
    }

    /**
     * Instantiate and return activity component
     *
     * @return ActivityComponent generated by Dagger2
     */
    public ActivityComponent getComponent() {
        if (mActivityComponent == null){
            mActivityComponent = DaggerActivityComponent
                    .builder()
                    .activityModule(new ActivityModule(this))
                    .appComponent(((Application) getApplication()).getComponent())
                    .build();
        }
        return mActivityComponent;
    }


    /**
     * show progress dialog
     */
    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_title)
                .content(R.string.please_wait)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    /**
     * hide progress dialog
     */
    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.cancel();
        }
    }

    /**
     * Show snack bar with error text
     *
     * @param resId - String id for message. I.e. R.string.network_error
     */
    @Override
    public void onError(@IdRes int resId) {
        showMessage(resId);
    }

    /**
     * Show snack bar with error text
     *
     * @param message - String message
     */
    @Override
    public void onError(String message) {
        showMessage(message);
    }

    /**
     * Show custom message with String
     *
     * @param message - your string message
     */
    @Override
    public void showMessage(String message) {
        DialogUtils.showSnackbar(this, message);
    }


    /**
     * Show custom message with String
     *
     * @param resId - String id for message. I.e. R.string.greetings
     */
    @Override
    public void showMessage(int resId) {
        DialogUtils.showSnackbar(this, getString(resId));
    }

    /**
     * Utility method for checking Internet connection
     *
     * @return connected or not.
     */
    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    /**
     * Hiding virtual keyboard
     */
    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Method for setup layout.
     * An implementation returning layout id for inflating.
     * I.e. R.layout.main
     *
     * @return layout id. If you are don`t using layout, just return -1
     */
    public abstract int getLayoutId();


    /**
     * Initializing views. Put in the implementation your findViewById() methods
     * Or instantiate ButterKnife instead.
     */
    public abstract void initViews();

    /**
     * Create your listener callbacks there.
     */
    public abstract void initListeners();


    /**
     * Toolbar initialization.
     * Set title, subtitle and more.
     */
    public abstract void initToolbar();
}
