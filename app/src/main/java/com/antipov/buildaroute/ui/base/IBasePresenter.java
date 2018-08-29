package com.antipov.buildaroute.ui.base;

/**
 * Base interface for all presenters.
 *
 * Generified with:
 * @see IBaseView - base view contract
 * @see IBaseInteractor - base interactor contract
 *
 * @author AlexanderAntipov
 * @since 21.08.2018.
 */

public interface IBasePresenter<V extends IBaseView, I extends IBaseInteractor> {

    void attachView(V mvpView);

    V getView();

    I getInteractor();

    boolean isViewNotAttached();

    void detachView();
}
