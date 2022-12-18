package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPostAttackSilent;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.render.SoundUtils;

public class HitSounds
        extends Module {
    private final ListSetting soundMode = new ListSetting("Sound Mode", "NeverLose", () -> true, "NeverLose", "Gachi", "UwU");
    private final NumberSetting volume = new NumberSetting("Volume", 50.0f, 1.0f, 100.0f, 1.0f, () -> true);

    public HitSounds() {
        super("HitSounds", "������������� ���� ��� �����", ModuleCategory.COMBAT);
        this.addSettings(this.soundMode, this.volume);
    }

    @EventTarget
    public void onSuffixUpdate(EventUpdate event) {
        this.setSuffix(this.soundMode.getCurrentMode());
    }

    @EventTarget
    public void onPostAttack(EventPostAttackSilent event) {
        float volume = this.volume.getNumberValue() / 10.0f;
        if (KillAura.isBreaked) {
            return;
        }
        if (this.soundMode.currentMode.equals("NeverLose")) {
            SoundUtils.playSound("neverlose.wav", -30.0f + volume * 3.0f, false);
        } else if (this.soundMode.currentMode.equals("Gachi")) {
            SoundUtils.playSound("ohh.wav", -30.0f + volume * 3.0f, false);
        } else if (this.soundMode.currentMode.equals("UwU")) {
            SoundUtils.playSound("uwu.wav", -30.0f + volume * 3.0f, false);
        }

    }
}
