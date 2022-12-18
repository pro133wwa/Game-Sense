/*    */ package Game.Sense.client.module.feature.AAA;
/*    */
/*    */ import Game.Sense.client.Helper.EventTarget;
/*    */ import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
/*    */ import Game.Sense.client.module.Module;
/*    */
import Game.Sense.client.module.feature.ModuleCategory;
/*    */ import Game.Sense.client.UI.Settings.Setting;
/*    */ import Game.Sense.client.UI.Settings.impl.BooleanSetting;
/*    */ import Game.Sense.client.Helper.Utility.inventory.InvenotryUtil;
/*    */ import Game.Sense.client.Helper.Utility.movement.MovementUtils;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */
/*    */
/*    */
/*    */ public class ReallyWorldElytra
        /*    */   extends Module
        /*    */ {
    /* 20 */   public BooleanSetting autojump = new BooleanSetting("AutoJump", "Автоматически прыгает", false, () -> Boolean.valueOf(true));
    /*    */
    /*    */
    /* 23 */   public BooleanSetting isAirBorne = new BooleanSetting("AutoElytra", "Автоматически открывает крылья(работает при прыжке/спрыгивание с блока)", false, () -> Boolean.valueOf(true));
    /*    */
    /*    */
    /* 26 */   public BooleanSetting autofly = new BooleanSetting("AutoFly", "Автоматически взлетает за вас", false, () -> Boolean.valueOf(true));
    /*    */
    /*    */
    /*    */
    /*    */   public ReallyWorldElytra() {
        /* 31 */     super("ReallyWorldElytra", "Флай на ReallyWorld Elytra", ModuleCategory.MOVEMENT);
        /* 32 */     addSettings(new Setting[] { (Setting)this.isAirBorne, (Setting)this.autojump, (Setting)this.autofly });
        /*    */   }
    /*    */
    /*    */   @EventTarget
    /*    */   public void onPreMotion(EventPreMotion eventPreMotion) {
        /* 37 */     if (this.autofly.getBoolValue() && mc.player.isElytraFlying()) {
            /* 38 */       mc.player.jump();
            /* 39 */       if (this.autofly.getBoolValue() && mc.player.isElytraFlying() && mc.player.isSneaking()) {
                /* 40 */         mc.player.motionY = -0.5D;
                /*    */       }
            /*    */     }
        /*    */
        /* 44 */     if (this.autojump.getBoolValue() && mc.player.onGround) {
            /* 45 */       mc.player.jump();
            /*    */     }
        /*    */
        /* 48 */     if (this.isAirBorne.getBoolValue() && mc.player.isAirBorne && mc.player.getFoodStats().getFoodLevel() / 2 > 3) {
            /* 49 */       mc.player.setSprinting(MovementUtils.isMoving());
            /* 50 */       boolean elytra = false;
            /* 51 */       mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            /*    */     }
        /*    */   }
    /*    */
    /*    */
    /*    */   public void onDisable() {
        /* 57 */     InvenotryUtil.swapElytraToChestplate();
        /* 58 */     mc.player.setSprinting(false);
        /* 59 */     super.onDisable();
        /*    */   }
    /*    */ }
