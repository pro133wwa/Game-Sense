package Game.Sense.client.UI.UwU;


import net.minecraft.client.main.Main;

import java.io.File;

public class UID {
    public static String login;
    public static int uid = 0;
    public static String line = "";
    public static String user = "";

    public static String subDate;
    public static String vkID;

    public static String getLogin() {
        return login;
    }

    public static int getUid() {

        return uid + 1;
    }

    public static String getUser() {
        return user = Main.user11;
    }

    public static String getLine() {
        return line = String.valueOf(getUid());
    }

    public static String getSubDate() {
        return subDate;
    }

    public static String getVkID() {
        return vkID;
    }

}

