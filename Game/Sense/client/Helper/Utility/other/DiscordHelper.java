package Game.Sense.client.Helper.Utility.other;

import Game.Sense.client.GameSense;
import Game.Sense.client.Helper.Utility.Helper;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHelper implements Helper {
    private static final String discordID = "b501d74505dbf5681df59274b5bb326becabcdb28374012d9f06d0436675a5e7";
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = new DiscordRPC();

    public static void startRPC() {
        try {
            DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
            DiscordRPC.discordInitialize(discordID, eventHandlers, true, null);
            DiscordHelper.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
            DiscordHelper.discordRichPresence.details =   "Premium" + " | " + "b" + GameSense.instance.version;
            DiscordHelper.discordRichPresence.largeImageKey = "logo";
            DiscordHelper.discordRichPresence.largeImageText = "rules - is you!";
            DiscordHelper.discordRichPresence.state = "Playing on: " + mc.getCurrentServerData().serverIP;
            DiscordRPC.discordUpdatePresence(discordRichPresence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopRPC() {
        DiscordRPC.discordShutdown();
    }
}
