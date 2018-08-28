package com.antipov.buildaroute.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showLoadingFullscreen();

    void hideLoadingFullscreen();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
