package Game.Sense.client.feature.impl.movement;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.packet.EventReceivePacket;
import Game.Sense.client.event.events.impl.player.EventPreMotion;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.ListSetting;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import Game.Sense.client.utils.math.TimerHelper;
import Game.Sense.client.utils.movement.MovementUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;

public class Flight extends Feature {
    public boolean damage = false;
    public boolean flaging = false;
    public TimerHelper timerHelper = new TimerHelper();
    public static ListSetting flyMode = new ListSetting("Flight Mode", "Matrix Glide", () -> true, "Vanilla", "Matrix Elytra", "Matrix Glide", "Matrix Pearl","Matrix Jump");
    public final NumberSetting speed = new NumberSetting("Flight Speed", 5F, 0.1F, 15F, 0.1F, () -> flyMode.currentMode.equals("Vanilla") || flyMode.currentMode.equals("Matrix Glide"));
    public final NumberSetting motionY = new NumberSetting("Motion Y", 0.05f, 0.01f, 0.1f, 0.01F, () -> flyMode.currentMode.equals("Matrix Elytra"));

    public float ticks = 0;

    public Flight() {
        super("Flight", "Позволяет летать без креатив режима", FeatureCategory.Movement);
        addSettings(flyMode, motionY, speed);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        flaging = false;
        if (this.isEnabled()) {

            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                flaging = true;
            }
        }
    }


    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        String mode = flyMode.getOptions();
        setSuffix("" + mode);
        if (mode.equalsIgnoreCase("Matrix Elytra")) {
            int eIndex = -1;

            for (int i = 0; i < 45; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                    eIndex = i;
                }
            }

            mc.player.motionY = 0.37D;

            if (mc.player.ticksExisted % 4 == 0) {
                mc.playerController.windowClick(0, eIndex, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, eIndex, 1, ClickType.PICKUP, mc.player);
            }

            mc.player.jumpMovementFactor = 0.115F;
            if (flaging) {
                mc.player.jumpMovementFactor = 0;
                mc.player.motionY = -0.5D;

            }

        } else if (mode.equalsIgnoreCase("Matrix Glide")) {
            if (mc.player.onGround) {
                mc.player.jump();
                timerHelper.reset();
            } else if (!mc.player.onGround && timerHelper.hasReached(280)) {
                mc.player.motionY = -0.04D;

                MovementUtils.setSpeed(speed.getNumberValue());
            }
        } else if (mode.equalsIgnoreCase("Vanilla")) {
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.allowFlying = true;

            if (mc.gameSettings.keyBindJump.pressed) {
                mc.player.motionY = 2.0;
            }

            if (mc.gameSettings.keyBindSneak.pressed) {
                mc.player.motionY = -2.0;
            }
            MovementUtils.setSpeed(speed.getNumberValue());

        } else if (mode.equalsIgnoreCase("Matrix Pearl")) {
            if (mc.player.hurtTime > 0) {
                mc.player.motionY += 0.13;
                if (mc.gameSettings.keyBindForward.pressed && !mc.player.onGround) {
                    mc.player.motionX -= MathHelper.sin((float) Math.toRadians(mc.player.rotationYaw));
                    mc.player.motionZ += MathHelper.cos((float) Math.toRadians(mc.player.rotationYaw));
                }
            }
        } else if (mode.equalsIgnoreCase("Matrix Web")) {

            if (mc.player.isInWeb) {
                mc.player.isInWeb = false;
                mc.player.motionY *= mc.player.ticksExisted % 2 == 0 ? -100 : -0.05;
            }
        }   else if (mode.equalsIgnoreCase("Matrix Jump")) {
            if (this.mc.player.onGround) {
                /*  83 */         if (MovementUtils.isMoving()) {
                    /*  84 */           this.mc.player.jump();
                    /*     */         }
                /*     */       }
            /*  87 */       else if (MovementUtils.isMoving()) {
                /*  88 */         this.mc.player.motionY = 1.2f;
                /*  89 */         MovementUtils.setSpeed(2);
                /*     */       }


        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.allowFlying = false;
        mc.timer.timerSpeed = 1f;
    }
}