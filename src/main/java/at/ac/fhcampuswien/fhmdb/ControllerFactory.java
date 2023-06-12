package at.ac.fhcampuswien.fhmdb;

import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private static HomeController homeControllerInstance = null;
    @Override
    public Object call(Class<?> aClass) {
        try {
            if (homeControllerInstance == null) {
                homeControllerInstance = (HomeController) aClass.getDeclaredConstructor().newInstance();
            }
            return homeControllerInstance;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
