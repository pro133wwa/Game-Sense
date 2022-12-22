package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.Utility.Helper;
import Game.Sense.client.Helper.Utility.math.MathematicHelper;
import Game.Sense.client.Helper.Utility.math.TimerHelper;
import Game.Sense.client.Helper.events.impl.packet.EventAttackSilent;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Criticals extends Module {

    private final BooleanSetting jump;
    public static ListSetting critMode = new ListSetting("Criticals Mode", "Packet", () -> true, "Packet", "WatchDog", "Ncp", "Matrix Packet 14", "Test");
    private final TimerHelper ncpTimer = new TimerHelper();

    public Criticals() {
        super("Criticals", "Автоматически наносит сущности критичиский урон при ударе", ModuleCategory.COMBAT);
        critMode = new ListSetting("Criticals Mode", "Packet", () -> true, "Packet", "WatchDog", "Ncp", "Matrix Packet 14", "Test");
        jump = new BooleanSetting("Mini-Jump", false, () -> !critMode.currentMode.equals("Matrix Packet 14"));
        addSettings(critMode, jump);
    }

    @EventTarget
    public void onAttackSilent(EventAttackSilent event) {
        String mode = critMode.getOptions();
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (mode.equalsIgnoreCase("Packet")) {
            if (jump.getBoolValue()) {
                Minecraft.player.setPosition(x, y + 0.04, z);
            }
            Helper.sendPacket(new CPacketPlayer.Position(x, y + 0.11, z, false));
            Helper.sendPacket(new CPacketPlayer.Position(x, y + 0.1100013579, z, false));
            Helper.sendPacket(new CPacketPlayer.Position(x, y + 0.0000013579, z, false));
            Minecraft.player.onCriticalHit(event.getTargetEntity());
        } else if (mode.equalsIgnoreCase("Matrix Packet 14")) {
            Minecraft.player.onGround = false;
            double yMotion = 1.0E-12;
            Minecraft.player.fallDistance = (float) yMotion;
            Minecraft.player.motionY = yMotion;
            Minecraft.player.isCollidedVertically = true;

        } else if (mode.equalsIgnoreCase("Test")) {

            Minecraft.player.onGround = false;
            double yMotion = 1.0E-12;
            Minecraft.player.fallDistance = (float) yMotion;
            Minecraft.player.motionY = yMotion;
            Minecraft.player.isCollidedVertically = true;
        } else if (mode.equalsIgnoreCase("Ncp")) {
            if (jump.getBoolValue()) {
                Minecraft.player.setPosition(x, y + 0.04, z);
            }
            if (Helper.timerHelper.hasReached(150) && Minecraft.player.onGround) {
                Helper.sendPacket(new CPacketPlayer.Position(x, y + 0.11, z, false));
                Helper.sendPacket(new CPacketPlayer.Position(x, y + 0.1100013579, z, false));
                Helper.sendPacket(new CPacketPlayer.Position(x, y + 0.0000013579, z, false));
                Minecraft.player.onCriticalHit(event.getTargetEntity());
                Helper.timerHelper.reset();
            }
        } else if (mode.equalsIgnoreCase("WatchDog")) {
            if (jump.getBoolValue()) {
                Minecraft.player.setPosition(x, y + 0.04, z);
            }
            double random = MathematicHelper.getRandomInRange(4.0E-7f, 4.0E-5f);
            if (Helper.timerHelper.hasReached(100)) {
                for (double value : new double[]{0.007017625 + random, 0.007349825 + random, 0.006102874 + random}) {
                    Minecraft.player.fallDistance = (float) value;
                    Minecraft.player.isCollidedVertically = true;
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Minecraft.player.posX, Minecraft.player.posY + value, Minecraft.player.posZ, Minecraft.player.rotationYaw, Minecraft.player.rotationPitch, false));
                    Helper.timerHelper.reset();
                }
            }
            Minecraft.player.onCriticalHit(event.getTargetEntity());
        }
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        String mode = critMode.getOptions();
        if (mode.equalsIgnoreCase("Test")) {
            if (Minecraft.player.onGround) {
                Minecraft.player.onGround = false;
                Minecraft.player.setPositionAndUpdate(Minecraft.player.posX, Minecraft.player.posY + MathematicHelper.round(0.2F, 0.2F), Minecraft.player.posZ);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = critMode.getOptions();
        setSuffix(mode);
    }
}