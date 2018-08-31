package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.data.pojo.WayPoint;
import com.antipov.buildaroute.ui.base.IBaseView;

import java.util.List;

public interface AddressView extends IBaseView {
    void setAutocomplete(List<WayPoint> results);

    void notifyAboutNoResults();
}
