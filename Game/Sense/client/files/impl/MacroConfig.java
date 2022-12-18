package Game.Sense.client.files.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.command.macro.Macro;
import Game.Sense.client.files.FileManager;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class MacroConfig extends FileManager.CustomFile {

    public MacroConfig(String name, boolean loadOnStart) {
        super(name, loadOnStart);
    }

    public void loadFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.getFile().getAbsolutePath());
            DataInputStream in = new DataInputStream(fileInputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String bind = curLine.split(":")[0];
                String value = curLine.split(":")[1];
                if (GameSense.instance.macroManager != null) {
                    GameSense.instance.macroManager.addMacro(new Macro(Keyboard.getKeyIndex(bind), value));
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void saveFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
            for (Macro m : GameSense.instance.macroManager.getMacros()) {
                if (m != null) {
                    out.write(Keyboard.getKeyName(m.getKey()) + ":" + m.getValue());
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception ignored) {

        }
    }
}
