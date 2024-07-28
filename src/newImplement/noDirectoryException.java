package newImplement;

public class noDirectoryException extends Exception{
    public noDirectoryException(){
        super();
        System.err.println("no directory!");
    }

    public noDirectoryException(String msg){
        super(msg);
    }
}
