package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.math.GCDFix;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;

public class AntiAim extends Module {
    public float rot = 0.0F;
    public NumberSetting spinSpeed = new NumberSetting("Spin Speed", 1.0F, 0.0F, 10.0F, 0.1F, () -> {
        return true;
    });
    public BooleanSetting serverside = new BooleanSetting("Server-Side", false, () -> {
        return true;
    });
    public NumberSetting pitch = new NumberSetting("Pitch", 0.0F, -90.0F, 90.0F, 1.0F, () -> {
        return true;
    });

    public AntiAim() {
        super("AntiAim", "Крутилочка", ModuleCategory.PLAYER);
        addSettings(serverside, pitch, spinSpeed);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        float speed = this.spinSpeed.getNumberValue() * 10.0F;
        if (this.serverside.getBoolValue()) {
            event.setPitch(this.pitch.getNumberValue());
        }

        mc.player.rotationPitchHead = this.pitch.getNumberValue();
        float yaw = GCDFix.getFixedRotation((float) (Math.floor((double) this.spinAim(speed)) + (double) MathematicHelper.randomizeFloat(-4.0F, 1.0F)));
        if (this.serverside.getBoolValue()) {
            event.setYaw(GCDFix.getFixedRotation(yaw));
        }

        mc.player.renderYawOffset = yaw;
        mc.player.rotationYawHead = yaw;
    }

    public float spinAim(float rots) {
        this.rot += rots;
        return this.rot;
    }
}
