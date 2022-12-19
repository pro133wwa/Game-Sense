package Game.Sense.client;


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
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;

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
    private static Logger logger;

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
