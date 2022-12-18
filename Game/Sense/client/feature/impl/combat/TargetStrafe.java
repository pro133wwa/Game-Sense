package Game.Sense.client.feature.impl.combat;

import Game.Sense.client.GameSense;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventMove;
import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.BooleanSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import Game.Sense.client.utils.math.RotationHelper;
import Game.Sense.client.utils.movement.MovementUtils;
import net.minecraft.entity.EntityLivingBase;

public class TargetStrafe extends Feature {
    public NumberSetting tstrafeRange;
    public NumberSetting spd;
    public BooleanSetting autoJump;
    public BooleanSetting damageBoost;
    public NumberSetting boostValue;

    public static int direction = -1;

    public TargetStrafe() {
        super("TargetStrafe", "Стрефит вокруг энтети", FeatureCategory.Combat);
        tstrafeRange = new NumberSetting("Strafe Distance", "Дистанция по которой вы будите стрейфить", 2.4F, 0.1F, 6.0F, 0.1F, () -> true);
        spd = new NumberSetting("Strafe Speed", 0.23F, 0.1F, 6, 0.01F, () -> true);
        autoJump = new BooleanSetting("AutoJump", true, () -> true);
        damageBoost = new BooleanSetting("DamageBoost", false, () -> true);
        boostValue = new NumberSetting("Boost Value", 0.5f, 0.1f, 4.0f, 0.01f, () -> damageBoost.getBoolValue());
        addSettings(tstrafeRange, spd, damageBoost, boostValue, autoJump);
    }

    @EventTarget
    public void onMotionUpdate(EventMove e) {
        EntityLivingBase entity = KillAura.target;
        float[] rotations = RotationHelper.getTargetRotations(entity);
        if (mc.player.getDistanceToEntity(entity) <= tstrafeRange.getNumberValue()) {
            if (mc.player.hurtTime > 0 && damageBoost.getBoolValue()) {
                MovementUtils.setMotion(e, spd.getNumberValue() + boostValue.getNumberValue(), rotations[0], direction, 0.0);
            } else {
                MovementUtils.setMotion(e, spd.getNumberValue(), rotations[0], direction, 0.0);
            }
        } else if (mc.player.hurtTime > 0 && damageBoost.getBoolValue()) {
            MovementUtils.setMotion(e, spd.getNumberValue() + boostValue.getNumberValue(), rotations[0], direction, 1.0);
        } else {
            MovementUtils.setMotion(e, spd.getNumberValue(), rotations[0], direction, 1.0);
        }
    }

    @EventTarget
    public void onUpdate(EventPreMotion event) {
        if (mc.player.isCollidedHorizontally)
            switchDirection();
        if (mc.gameSettings.keyBindLeft.isPressed())
            direction = 1;
        if (mc.gameSettings.keyBindRight.isPressed())
            direction = -1;
        if (KillAura.target.getHealth() > 0) {
            if (autoJump.getBoolValue() && GameSense.instance.featureManager.getFeature(KillAura.class).isEnabled() && GameSense.instance.featureManager.getFeature(TargetStrafe.class).isEnabled()) {
                if (mc.player.onGround) {
                    mc.player.jump();
                }
            }
        }
    }

    @EventTarget
    public void onSwitchDir(EventUpdate event) {
        if (KillAura.target == null)
            return;
        if (mc.player.isCollidedHorizontally)
            switchDirection();
        if (mc.gameSettings.keyBindLeft.isKeyDown())
            direction = 1;
        if (mc.gameSettings.keyBindRight.isKeyDown())
            direction = -1;
    }

    private void switchDirection() {
        direction = direction == 1 ? -1 : 1;
    }

    @EventTarget
    public void onMove(EventMove e) {
        if (GameSense.instance.featureManager.getFeature(KillAura.class).isEnabled() && KillAura.target != null && KillAura.target.getHealth() > 0) {
            if (mc.player.isCollidedHorizontally)
                switchDirection();
            if (KillAura.target.getHealth() > 0.0F)
                onMotionUpdate(e);

        }
    }
}


