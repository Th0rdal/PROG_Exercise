package at.ac.fhcampuswien.fhmdb;

import com.jfoenix.controls.JFXListView;

public interface ClickEventHandler<T> {

    public void onClick(T t, HomeController homeController);
}
