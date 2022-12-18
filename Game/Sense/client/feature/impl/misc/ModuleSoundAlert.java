package Game.Sense.client.feature.impl.misc;

import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.ListSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;

public class ModuleSoundAlert extends Feature {
    public static ListSetting soundMode;
    public static NumberSetting volume;
    public static NumberSetting pitch;

    public ModuleSoundAlert() {
        super("ModuleSoundAlert", "Воспроизводит звуки включения/выключения модуля", FeatureCategory.Misc);
        soundMode = new ListSetting("Sound Mode", "Wav", () -> true, "Wav", "Button");
        volume = new NumberSetting("Volume", 50.0f, 1.0f, 100.0f, 1.0f, () -> true);
        pitch = new NumberSetting("Pitch", 2.0f, 0.5f, 2.0f, 0.1f, () -> ModuleSoundAlert.soundMode.currentMode.equals("Button"));
        this.addSettings(soundMode, volume, pitch);
    }
}
