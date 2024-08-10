package Controller;

import java.util.concurrent.Callable;

public class ExceptionHandler {
    public static <T> void handleException(Callable<T> businessLogic) {
        try{
            businessLogic.call();
        }catch(NullPointerException e) {
        }catch(ClassNotFoundException e) {
        }catch(InterruptedException e) {
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}