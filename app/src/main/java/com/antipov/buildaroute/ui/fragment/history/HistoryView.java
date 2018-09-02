package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.data.db.entities.Route;
import com.antipov.buildaroute.ui.base.IBaseView;

import java.util.List;

public interface HistoryView extends IBaseView {
    void renderList(List<Route> history);

    void onNoHistoryFound();
}
