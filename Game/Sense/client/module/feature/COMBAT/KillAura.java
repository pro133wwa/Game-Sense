package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventAttackSilent;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
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
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

public class KillAura extends Module {
    /*  42 */   public static TimerHelper timerHelper = new TimerHelper();
    /*     */   public static float yaw;
    /*     */   public float pitch;
    /*  45 */   public float pitch2;
    /*     */   private int notiTicks;
    /*     */   public static boolean isAttacking;
    /*  48 */   TimerHelper shieldFixerTimer = new TimerHelper();
    /*  49 */   public float yaw2;
    /*     */   public static boolean isBreaked;
    /*     */   public static EntityLivingBase target;
    /*  52 */   public static ListSetting rotationMode = new ListSetting("Rotation Mode", "Matrix", () -> Boolean.valueOf(true), "Vanilla", "Matrix", "Sunrise", "Snap", "Custom");
    /*  53 */   public static ListSetting typeMode = new ListSetting("Type Mode", "Single", () -> Boolean.valueOf(true), "Single", "Switch");
    /*  54 */   public static ListSetting sortMode = new ListSetting("Priority Mode", "Distance", () -> Boolean.valueOf(typeMode.currentMode.equalsIgnoreCase("Switch")), "Distance", "Health", "Crosshair", "Higher Armor", "Lowest Armor");
    /*  55 */   public static NumberSetting fov = new NumberSetting("FOV", "��������� ������������� ������ � ������� �� ������ ������� ������", 180.0F, 0.0F, 180.0F, 1.0F, () -> Boolean.valueOf(true));
    /*  56 */   public static NumberSetting attackCoolDown = new NumberSetting("Attack CoolDown", "����������� �������� �����", 0.85F, 0.1F, 1.0F, 0.01F, () -> Boolean.valueOf(!rotationMode.currentMode.equals("Snap")));
    /*  57 */   public static NumberSetting range = new NumberSetting("AttackRange", "��������� � ������� �� ������ ������� ������", 3.6F, 3.0F, 6.0F, 0.01F, () -> Boolean.valueOf(true));
    /*  58 */   public static NumberSetting yawrandom = new NumberSetting("Yaw Random", 1.6F, 0.1F, 20.0F, 0.01F, () -> Boolean.valueOf(rotationMode.currentMode.equals("Custom")));
    /*  59 */   public static NumberSetting pitchRandom = new NumberSetting("Pitch Random", 1.6F, 0.1F, 20.0F, 0.01F, () -> Boolean.valueOf(rotationMode.currentMode.equals("Custom")));
    /*  60 */   public static BooleanSetting staticPitch = new BooleanSetting("Static Pitch", false, () -> Boolean.valueOf(rotationMode.currentMode.equals("Custom")));
    /*  61 */   public static NumberSetting pitchHead = new NumberSetting("Pitch Head", 0.35F, 0.1F, 1.2F, 0.01F, () -> Boolean.valueOf(rotationMode.currentMode.equals("Custom")));
    /*  62 */   public BooleanSetting rayCast = new BooleanSetting("RayCast", "��������� �������� �� ������� �� ���-���� ������", false, () -> Boolean.valueOf(true));
    /*  63 */   public static BooleanSetting walls = new BooleanSetting("Walls", "��������� ���� ������ �����", true, () -> Boolean.valueOf(true));
    /*  64 */   public static BooleanSetting onlyCritical = new BooleanSetting("Only Critical", "���� � ������ ������ ��� �����", false, () -> Boolean.valueOf(true));
    /*  65 */   public BooleanSetting spaceOnly = new BooleanSetting("Space Only", "Only Crits ����� �������� ���� ����� ������", false, () -> Boolean.valueOf(onlyCritical.getBoolValue()));
    /*  66 */   public NumberSetting criticalFallDistance = new NumberSetting("Critical Fall Distance", "����������� ��������� �� ����� ��� �����", 0.2F, 0.08F, 1.0F, 0.01F, () -> Boolean.valueOf(onlyCritical.getBoolValue()));
    /*  67 */   public BooleanSetting shieldFixer = new BooleanSetting("ShieldFixer", "�������� ��� �� ����� �����, �������� ������ Matrix", false, () -> Boolean.valueOf(true));
    /*  68 */   public NumberSetting fixerDelay = new NumberSetting("Fixer Delay", "����������� ��� ����� ��� ����� ����������� (��� ������, ��� ��� ����� ������ ����������)", 150.0F, 0.0F, 600.0F, 10.0F, () -> Boolean.valueOf(shieldFixer.getBoolValue()));
    /*  69 */   public BooleanSetting shieldDesync = new BooleanSetting("Shield Desync", false, () -> Boolean.valueOf(true));
    /*  70 */   public static BooleanSetting shieldBreaker = new BooleanSetting("ShieldBreaker", "������������� ������ ��� ����������", false, () -> Boolean.valueOf(true));
    /*  71 */   public static BooleanSetting breakNotifications = new BooleanSetting("Break Notifications", true, () -> Boolean.valueOf(shieldBreaker.getBoolValue()));
    /*  72 */   public static BooleanSetting silentMove = new BooleanSetting("SilentMove", false, () -> Boolean.valueOf(true));
    /*  73 */   //public static MultipleBoolSetting targetsSetting = new MultipleBoolSetting("Targets", new BooleanSettingComponent[] { new BooleanSettingComponent("Players", true), new BooleanSettingComponent("Mobs"), new BooleanSettingComponent("Animals"), new BooleanSettingComponent("Villagers"), new BooleanSettingComponent("Invisibles", true) });
    /* 74 */    public static BooleanSetting Players = new BooleanSetting("Players",true);
    /* 75 */    public static BooleanSetting Mobs = new BooleanSetting("Mobs",true);
    public static BooleanSetting Villagers = new BooleanSetting("Villagers",true);
    public static BooleanSetting Animals = new BooleanSetting("Animals",true);
    public static BooleanSetting Invisibles = new BooleanSetting("Invisibles",true);

