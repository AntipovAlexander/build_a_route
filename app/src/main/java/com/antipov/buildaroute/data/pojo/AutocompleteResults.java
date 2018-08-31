package com.antipov.buildaroute.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteResults {
    private List<WayPoint> results = new ArrayList<>();

    public List<WayPoint> getResults() {
        return results;
    }

    public void setResults(List<WayPoint> results) {
        this.results = results;
    }

    public static AutocompleteResults getForTests(boolean empty) {
        AutocompleteResults model = new AutocompleteResults();
        List<WayPoint> items = new ArrayList<>(1);
        if (!empty) {
            WayPoint item = new WayPoint();
            item.setFormattedAddress("some address");
            items.add(item);
        }
        model.setResults(items);
        return model;
    }
}
