package Game.Sense.client.UI.dop.Notif;

import Game.Sense.client.GameSense;
import Game.Sense.client.module.feature.RENDER.ClickGUI;
import Game.Sense.client.module.feature.RENDER.Notifications;
import Game.Sense.client.UI.NursultanGui.ClickGuiScreen;
import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.Utility.render.ColorUtils2;
import Game.Sense.client.Helper.Utility.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NotificationRenderer implements Helper {
    private static final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<>();

    public static void queue(String title, String content, int second, NotificationMode type) {
        NOTIFICATIONS.add(new Notification(title, content, type, second * 1000, Minecraft.getMinecraft().neverlose500_18));
    }

    public static void publish(ScaledResolution sr) {
        if (GameSense.instance.featureManager.getFeature(Notifications.class).isEnabled() && Notifications.notifMode.currentMode.equalsIgnoreCase("Rect") && !mc.gameSettings.showDebugInfo && mc.world != null && !(mc.currentScreen instanceof ClickGuiScreen)) {
            if (!NOTIFICATIONS.isEmpty()) {
                int y = sr.getScaledHeight() - 40;
                double better;
                for (Notification notification : NOTIFICATIONS) {
                    better = Minecraft.getMinecraft().neverlose500_18.getStringWidth(notification.getTitle() + " " + notification.getContent());

                    if (!notification.getTimer().hasReached(notification.getTime() / 2)) {
                        notification.notificationTimeBarWidth = 126;
                    } else {

                    }

                    if (!notification.getTimer().hasReached(notification.getTime())) {
                        notification.x = sr.getScaledWidth() - 110;
                        notification.y = MathHelper.EaseOutBack((float) notification.y, (float) y, (float) (5 * GameSense.deltaTime()));
                    } else {
                        notification.x = sr.getScaledWidth() - 185;
                        NOTIFICATIONS.remove(notification);
                    }
                    GlStateManager.pushMatrix();
                    GlStateManager.disableBlend();



                    Color onecolor = new Color(ClickGUI.bgonecolor.getColorValue());
                    Color twocolor = new Color(ClickGUI.bgtwocolor.getColorValue());
                    Color gradientColor1 = ColorUtils2.interpolateColorsBackAndForth(15, 0,onecolor,twocolor);
                    Color gradientColor2 = ColorUtils2.interpolateColorsBackAndForth(15, 90,onecolor,twocolor );
                    Color gradientColor3 = ColorUtils2.interpolateColorsBackAndForth(15, 180, onecolor,twocolor);
                    Color gradientColor4 = ColorUtils2.interpolateColorsBackAndForth(15, 270, onecolor,twocolor);



                    RoundedUtil.drawGradientRound((float) notification.x-27, (float)notification.y - 14.0f,(float) notification.notificationTimeBarWidth, 17, 4,gradientColor1.brighter().brighter(),gradientColor2.brighter().brighter(),gradientColor3.brighter().brighter(),gradientColor4.brighter().brighter() );
                    RoundedUtil.drawGradientRound((float) notification.x-26, (float)notification.y - 13.0f,(float) notification.notificationTimeBarWidth, 15, 4,gradientColor1,gradientColor2,gradientColor3,gradientColor4 );

                    Minecraft.getMinecraft().rubik_18.drawString("Sense:", (float) (notification.x - 22), (float) (notification.y - 8), -1);
                    Minecraft.getMinecraft().nurik.drawString(notification.getContent(), (float) (notification.x + 10), (float) (notification.y - 8), -1);
                    GlStateManager.popMatrix();
                    y -= 30;
                }
            }
        }
    }
}