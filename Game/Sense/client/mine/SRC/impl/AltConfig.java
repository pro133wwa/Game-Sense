package Game.Sense.client.mine.SRC.impl;



import Game.Sense.client.mine.SRC.FileManager;

import java.io.*;

public class AltConfig extends FileManager.CustomFile {
    public AltConfig(String name, boolean loadOnStart) {
        super(name, loadOnStart);
    }

    public void loadFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getFile()));

        String line;


        bufferedReader.close();
    }

    public void saveFile() throws IOException {

    }
}