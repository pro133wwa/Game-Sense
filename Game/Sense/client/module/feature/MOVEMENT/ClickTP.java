package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventManager;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventSendPacket;
import Game.Sense.client.Helper.events.impl.player.EventBlockInteract;
import Game.Sense.client.Helper.events.impl.render.EventRender3D;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ColorSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.COMBAT.KillAura;
import Game.Sense.client.module.feature.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ClickTP
        extends Module {
    public static NumberSetting maxBlockReachValue;
    public static BooleanSetting posESP;
    public static BooleanSetting autoDisable;
    public static ColorSetting posESPColor;
    private int x;
    private int y;
    private int z;
    private boolean wasClick;

    public ClickTP() {
        super("ClickTP", "\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0442 \u0432\u0430\u0441 \u043d\u0430 \u0442\u043e\u0447\u043a\u0443 \u043a\u0443\u0434\u0430 \u0432\u044b \u0437\u0430\u0436\u0430\u043b\u0438 \u041b\u041a\u041c", ModuleCategory.MOVEMENT);
        maxBlockReachValue = new NumberSetting("Max reach value", 120.0f, 10.0f, 500.0f, 10.0f, () -> true);
        autoDisable = new BooleanSetting("Auto Disable", true, () -> true);
        posESP = new BooleanSetting("Position ESP", true, () -> true);
        posESPColor = new ColorSetting("Color", Color.WHITE.getRGB(), posESP::getBoolValue);
        addSettings(maxBlockReachValue, autoDisable, posESP, posESPColor);
    }

    @Override
    public void onDisable() {
        x = (int) Minecraft.player.posX;
        y = (int) Minecraft.player.posY;
        z = (int) Minecraft.player.posZ;
        wasClick = false;
        super.onDisable();
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        if (Minecraft.player == null || Minecraft.world == null) {
            return;
        }
        if (Minecraft.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            Color color = new Color(posESPColor.color);
            BlockPos pos = Minecraft.objectMouseOver.getBlockPos();
            if (posESP.getBoolValue()) {
                GlStateManager.pushMatrix();
                //RenderHelper2.blockEsp(pos, color, true, 1.0, 1.0);
                GlStateManager.popMatrix();
            }
        }
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (Minecraft.player == null || Minecraft.world == null) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            if (Mouse.isButtonDown(0) && KillAura.target == null) {
                mc.player.motionY = 0.0201F;
                for(int i = 0 ; i < 30 ; i++) {
                    packet.x = x;
                    packet.y = y;
                    packet.z = z;
                }
                if (Minecraft.player.posX == (double) x && Minecraft.player.posY == (double)(y - 1) && Minecraft.player.posZ == (double) z) {
                    NotificationRenderer.queue("Click TP", TextFormatting.GREEN + "Successfully " + TextFormatting.WHITE + "teleported to " + TextFormatting.RED + "X: " + (int) Minecraft.player.posX + " Y: " + (int) Minecraft.player.posY + " Z: " + (int) Minecraft.player.posZ, 4, NotificationMode.SUCCESS);
                    if (autoDisable.getBoolValue()) {
                        toggle();
                        EventManager.unregister(this);
                    }
                }
            }
        }
    }

    @EventTarget
    public void onBlockInteract(EventBlockInteract event) {
        if (Minecraft.player == null || Minecraft.world == null) {
            return;
        }
        if (event.getPos() != null) {
            BlockPos pos = event.getPos();
            x = pos.getX();
            y = pos.getY();
            z = pos.getZ();
            wasClick = true;
        }
    }
}

