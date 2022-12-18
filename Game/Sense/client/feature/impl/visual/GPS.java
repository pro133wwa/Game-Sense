package Game.Sense.client.feature.impl.visual;

import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.command.impl.GPSCommand;
import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.event.events.impl.render.EventRender2D;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.notification.NotificationMode;
import Game.Sense.client.ui.notification.NotificationRenderer;
import Game.Sense.client.utils.math.AnimationHelper;
import Game.Sense.client.utils.math.RotationHelper;
import Game.Sense.client.utils.other.ChatUtils;
import Game.Sense.client.utils.render.RenderUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GPS extends Feature {
    public GPS() {
        super("GPS", "Прокладывает путь до координат", FeatureCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (GPSCommand.mode.equalsIgnoreCase("on")) {
            setSuffix("" + GPSCommand.x + " " + GPSCommand.z);
        }
    }

    @EventTarget
    public void one(EventRender2D event2D) {
        if (GPSCommand.mode.equalsIgnoreCase("on")) {
            GL11.glPushMatrix();
            int x = event2D.getResolution().getScaledWidth() / 2;
            int y = event2D.getResolution().getScaledHeight() / 2;
            GL11.glTranslatef((float) x, (float) y, 0.0F);
            GL11.glRotatef(getAngle(new BlockPos(Integer.parseInt(String.valueOf(GPSCommand.x)), 0, Integer.parseInt(String.valueOf(GPSCommand.z)))) % 360.0F + 180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float) (-x), (float) (-y), 0.0F);
            RenderUtils.drawBlurredShadow((float) x - 3, (float) (y + 48), 5.0F, 10.0F, 15, new Color(255, 255, 255));
            RenderUtils.drawTriangle((float) x - 5, (float) (y + 50), 5.0F, 10.0F, new Color(255, 255, 255).darker().getRGB(), new Color(255, 255, 255).getRGB());
            GL11.glTranslatef((float) x, (float) y, 0.0F);
            GL11.glRotatef(-(getAngle(new BlockPos(Integer.parseInt(String.valueOf(GPSCommand.x)), 0, Integer.parseInt(String.valueOf(GPSCommand.z)))) % 360.0F + 180.0F), 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float) (-x), (float) (-y), 0.0F);
            GL11.glPopMatrix();
        }
    }

    public static float getAngle(BlockPos entity) {
        return (float) (RotationHelper.getRotations(entity.getX(), 0, entity.getZ())[0] - AnimationHelper.Interpolate(mc.player.rotationYaw, mc.player.prevRotationYaw, 1.0D));
    }

    @Override
    public void onEnable() {
        ChatUtils.addChatMessage(ChatFormatting.GREEN + "Как использовать? .gps <x> <y> <on/off>");
        NotificationRenderer.queue(TextFormatting.WHITE + "GPS", ChatFormatting.GREEN + "Как использовать? .gps <x> <y> <on/off>", 7, NotificationMode.INFO);

        super.onEnable();
    }
}
