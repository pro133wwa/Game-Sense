package Game.Sense.client.module.feature.MOVEMENT;

import Game.Sense.client.Helper.EventTarget;


import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayer;

import java.util.Random;


public class KTLeave extends Module
{
    public static ListSetting KTLeavemod = new ListSetting("KTLeave Mode", "Random", () -> true, "Random","OneTP");
    public KTLeave() {
        super("KTLeave", "", ModuleCategory.MOVEMENT);
        addSettings(KTLeavemod);
    }

    public static int generateRandomNumber(int n) {
        return new Random().nextInt(n + 1);
    }


    @EventTarget
    public void onUpdate(final EventUpdate event) {
        String mode = KTLeavemod.getOptions();
        if (mode.equalsIgnoreCase("Random")) {
        int endX = generateRandomNumber(10000);
        int endZ = generateRandomNumber(10000);
        int endY = 200;
        if(mc.player.isSneaking() && mc.player.ticksExisted % 8 == 0) {
            ChatUtils.addChatMessage(endX + "-X   " + endY + "-Y   " + endZ + "-Z   ");
            if (mc.player.posX != endX && mc.player.posZ != endZ) {
                mc.player.motionY = 0.05f;
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5, endY, endZ - 0.5, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 109, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX + 0.5, endY, endZ - 0.5, true));
            }
        }else if (mode.equalsIgnoreCase("OneTP")) {
            float endX2 = 15900;
            float endZ2 = -1000;
            float endY2 = 70;
            if(mc.player.isSneaking() && mc.player.ticksExisted % 8 == 0) {
                ChatUtils.addChatMessage("  ћобилизирую на заданные мне координаты " + endX + " " + endY + " " + endZ);
                if (mc.player.posX != endX2 && mc.player.posZ != endZ2) {
                    mc.player.motionY = 0.05f;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(endX2 + 0.5, endY2, endZ2 - 0.5, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(endX2, endY2 + 109, endZ2, true));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(endX2 + 0.5, endY2, endZ2 - 0.5, true));
                }
            }
                }
             }
         }
    }

