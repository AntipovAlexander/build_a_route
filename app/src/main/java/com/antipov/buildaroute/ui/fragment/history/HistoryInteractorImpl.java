package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.db.entities.DaoSession;
import com.antipov.buildaroute.data.db.entities.Route;
import com.antipov.buildaroute.ui.base.BaseInteractor;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import java.util.List;
import javax.inject.Inject;

import rx.Observable;


public class HistoryInteractorImpl extends BaseInteractor implements HistoryInteractor {

    private final DaoSession dao;

    @Inject
    public HistoryInteractorImpl(SchedulerProvider scheduler, DaoSession daoSession) {
        super(scheduler);
        this.dao = daoSession;
    }

    @Override
    public Observable<List<Route>> loadHistory() {
        return Observable
                .fromCallable(() -> dao.getRouteDao().loadAll())
                .subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }
}
