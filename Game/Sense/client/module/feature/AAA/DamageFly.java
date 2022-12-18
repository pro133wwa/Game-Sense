package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;

public class DamageFly extends Module {

    boolean velocity;
    int ticks;
    double motion;
    boolean damage;
    boolean isVelocity;
    public NumberSetting maxTicks = new NumberSetting("Max Ticks", "Длительность флая", 24, 1, 27, 1, () -> true);

    public DamageFly() {
        super("DamageFly", ModuleCategory.MOVEMENT);
        addSettings(maxTicks);
    }

    @EventTarget
    private void onPacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if (((SPacketEntityVelocity) event.getPacket()).getMotionY() > 0) {
                isVelocity = true;
            }
            if ((((SPacketEntityVelocity) event.getPacket()).getMotionY() / 8000.0D) > 0.2) {
                motion = (((SPacketEntityVelocity) event.getPacket()).getMotionY() / 8000.0D);
                velocity = true;
            }
        }
    }

    @EventTarget
    private void onUpdate(EventPreMotion event) {
        if (mc.player.hurtTime == 9) {
            damage = true;
        }
        if (damage && isVelocity) {
            if (velocity) {
                mc.player.motionY = motion;
                mc.player.jumpMovementFactor = 0.415F;
                ticks++;
            }
            if (ticks >= 27) {
                isVelocity = false;
                velocity = false;
                damage = false;
                ticks = 0;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        damage = false;
        velocity = false;
        ticks = 0;
    }
}