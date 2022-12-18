package Game.Sense.client;


import Game.Sense.client.command.CommandManager;
import Game.Sense.client.command.macro.MacroManager;
import Game.Sense.client.draggable.DraggableHUD;
import Game.Sense.client.event.EventManager;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.input.EventInputKey;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.FeatureManager;
import Game.Sense.client.files.FileManager;
import Game.Sense.client.files.impl.FriendConfig;
import Game.Sense.client.files.impl.HudConfig;
import Game.Sense.client.files.impl.MacroConfig;
import Game.Sense.client.friend.FriendManager;
import Game.Sense.client.utils.render.ScaleUtils;
import ViaMCP.ViaMCP;
import Game.Sense.client.ui.clickgui.ClickGuiScreen;
import Game.Sense.client.ui.config.ConfigManager;
import Game.Sense.client.utils.math.TPSUtils;
import Game.Sense.client.utils.other.DiscordHelper;
import Game.Sense.client.utils.render.cosmetic.CosmeticRender;
import Game.Sense.client.utils.render.cosmetic.impl.DragonWing;
import Game.Sense.client.utils.scaleUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;

public class GameSense {
    public static ScaleUtils scale = new ScaleUtils(2);
    public Long time;

    public FeatureManager featureManager;
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
    public String version = "0.1";
    public String coder = "UwU_, toperov, DiGGeR";

    public void init() {

        time = System.currentTimeMillis();
        Display.setTitle(name + " " + type + " " + version);
        (fileManager = new FileManager()).loadFiles();

        friendManager = new FriendManager();
        featureManager = new FeatureManager();

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
        featureManager.getAllFeatures().stream().filter(module -> module.getBind() == event.getKey()).forEach(Feature::toggle);
        macroManager.getMacros().stream().filter(macros -> macros.getKey() == event.getKey()).forEach(macros -> Minecraft.getMinecraft().player.sendChatMessage(macros.getValue()));
    }
    public static final Color getClientColor() {
        return new Color(0, 0, 0, 255);
    }
}
