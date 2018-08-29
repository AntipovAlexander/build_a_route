package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.data.pojo.AutocompleteItem;
import com.antipov.buildaroute.ui.base.IBaseView;

import java.util.List;

public interface AddressView extends IBaseView {
    void setAutocomplete(List<AutocompleteItem> results);

    void notifyAboutNoResults();
}
