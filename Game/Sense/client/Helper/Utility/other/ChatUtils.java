package Game.Sense.client.Helper.Utility.other;

import Game.Sense.client.GameSense;
import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.Helper.Utility.Helper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ChatUtils implements Helper {

    public static String chatPrefix = "\2477[" + TextFormatting.WHITE + GameSense.instance.name + "\2477] " + ChatFormatting.RESET;

    public static void addChatMessage(String message) {
        mc.player.addChatMessage(new TextComponentString(chatPrefix + message));
    }
}
