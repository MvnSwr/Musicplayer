package Exceptions;
public class NoRemainingSongException extends Exception{
    public NoRemainingSongException(){
        System.err.println("no remaining song!");
    }

    public NoRemainingSongException(String msg){
        System.err.println(msg);
    }
}