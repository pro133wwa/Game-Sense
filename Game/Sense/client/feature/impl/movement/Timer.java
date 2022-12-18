/*    */ package Game.Sense.client.feature.impl.movement;
/*    */
/*    */ import Game.Sense.client.GameSense;
/*    */ import Game.Sense.client.draggable.component.impl.DraggableTimer;
/*    */ import Game.Sense.client.event.EventTarget;
/*    */ import Game.Sense.client.event.events.impl.player.EventPreMotion;
/*    */ import Game.Sense.client.event.events.impl.render.EventRender2D;
/*    */ import Game.Sense.client.feature.Feature;
/*    */ import Game.Sense.client.feature.impl.FeatureCategory;
/*    */ import Game.Sense.client.feature.impl.hud.ClickGUI;
import Game.Sense.client.ui.settings.Setting;
/*    */ import Game.Sense.client.ui.settings.impl.BooleanSetting;
/*    */ import Game.Sense.client.ui.settings.impl.NumberSetting;
/*    */ import Game.Sense.client.utils.math.MathematicHelper;
/*    */ import Game.Sense.client.utils.movement.MovementUtils;
/*    */
/*    */ import Game.Sense.client.utils.render.ColorUtils2;
import Game.Sense.client.utils.render.RenderUtils;
import Game.Sense.client.utils.render.RoundedUtil;
/*    */ import java.awt.Color;
/*    */
/*    */ public class Timer
        /*    */   extends Feature {

    /* 21 */   public int ticks = 0;
    /*    */   public boolean active;
    /* 23 */   private final NumberSetting timerSpeed = new NumberSetting("Timer Amount", 2.0F, 0.1F, 10.0F, 0.1F, () -> Boolean.valueOf(true));
    /* 24 */   public static BooleanSetting smart = new BooleanSetting("Smart", false, () -> Boolean.valueOf(true));
    /*    */
    /*    */   public Timer() {
        /* 27 */     super("Timer", "Увеличивает скорость игры", FeatureCategory.Movement);
        /* 28 */     addSettings(new Setting[] { (Setting)this.timerSpeed, (Setting)smart });
        /*    */   }
    /*    */
    /*    */   @EventTarget
    /*    */   public void onPreUpdate(EventPreMotion preMotion) {
        /* 33 */     if (!smart.getBoolValue()) {
            /* 34 */       mc.timer.timerSpeed = this.timerSpeed.getNumberValue();
            /*    */     }
        /* 36 */     if (smart.getBoolValue()) {
            /* 37 */       if (this.ticks <= 50 && !this.active && MovementUtils.isMoving()) {
                /* 38 */         this.ticks++;
                /* 39 */         mc.timer.timerSpeed = this.timerSpeed.getNumberValue();
                /*    */       }
            /* 41 */       if (this.ticks == 50) {
                /* 42 */         this.active = true;
                /*    */       }
            /* 44 */       if (this.active) {
                /* 45 */         mc.timer.timerSpeed = 1.0F;
                /* 46 */         if (!MovementUtils.isMoving())
                    /* 47 */           this.ticks--;
                /*    */       }
            /* 49 */       if (this.ticks == 0) {
                /* 50 */         this.active = false;
                /*    */       }
            /*    */     }
        /*    */   }
    /*    */
    /*    */   @EventTarget
    /*    */   public void onRender(EventRender2D event2D) {
             if (smart.getBoolValue()) {
                 DraggableTimer dt = (DraggableTimer) GameSense.instance.draggableHUD.getDraggableComponentByClass(DraggableTimer.class);
                dt.setWidth(150);
                   dt.setHeight(25);
                            Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
                            Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());

            Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0,onecolor,twocolor);
            Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90,onecolor,twocolor );
            Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor,twocolor);
            Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor,twocolor);/* 61 */
                 RoundedUtil.drawGradientRound((dt.getX() - 50), dt.getY(), (100.0F - this.ticks * 2.0F), 10.0f, 4,gradientColor1.brighter(),gradientColor2.brighter(),gradientColor3.brighter(),gradientColor4.brighter());
                 mc.sfui18.drawCenteredString("" + MathematicHelper.round(100.0F - this.ticks * 2.0F, 1) + "%", dt.getX(), (dt.getY()+2), -1);
                 RenderUtils.drawRect2(dt.getX()-26, (dt.getY() + 14),52,11,Color.BLACK.getRGB());
                 mc.sfui18.drawCenteredString("Smart Timer",dt.getX(), (dt.getY() + 16),-1);
        }
          }
    /*    */
    /*    */   public void onDisable() {
        /* 68 */     super.onDisable();
        /* 69 */      mc.timer.timerSpeed = 1.0F;
        /*    */   }
    /*    */ }


/* Location:              C:\Users\ANiki\Downloads\Client(1).jar!\fun\rich\client\feature\impl\movement\Timer.class
 * Java compiler version: 18 (62.0)
 * JD-Core Version:       1.1.3
 */