package com.antipov.buildaroute.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteResults {
    private List<AutocompleteItem> results = new ArrayList<>();

    public List<AutocompleteItem> getResults() {
        return results;
    }

    public void setResults(List<AutocompleteItem> results) {
        this.results = results;
    }

    public static AutocompleteResults getForTests(boolean empty) {
        AutocompleteResults model = new AutocompleteResults();
        List<AutocompleteItem> items = new ArrayList<>(1);
        if (!empty) {
            AutocompleteItem item = new AutocompleteItem();
            item.setFormattedAddress("some address");
            items.add(item);
        }
        model.setResults(items);
        return model;
    }
}
