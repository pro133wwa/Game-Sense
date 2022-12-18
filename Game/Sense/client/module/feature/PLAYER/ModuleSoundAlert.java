package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class ModuleSoundAlert extends Module {
    public static ListSetting soundMode;
    public static NumberSetting volume;
    public static NumberSetting pitch;

    public ModuleSoundAlert() {
        super("ModuleSoundAlert", "Воспроизводит звуки включения/выключения модуля", ModuleCategory.PLAYER);
        soundMode = new ListSetting("Sound Mode", "Wav", () -> true, "Wav", "Button");
        volume = new NumberSetting("Volume", 50.0f, 1.0f, 100.0f, 1.0f, () -> true);
        pitch = new NumberSetting("Pitch", 2.0f, 0.5f, 2.0f, 0.1f, () -> ModuleSoundAlert.soundMode.currentMode.equals("Button"));
        this.addSettings(soundMode, volume, pitch);
    }
}
