package Game.Sense.client.files.impl;



import Game.Sense.client.files.FileManager;

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