    /*     */   public KillAura() {
        /*  76 */     super("Attack Aura", "������������� �������� ������", ModuleCategory.COMBAT);
        /*  77 */     addSettings(rotationMode, typeMode, sortMode, Players, Mobs, Villagers, Animals, Invisibles, fov, attackCoolDown, range, rayCast, yawrandom, pitchRandom, pitchHead, staticPitch, walls, onlyCritical, spaceOnly, criticalFallDistance, shieldBreaker, breakNotifications, shieldFixer, fixerDelay, shieldDesync, silentMove);
        /*     */   }
    /*     */
    /*     */
    /*     */   @EventTarget
    /*     */   public void onSendPacket(EventSendPacket event) {
        /*  83 */     if (event.getPacket() instanceof CPacketUseEntity) {
            /*  84 */       CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
            /*     */
            /*  86 */       if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT) {
                /*  87 */         event.setCancelled(true);
                /*     */       }
            /*     */
            /*  90 */       if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT_AT) {
                /*  91 */         event.setCancelled(true);
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */   @EventTarget
    /*     */   public void onPreAttack(EventPreMotion event) {
        /*  98 */     String mode = rotationMode.getOptions();
        /*     */
        /* 100 */     setSuffix("" + mode);
        /*     */
        /* 102 */
        target = KillauraUtils.getSortEntities();
        /*     */
        /*     */
        /* 105 */     if (target == null) {
            /*     */       return;
            /*     */     }
        /*     */
        /*     */
        /* 110 */     if (!rotationMode.currentMode.equals("Snap") && !RotationHelper.isLookingAtEntity(false, yaw, pitch, 0.12F, 0.12F, 0.12F, target, range.getNumberValue()) && rayCast.getBoolValue()) {
            /*     */       return;
            /*     */     }
        /*     */
        /*     */
        /* 115 */     Minecraft.player.jumpTicks = 0;
        /* 116 */     BlockPos blockPos = new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1D, Minecraft.player.posZ);
        /* 117 */     Block block = Minecraft.world.getBlockState(blockPos).getBlock();
        /* 118 */     float f2 = Minecraft.player.getCooledAttackStrength(0.5F);
        /* 119 */     boolean flag = (f2 > 0.9F);
        /* 120 */     if (!flag && onlyCritical.getBoolValue())
            /*     */       return;
        /* 122 */     if (Minecraft.gameSettings.keyBindJump.isKeyDown() || !spaceOnly.getBoolValue()) {
            /* 123 */       if (MovementUtils.airBlockAboveHead()) {
                /* 124 */         if (Minecraft.player.fallDistance < criticalFallDistance.getNumberValue() && !(block instanceof net.minecraft.block.BlockLiquid) && onlyCritical.getBoolValue() && !Minecraft.player.isRiding() && !Minecraft.player.isOnLadder() && !Minecraft.player.isInLiquid() && !Minecraft.player.isInWeb) {
                    /* 125 */           Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    /*     */           return;
                    /*     */         }
                /* 128 */       } else if (Minecraft.player.fallDistance > 0.0F && !Minecraft.player.onGround && onlyCritical.getBoolValue() && !Minecraft.player.isRiding() && !Minecraft.player.isOnLadder() && !Minecraft.player.isInLiquid() && !Minecraft.player.isInWeb) {
                /* 129 */         Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
                /*     */         return;
                /*     */       }
            /*     */     }
        /* 133 */     if (rotationMode.currentMode.equals("Snap") && Minecraft.player.getCooledAttackStrength(0.0F) >= attackCoolDown.getNumberValue()) {
            /* 134 */       float[] rots1 = RotationHelper.getRotations(target);
            /* 135 */       Minecraft.player.rotationYaw = rots1[0];
            /* 136 */       Minecraft.player.rotationPitch = rots1[1];
            /*     */     }
        /* 138 */     KillauraUtils.attackEntity(target);
        /*     */   }
    /*     */
    /*     */
    /*     */   @EventTarget
    /*     */   public void onRotations(EventPreMotion event) {
        /* 144 */     String mode = rotationMode.getOptions();
        /*     */
        /* 146 */     if (target == null) {
            /*     */       return;
            /*     */     }
        /*     */
        /* 150 */     if (!target.isDead) {
            /*     */
            /* 152 */       float[] matrix = RotationHelper.getRotations(target);
            /* 153 */       float[] fake = RotationHelper.getFakeRotations(target);
            /*     */
            /* 155 */       float[] custom = RotationHelper.getCustomRotations(target);
            /*     */
            /* 157 */       if (mode.equalsIgnoreCase("Matrix")) {
                /* 158 */         event.setYaw(matrix[0]);
                /* 159 */         event.setPitch(matrix[1]);
                /* 160 */
                yaw = matrix[0];
                /* 161 */         pitch = matrix[1];
                /* 162 */         Minecraft.player.renderYawOffset = matrix[0];
                /* 163 */         Minecraft.player.rotationYawHead = matrix[0];
                /* 164 */         Minecraft.player.rotationPitchHead = matrix[1];
                /* 165 */       } else if (mode.equalsIgnoreCase("Sunrise")) {
                /* 166 */         yaw2 = GCDFix.getFixedRotation(MathHelper.Rotate(yaw2, matrix[0], 40.0F, 50.0F));
                /* 167 */         pitch2 = GCDFix.getFixedRotation(MathHelper.Rotate(pitch2, matrix[1], 0.35F, 2.1F));
                /* 168 */         event.setYaw(yaw2);
                /* 169 */         event.setPitch(pitch2);
                /* 170 */
                yaw = yaw2;
                /* 171 */         pitch = pitch2;
                /* 172 */         Minecraft.player.renderYawOffset = fake[0];
                /* 173 */         Minecraft.player.rotationYawHead = fake[0];
                /* 174 */         Minecraft.player.rotationPitchHead = fake[1];
                /* 175 */       } else if (mode.equalsIgnoreCase("Custom")) {
                /* 176 */         event.setYaw(custom[0]);
                /* 177 */         event.setPitch(custom[1]);
                /* 178 */
                yaw = custom[0];
                /* 179 */         pitch = custom[1];
                /* 180 */         Minecraft.player.renderYawOffset = custom[0];
                /* 181 */         Minecraft.player.rotationYawHead = custom[0];
                /* 182 */         Minecraft.player.rotationPitchHead = custom[1];
                /*     */       }

            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */   @EventTarget
    /*     */   public void onAttackSilent(EventAttackSilent eventAttackSilent) {
        /* 191 */
        isAttacking = true;
        /* 192 */     if (Minecraft.player.isBlocking() && shieldFixerTimer.hasReached(fixerDelay.getNumberValue()) && Minecraft.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof net.minecraft.item.ItemShield && shieldFixer.getBoolValue()) {
            /* 193 */       Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(900, 900, 900), EnumFacing.UP));
            /* 194 */       Minecraft.playerController.processRightClick(Minecraft.player, Minecraft.world, EnumHand.OFF_HAND);
            /* 195 */       shieldFixerTimer.reset();
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */   @EventTarget
    /*     */   public void onUpdate(EventUpdate event) {
        /* 203 */     if (shieldDesync.getBoolValue() && Minecraft.player.isBlocking() && target != null && Minecraft.player.ticksExisted % 8 == 0) {
            /* 204 */       Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(900, 900, 900), EnumFacing.DOWN));
            /* 205 */       Minecraft.playerController.processRightClick(Minecraft.player, Minecraft.world, EnumHand.OFF_HAND);
            /*     */     }
        /* 207 */     if (shieldFixer.getBoolValue()) {
            /* 208 */       if (target.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemAxe) {
                /* 209 */         if (Minecraft.gameSettings.keyBindUseItem.isKeyDown()) {
                    /* 210 */           Minecraft.gameSettings.keyBindUseItem.pressed = false;
                    /*     */         }
                /*     */       } else {
                /* 213 */         Minecraft.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */   @EventTarget
    /*     */   public void onSound(EventReceivePacket sound) {
        /* 220 */     if (breakNotifications.getBoolValue() &&
                /* 221 */       sound.getPacket() instanceof SPacketEntityStatus) {
            /* 222 */       SPacketEntityStatus sPacketEntityStatus = (SPacketEntityStatus)sound.getPacket();
            /* 223 */       if (sPacketEntityStatus.getOpCode() == 30 &&
                    /* 224 */         sPacketEntityStatus.getEntity(Minecraft.world) == target) {
                /* 225 */         if (notiTicks < 2) {
                    /* 226 */
                    NotificationRenderer.queue(TextFormatting.GREEN + "Shield-Breaker", "Successfully destroyed " + target.getName() + " shield", 2, NotificationMode.SUCCESS);
                    /*     */         } else {
                    /* 228 */           notiTicks = 0;
                    /*     */         }
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */   public static void BreakShield(EntityLivingBase tg) {
        /* 237 */     if (InventoryUtil.doesHotbarHaveAxe() && shieldBreaker.getBoolValue()) {
            /* 238 */       int item = InventoryUtil.getAxe();
            /* 239 */       if (InventoryUtil.getAxe() >= 0 && tg instanceof EntityPlayer && tg.isHandActive() && tg.getActiveItemStack().getItem() instanceof net.minecraft.item.ItemShield) {
                /* 240 */         Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(item));
                /* 241 */         Minecraft.playerController.attackEntity(Minecraft.player, target);
                /* 242 */         Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                /* 243 */         Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */   public void onDisable() {
        /* 250 */
        target = null;
        /* 251 */     super.onDisable();
        /*     */   }
    /*     */ }
