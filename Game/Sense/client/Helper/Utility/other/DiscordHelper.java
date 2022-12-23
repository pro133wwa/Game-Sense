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
            DiscordHelper.discordRichPresence.largeImageKey = "https://steamuserimages-a.akamaihd.net/ugc/1699529224113152957/5C738FA2908C3446C9BFB31835A0A83D31AA2D20/?imw=512&amp;&amp;ima=fit&amp;impolicy=Letterbox&amp;imcolor=%23000000&amp;letterbox=false";
            DiscordHelper.discordRichPresence.largeImageText = "vk.com/HUNNER.cc";
            DiscordHelper.discordRichPresence.smallImageText = "UID: " + "0 | Name: " + "DiGGeR";
            DiscordHelper.discordRichPresence.smallImageKey = "https://steamuserimages-a.akamaihd.net/ugc/1699529224113152957/5C738FA2908C3446C9BFB31835A0A83D31AA2D20/?imw=512&amp;&amp;ima=fit&amp;impolicy=Letterbox&amp;imcolor=%23000000&amp;letterbox=false";
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
