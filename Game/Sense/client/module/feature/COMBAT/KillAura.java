package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventAttackSilent;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.UI.NursultanGui.component.PropertyComponent;
import Game.Sense.client.UI.NursultanGui.component.impl.BooleanSettingComponent;
import Game.Sense.client.UI.UwU.InventoryUtil;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.MultipleBoolSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.inventory.InvenotryUtil;
import Game.Sense.client.Helper.Utility.math.GCDFix;
import Game.Sense.client.Helper.Utility.math.KillauraUtils;
import Game.Sense.client.Helper.Utility.math.RotationHelper;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

public class KillAura extends Module {
    public static TimerHelper timerHelper = new TimerHelper();

    private int notiTicks;
    public static boolean isAttacking;
    TimerHelper shieldFixerTimer = new TimerHelper();
    public static float yawStatic, pitchStatic;
    public static boolean isBreaked;
    public static EntityLivingBase target;
    public static ListSetting clickMode = new ListSetting("Click Mode", "1.9", () -> true, "1.9", "1.8");
    public static NumberSetting minAPS = new NumberSetting("Min APS", "??????????? ?????????? ?????? ? ???????", 12.0f, 1.0f, 20.0f, 1.0f, () -> clickMode.currentMode.equals("1.8"), NumberSetting.NumberType.APS);
    public static NumberSetting maxAPS = new NumberSetting("Max APS", "???????????? ?????????? ?????? ? ???????", 13.0f, 1.0f, 20.0f, 1.0f, () -> clickMode.currentMode.equals("1.8"), NumberSetting.NumberType.APS);
    public static ListSetting rotationMode = new ListSetting("Rotation Mode", "Matrix", "Vanilla", "Matrix", "Sunrise", "Snap", "Custom");
    public static ListSetting typeMode = new ListSetting("Type Mode", "Single", "Single", "Switch");
    public static BooleanSetting silent = new BooleanSetting("Silent Aura", true);
    public static ListSetting sortMode = new ListSetting("Priority Mode", "Distance", () -> typeMode.currentMode.equalsIgnoreCase("Switch"), "Distance", "Health", "Crosshair", "Higher Armor", "Lowest Armor");
    public static NumberSetting fov = new NumberSetting("FOV", "????????? ????????????? ?????? ? ??????? ?? ?????? ??????? ??????", 180, 0, 180, 1);
    public static NumberSetting attackCoolDown = new NumberSetting("Attack CoolDown", "??????????? ???????? ?????", 0.85F, 0.1F, 1F, 0.01F, () -> !rotationMode.currentMode.equals("Snap") && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static NumberSetting range = new NumberSetting("AttackRange", "????????? ? ??????? ?? ?????? ??????? ??????", 3.6F, 3, 6, 0.01f);
    public static NumberSetting preAimRange = new NumberSetting("Pre Aim Range", "????? ????? ?????????? ?? ?????", 0.0f, 0.0f, 4.0f, 0.1f, () -> !rotationMode.currentMode.equals("Vanilla"));
    public static NumberSetting yawrandom = new NumberSetting("Yaw Random", 1.6f, 0.1f, 20, 0.01F, () -> rotationMode.currentMode.equals("Custom"));
    public static NumberSetting pitchRandom = new NumberSetting("Pitch Random", 1.6f, 0.1f, 20, 0.01F, () -> rotationMode.currentMode.equals("Custom"));
    public static BooleanSetting staticPitch = new BooleanSetting("Static Pitch", false, () -> rotationMode.currentMode.equals("Custom"));
    public static NumberSetting pitchHead = new NumberSetting("Pitch Head", 0.35f, 0.1f, 1.2f, 0.01F, () -> rotationMode.currentMode.equals("Custom"));
    public BooleanSetting rayCast = new BooleanSetting("RayCast", "????????? ???????? ?? ??????? ?? ???-???? ??????", true);
    public static BooleanSetting walls = new BooleanSetting("Walls", "????????? ???? ?????? ?????", true);
    public static BooleanSetting onlyCritical = new BooleanSetting("Only Critical", "???? ? ?????? ?????? ??? ?????", true, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
    public BooleanSetting spaceOnly = new BooleanSetting("Space Only", "Only Crits ????? ???????? ???? ????? ??????", false, () -> onlyCritical.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static BooleanSetting checkCrystals = new BooleanSetting("Check Crystal", "Only Crits ?? ????? ???????? ????? ????? ???????", false, () -> onlyCritical.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static NumberSetting radiusCrystals = new NumberSetting("Distance to Crystal", 3, 1, 8, 1, () -> checkCrystals.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static NumberSetting criticalFallDistance = new NumberSetting("Critical Fall Distance", "??????????? ????????? ?? ????? ??? ?????", 0.2F, 0.08F, 1F, 0.01f, () -> onlyCritical.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public BooleanSetting shieldFixer = new BooleanSetting("ShieldFixer", "???????? ??? ?? ????? ?????, ???????? ?????? Matrix", false, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
    public NumberSetting fixerDelay = new NumberSetting("Fixer Delay", "??????????? ??? ????? ??? ????? ??????????? (??? ??????, ??? ??? ????? ?????? ??????????)", 150.0f, 0.0f, 600.0f, 10.0f, () -> shieldFixer.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public BooleanSetting shieldDesync = new BooleanSetting("Shield Desync", false, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static BooleanSetting shieldBreaker = new BooleanSetting("ShieldBreaker", "????????????? ?????? ??? ??????????", true, () -> clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static BooleanSetting breakNotifications = new BooleanSetting("Break Notifications", true, () -> shieldBreaker.getCurrentValue() && clickMode.currentMode.equalsIgnoreCase("1.9"));
    public static BooleanSetting silentMove = new BooleanSetting("SilentMove", false);
    public static MultipleBoolSetting targetsSetting = new MultipleBoolSetting("Targets", new BooleanSetting("Players", true), new BooleanSetting("Mobs"), new BooleanSetting("Animals"), new BooleanSetting("Villagers"), new BooleanSetting("Invisibles", true));

    public KillAura() {
        super("KillAura", "????????????? ???????? ??????", ModuleCategory.COMBAT);
        addSettings(rotationMode, typeMode, sortMode, clickMode, minAPS, maxAPS, targetsSetting, fov, attackCoolDown, range, preAimRange, silent, rayCast, yawrandom, pitchRandom, pitchHead, staticPitch, walls, onlyCritical, spaceOnly, checkCrystals, radiusCrystals, criticalFallDistance, shieldBreaker, breakNotifications, shieldFixer, fixerDelay, shieldDesync, silentMove);
    }

    @EventTarget
    public void onFix(EventSendPacket event) {
        /* INTERACT FIX */
        if (target == null) return;

        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packetUseEntity = (CPacketUseEntity) event.getPacket();
            if ((packetUseEntity.getAction() == CPacketUseEntity.Action.INTERACT) || (packetUseEntity.getAction() == CPacketUseEntity.Action.INTERACT_AT)) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onEventPre(EventPreMotion event) {
        String mode = rotationMode.getOptions();
        setSuffix("" + mode);
        /* Sorting */
        target = KillauraUtils.getSortEntities();
        /* ????? ?????? */
        if (target == null) {
            return;
        }
        /* RayCast */
        if (!RotationHelper.isLookingAtEntity(false, yawStatic, pitchStatic, 0.06f, 0.06f, 0.06f, target, range.getCurrentValueInt() + preAimRange.getCurrentValue())) {
            return;
        }
        /* Only Critical */
        if (!(!mc.gameSettings.keyBindJump.isKeyDown() && spaceOnly.getCurrentValue() || KillauraUtils.checkCrystal() && checkCrystals.getCurrentValue() || mc.player.capabilities.isFlying)) {
            if (MovementUtils.airBlockAboveHead()) {
                if (!(mc.player.fallDistance >= criticalFallDistance.getCurrentValueInt() || !onlyCritical.getCurrentValue() || mc.player.isRiding() || mc.player.isOnLadder() || mc.player.isInLiquid() || mc.player.isInWeb)) {
                    return;
                }
            } else if (!(!(mc.player.fallDistance > 0.0f) || mc.player.onGround || !onlyCritical.getCurrentValue() || mc.player.isRiding() || mc.player.isOnLadder() || mc.player.isInLiquid() || mc.player.isInWeb)) {
                return;
            }
        }

        KillauraUtils.attackEntity(target);

    }

    public float prevYaw, prevPitch;

    @EventTarget
    public void onRotations(EventPreMotion event) {

        String mode = rotationMode.getOptions();

        if (target == null) {
            return;
        }

        if (!target.isDead) {

            /* ROTATIONS */
            float[] sunrise = getSunriseRots(target);
            float[] matrix = getRotations(target);

            float[] fake = RotationHelper.getRotations(target);

            float[] custom = RotationHelper.getCustomRotations(target);

            if (mode.equalsIgnoreCase("Matrix")) {
                if (silent.getCurrentValue()) {
                    event.setYaw(matrix[0]);
                    event.setPitch(matrix[1]);
                    yawStatic = matrix[0];
                    pitchStatic = matrix[1];
                    mc.player.renderYawOffset = matrix[0];
                    mc.player.rotationYawHead = matrix[0];
                    mc.player.rotationPitchHead = matrix[1];
                } else {
                    mc.player.rotationYaw = matrix[0];
                    mc.player.rotationPitch = matrix[1];
                }
            } else if (mode.equalsIgnoreCase("Sunrise")) {
                if (silent.getCurrentValue()) {
                    event.setYaw(sunrise[0]);
                    event.setPitch(sunrise[1]);
                    yawStatic = sunrise[0];
                    pitchStatic = sunrise[1];
                    mc.player.renderYawOffset = sunrise[0];
                    mc.player.rotationYawHead = sunrise[0];
                    mc.player.rotationPitchHead = sunrise[1];
                } else {
                    mc.player.rotationYaw = sunrise[0];
                    mc.player.rotationPitch = sunrise[1];
                }

            } else if (mode.equalsIgnoreCase("Custom")) {
                if (silent.getCurrentValue()) {
                    event.setYaw(custom[0]);
                    event.setPitch(custom[1]);
                    mc.player.renderYawOffset = custom[0];
                    mc.player.rotationYawHead = custom[0];
                    mc.player.rotationPitchHead = custom[1];
                } else {
                    mc.player.rotationYaw = fake[0];
                    mc.player.rotationPitch = fake[1];
                }
            }

        } else {
            yawStatic = mc.player.rotationYaw;
            pitchStatic = mc.player.rotationPitch;
        }
    }

    private static double getDist(Entity entity) {
        Vec3d vec = entity.getPositionVector().add(new Vec3d(0, MathHelper.clamp(entity.posY - mc.player.posY + mc.player.getEyeHeight(), 0, entity.height), 0));
        return mc.player.getPositionVector().add(new Vec3d(0, mc.player.height / 2, 0)).distanceTo(vec);
    }

    public static float[] getRotations(Entity entity) {
        Vec3d vec = entity.getPositionVector().add(new Vec3d(0, MathHelper.clamp(entity.getEyeHeight() * (getDist(entity) / (range.getCurrentValueInt() + entity.width)), 0.2, mc.player.getEyeHeight()), 0));
        double diffX = vec.xCoord - mc.player.posX;
        double diffY = vec.yCoord - (mc.player.posY + mc.player.getEyeHeight());
        double diffZ = vec.zCoord - mc.player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yawTo = (float) ((Math.toDegrees(Math.atan2(diffZ, diffX)) - 90) + GCDFix.getFixedRotation((float) (Math.sin(System.currentTimeMillis() / 30) * 2)));
        float pitchTo = (float) (-(Math.toDegrees(Math.atan2(diffY, dist))) + GCDFix.getFixedRotation((float) (Math.cos(System.currentTimeMillis() / 30) * 2)));
        yawTo = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yawTo - mc.player.rotationYaw));
        pitchTo = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitchTo - mc.player.rotationPitch));
        pitchTo = MathHelper.clamp(pitchTo, -90, 90);
        yawStatic = GCDFix.getFixedRotation(MathHelper.Rotate(yawStatic, yawTo, 90, 90));
        pitchStatic = GCDFix.getFixedRotation(MathHelper.Rotate(pitchStatic, pitchTo, 1, 12));
        return new float[]{yawStatic, pitchStatic};
    }

    public static float[] getSunriseRots(Entity entity) {
        Vec3d vec = entity.getPositionVector().add(new Vec3d(0, MathHelper.clamp(entity.getEyeHeight() * (getDist(entity) / (range.getCurrentValue() + entity.width)), 0.2, mc.player.getEyeHeight()), 0));
        double diffX = vec.xCoord - mc.player.posX;
        double diffY = vec.yCoord - (mc.player.posY + mc.player.getEyeHeight());
        double diffZ = vec.zCoord - mc.player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yawTo = (float) ((Math.toDegrees(Math.atan2(diffZ, diffX)) - 90) + GCDFix.getFixedRotation((float) (Math.sin(System.currentTimeMillis() / 30) * 2)));
        float pitchTo = (float) (-(Math.toDegrees(Math.atan2(diffY, dist))) + GCDFix.getFixedRotation((float) (Math.cos(System.currentTimeMillis() / 30) * 2)));
        yawTo = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yawTo - mc.player.rotationYaw));
        pitchTo = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitchTo - mc.player.rotationPitch));
        pitchTo = MathHelper.clamp(pitchTo, -90, 90);
        yawStatic = GCDFix.getFixedRotation(MathHelper.Rotate(yawStatic, yawTo, 89, 89));
        pitchStatic = GCDFix.getFixedRotation(MathHelper.Rotate(pitchStatic, pitchTo, 1f, 3f));
        return new float[]{yawStatic, pitchStatic};
    }

    @EventTarget
    public void onAttackSilent(EventAttackSilent eventAttackSilent) {
        /* SHIELD Fix */
        if (mc.player.isBlocking() && this.shieldFixerTimer.hasReached(fixerDelay.getCurrentValueInt()) && mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemShield && shieldFixer.getCurrentValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(900, 900, 900), EnumFacing.UP));
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
            this.shieldFixerTimer.reset();
        }
    }


    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (target == null) {
            return;
        }
        /* SHIELD Desync */
        if (shieldDesync.getCurrentValue() && mc.player.isBlocking() && target != null && mc.player.ticksExisted % 8 == 0) {
            mc.player.stopActiveHand();
        }
        if (shieldFixer.getCurrentValue()) {
            if (target.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    mc.player.stopActiveHand();
                }
            }
        }
    }

    @EventTarget
    public void onSound(EventReceivePacket sound) {
        if (breakNotifications.getCurrentValue()) {
            if (sound.getPacket() instanceof SPacketEntityStatus) {
                SPacketEntityStatus sPacketEntityStatus = (SPacketEntityStatus) sound.getPacket();
                if (sPacketEntityStatus.getOpCode() == 30) {
                    if (sPacketEntityStatus.getEntity(mc.world) == target) {
                        if (notiTicks < 2) {
                            NotificationRenderer.queue(TextFormatting.GREEN + "Shield-Breaker", "Successfully destroyed " + target.getName() + " shield", 2, NotificationMode.SUCCESS);
                        } else {
                            notiTicks = 0;
                        }
                    }
                }
            }
        }
    }

    public static void BreakShield(EntityLivingBase tg) {
        if (InvenotryUtil.doesHotbarHaveAxe() && shieldBreaker.getCurrentValue()) {
            int item = InvenotryUtil.getAxe();
            if (InvenotryUtil.getAxe() >= 0 && tg instanceof EntityPlayer && tg.isHandActive() && tg.getActiveItemStack().getItem() instanceof ItemShield) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(item));
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            }
        }
    }

    @Override
    public void onDisable() {
        target = null;
        super.onDisable();
    }
}
