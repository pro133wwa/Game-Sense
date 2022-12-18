package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;



@SuppressWarnings("all")
public class Speed extends Module {
    public static float ticks = 0;
    public TimerHelper timerHelper = new TimerHelper();
    public static ListSetting speedMode = new ListSetting("Speed Mode", "Matrix New", () -> true, "ReallyWorld2","ReallyWorld","Matrix New","Sunrise Damage");
    private float jumps = 0;

    public Speed() {
        super("Speed", "Быстрая скорость бега", ModuleCategory.MOVEMENT);
        addSettings(speedMode);

    }



    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        String mode = speedMode.getOptions();
         if (mode.equalsIgnoreCase("NCPHop")) {
            if (MovementUtils.isMoving()) {
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.jump();
                    Helper.mc.player.speedInAir = 0.0223f;
                }
                MovementUtils.strafe();
            } else {
                Helper.mc.player.motionX = 0.0;
                Helper.mc.player.motionZ = 0.0;
            }
        }
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        String mode = speedMode.getOptions();
        if (mode.equalsIgnoreCase("Matrix")) {
            if (Helper.mc.player.isInWeb || Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid()) {
                return;
            }
            if (Helper.mc.player.onGround && !Helper.mc.gameSettings.keyBindJump.pressed) {
                Helper.mc.player.jump();
                Helper.mc.timer.timerSpeed = 1.3f;
            }
            if (Helper.mc.world.getCollisionBoxes(Helper.mc.player, Helper.mc.player.getEntityBoundingBox().offset(0.D, -0.5D, 0.D).expand(-0.001D, 0.D, -0.001D)).isEmpty() && Helper.mc.player.onGround) {
                Helper.mc.player.jump();
            }
            if (Helper.mc.world.getBlockState(new BlockPos(Helper.mc.player.posX, Helper.mc.player.posY - 1.1D, Helper.mc.player.posZ)).getBlock() != Blocks.AIR) {
                if (Helper.mc.player.motionY == -0.4448259643949201D) {
                    event.setOnGround(false);
                    Helper.mc.player.motionX *= 2.D;
                    Helper.mc.player.motionZ *= 2.D;
                    MovementUtils.setMotion(MovementUtils.getSpeed() * 1.D + Math.sin(MovementUtils.getAllDirection()) * 0.005D);
                }
            }
        } else if (mode.equalsIgnoreCase("Matrix New")) {
            if (mc.player.onGround) {
                mc.player.jump();
                mc.player.speedInAir = 0.0201F;
                mc.timer.timerSpeed = 0.9F;
            }
            if (mc.player.fallDistance > 0.6 && mc.player.fallDistance < 1.3) {
                mc.player.speedInAir = 0.02F;
                mc.timer.timerSpeed = 2F;
            }
        } else if (mode.equalsIgnoreCase("Sunrise Damage")) {
            if (MovementUtils.isMoving()) {
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 9.5D /20, 0, Math.cos(MovementUtils.getAllDirection()) * 9.5D / 20);
                    MovementUtils.strafe();
                } else if (Helper.mc.player.isInWater()) {
                    Helper.mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 9.5D / 24.5D, 0, Math.cos(MovementUtils.getAllDirection()) * 9.5D / 24.5D);
                    MovementUtils.strafe();
                } else {
                    Helper.mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 0.005D * MovementUtils.getSpeed(), 0, Math.cos(MovementUtils.getAllDirection()) * 0.005 * MovementUtils.getSpeed());
                    MovementUtils.strafe();

                }
            }
        } else if (mode.equalsIgnoreCase("ReallyWorld")) {
            if (MovementUtils.isMoving()) {
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.jump();
                }
                if (Helper.mc.player.fallDistance <= 0.2) {
                    Helper.mc.timer.timerSpeed = 2.88f;
                    Helper.mc.player.jumpMovementFactor = 0.02652f;
                } else if ((double) Helper.mc.player.fallDistance < 1.24) {
                    Helper.mc.timer.timerSpeed = 0.47f;
                }
                if (Helper.mc.player.motionY == -0.4478299643949201D);
            }

        } else if (mode.equalsIgnoreCase("ReallyWorld2")) {
            Helper.mc.timer.timerSpeed = 1.0f;

            if (!MovementUtils.isMoving() || Helper.mc.player.isInWater() || Helper.mc.player.isInLava() ||
                    Helper.mc.player.isOnLadder() || Helper.mc.player.isRiding()) { return;
            }
            if (Helper.mc.player.onGround) {
                Helper.mc.player.jump();
            }else {
                if (Helper.mc.player.fallDistance <= 0.1) {
                    Helper.mc.timer.timerSpeed = 1.3f;
                } else if (Helper.mc.player.fallDistance > 1.0) {
                    Helper.mc.timer.timerSpeed = 0.6f;
                } else
                    Helper.mc.timer.timerSpeed = 1f;
            }
        }else if (mode.equalsIgnoreCase("NCPYPort")) {
            if (MovementUtils.isMoving()) {
                Helper.mc.timer.timerSpeed = 1.05f;
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.motionY = 0.4f;
                    if (!Helper.mc.player.isPotionActive(Potion.getPotionById(1))) {
                        MovementUtils.setSpeed(0.34f);
                    } else {
                        if (Helper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() == 0) {
                            MovementUtils.setSpeed(0.49f);
                        }
                        if (Helper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() == 1) {
                            MovementUtils.setSpeed(0.575f);
                        }
                        if (Helper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() == 2) {
                            MovementUtils.setSpeed(0.73f);
                        }
                    }
                } else {
                    Helper.mc.player.motionY = -100.0;
                }
            }
        }

    }

    @Override
    public void onEnable() {
        if (speedMode.currentMode.equalsIgnoreCase("NCPHop")) {
            Helper.mc.timer.timerSpeed = 1.0865f;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Helper.mc.player.speedInAir = 0.02f;
        Helper.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}