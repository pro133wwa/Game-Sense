package Game.Sense.client.Helper.Utility.other;

import Game.Sense.client.GameSense;
import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.Helper.Utility.Helper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ChatUtils implements Helper {

    public static String chatPrefix = "\2477[" + TextFormatting.RED + GameSense.instance.name2 + "\2477] " + TextFormatting.RED;

    public static void addChatMessage(String message) {
        mc.player.addChatMessage(new TextComponentString(chatPrefix + message));
    }
}
