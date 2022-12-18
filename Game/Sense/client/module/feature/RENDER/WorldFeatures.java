package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import net.minecraft.network.play.server.SPacketTimeUpdate;

import java.awt.*;

public class WorldFeatures extends Module {
    private long spinTime = 0;

    public static BooleanSetting snow = new BooleanSetting("Snow", true, () -> true);
    public static ColorSetting weatherColor = new ColorSetting("Weather", new Color(0xFFFFFF).getRGB(), () -> snow.getBoolValue());
    public static BooleanSetting worldColor = new BooleanSetting("World Color", false, () -> true);
    public static ColorSetting worldColors = new ColorSetting("Color World", new Color(0xFFFFFF).getRGB(), () -> worldColor.getBoolValue());
    public BooleanSetting ambience = new BooleanSetting("Ambience", false, () -> true);
    public ListSetting ambienceMode = new ListSetting("Ambience Mode", "Day", () -> ambience.getBoolValue(), "Day", "Night", "Morning", "Sunset", "Spin");
    public NumberSetting ambienceSpeed = new NumberSetting("Ambience Speed", 20.f, 0.1f, 1000.f, 1, () -> ambienceMode.currentMode.equals("Spin"));

    public WorldFeatures() {
        super("WorldFeatures", ModuleCategory.RENDER);
        addSettings(snow, weatherColor, worldColor, worldColors, ambience, ambienceMode, ambienceSpeed);
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (ambience.getBoolValue()) {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = ambienceMode.getOptions();
        if (ambience.getBoolValue()) {
            if (mode.equalsIgnoreCase("Spin")) {
                mc.world.setWorldTime(spinTime);
                this.spinTime = (long) (spinTime + ambienceSpeed.getNumberValue());
            } else if (mode.equalsIgnoreCase("Day")) {
                mc.world.setWorldTime(5000);
            } else if (mode.equalsIgnoreCase("Night")) {
                mc.world.setWorldTime(17000);
            } else if (mode.equalsIgnoreCase("Morning")) {
                mc.world.setWorldTime(0);
            } else if (mode.equalsIgnoreCase("Sunset")) {
                mc.world.setWorldTime(13000);
            }
        }
    }
}
