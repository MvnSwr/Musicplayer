package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatabaseHandler {
    protected static void safe(String directory){
        try{
            FileOutputStream fos = new FileOutputStream("Cache.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(directory);
            oos.close();
        }catch(NotSerializableException e){
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    protected static String load() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream("Cache.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        String directory = (String) ois.readObject();
        ois.close();
        
        return directory;
    }

    protected static void deleteSafedCache(){
        File file = new File("Cache.tmp");
        if(file.exists()){
            file.delete();
        }
    }
}