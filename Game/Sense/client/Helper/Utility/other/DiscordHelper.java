package Game.Sense.client.Helper.Utility.other;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.Utility.Helper;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHelper implements Helper {
    private static final String discordID = "1055952140880859216";
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = new DiscordRPC();

    public static void startRPC() {
        try {
            DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
            DiscordRPC.discordInitialize(discordID, eventHandlers, true, null);
            DiscordHelper.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
            DiscordHelper.discordRichPresence.details = "Role: " + "Delvoper";
            DiscordHelper.discordRichPresence.largeImageKey = "https://media.discordapp.net/attachments/1055376209162276945/1055953913565679646/standard_2.gif";
            DiscordHelper.discordRichPresence.largeImageText = "vk.com/HUNNER.cc";
            DiscordHelper.discordRichPresence.smallImageText = "UID: " + "0 | Name: " + "DiGGeR";
            DiscordHelper.discordRichPresence.smallImageKey = "https://steamuserimages-a.akamaihd.net/ugc/1821118493985004024/0F9DD7AA19E5EB4BD19338050EC0E6C0407D87B4/?imw=512&amp;imh=284&amp;ima=fit&amp;impolicy=Letterbox&amp;imcolor=%23000000&amp;letterbox=true";
            DiscordHelper.discordRichPresence.state = "Version: " + GameSense.instance.version;
            DiscordRPC.discordUpdatePresence(discordRichPresence);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopRPC() {
        DiscordRPC.discordShutdown();
    }
}
