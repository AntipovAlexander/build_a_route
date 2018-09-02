package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.data.db.entities.Route;
import com.antipov.buildaroute.ui.base.IBaseInteractor;

import java.util.List;

import rx.Observable;

public interface HistoryInteractor extends IBaseInteractor {
    Observable<List<Route>> loadHistory();
}
