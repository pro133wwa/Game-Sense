package Game.Sense.client.mine.SRC.impl;

import Game.Sense.client.GameSense;
import Game.Sense.client.mine.SRC.FileManager;
import Game.Sense.client.UI.UwU.NAXNADO.Friend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class FriendConfig extends FileManager.CustomFile {
    public FriendConfig(String name, boolean loadOnStart) {
        super(name, loadOnStart);
    }

    @Override
    public void loadFile() {
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                if (GameSense.instance.friendManager == null) continue;
                GameSense.instance.friendManager.addFriend(name);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
            for (Friend friend : GameSense.instance.friendManager.getFriends()) {
                out.write(friend.getName().replace(" ", ""));
                out.write("\r\n");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
