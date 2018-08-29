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
}
