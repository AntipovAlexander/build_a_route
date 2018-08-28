package com.antipov.buildaroute.ui.base;


import javax.inject.Inject;


/**
 * Base class for presenters.
 *
 * Generified with:
 * @see IBaseView - base view contract
 * @see IBaseInteractor - base interactor contract
 *
 * Implements:
 * @see IBasePresenter - interface for all presenters in app
 *
 * @author AlexanderAntipov
 * @since 21.08.2018.
 */
public class BasePresenter<V extends IBaseView, I extends IBaseInteractor> implements IBasePresenter<V, I> {

    private V mView;

    private I mInteractor;

    /**
     * @param interactor - injecting interactor
     */
    @Inject
    public BasePresenter(I interactor) {
        this.mInteractor = interactor;
    }

    /**
     * @param mvpView - attaching view
     */
    @Override
    public void attachView(V mvpView) {
        this.mView = mvpView;
    }

    /**
     * @return view
     */
    @Override
    public V getView() {
        return mView;
    }

    /**
     * @return interactor
     */
    @Override
    public I getInteractor() {
        return mInteractor;
    }

    /**
     * @return is view attached
     */
    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * detaching view
     */
    @Override
    public void detachView() {
        this.mView = null;
    }
}
