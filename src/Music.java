import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Music {
    private static List<String> tracks = new ArrayList<>();
	private static Random random = new Random();
    
	static String getSong(){
		if (tracks.isEmpty()) {
            throw new IllegalStateException("Keine Songs in der Liste");
        }
        return tracks.get(random.nextInt(tracks.size()));
	}

    static void load(){
		File root = new File("C:/Users/marvi/OneDrive/Laptop/Programmieren/Musicplayer/Music");
		if (root.exists() && root.isDirectory()) {
            getFiles(root.listFiles());
        }
    }

	private static void getFiles(File[] files){
		for(File fl : files){
			if(fl.isDirectory()){
				getFiles(fl.listFiles());
			}else if(fl.getName().endsWith(".wav")){
				tracks.add(fl.getAbsolutePath());
			}
		}
	}
}