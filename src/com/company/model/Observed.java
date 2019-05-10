package com.company.model;

public interface Observed {
    void addObserver(Observer o);

    void removeObserver(Observer o);

    boolean notifyObservers();
}
