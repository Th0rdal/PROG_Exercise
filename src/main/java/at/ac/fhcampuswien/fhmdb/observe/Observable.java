package at.ac.fhcampuswien.fhmdb.observe;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    public List<Observer> observers= new ArrayList<>();
    void addObserver(Observer observer);
    void updateObservers(ObservableMessages message);

}
