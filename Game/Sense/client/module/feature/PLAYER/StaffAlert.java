package Game.Sense.client.module.feature.PLAYER;

import Game.Sense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.packet.EventReceivePacket;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;

import java.util.ArrayList;

import static Game.Sense.client.Helper.Utility.Helper.mc;

public class StaffAlert extends Module {
    public StaffAlert() {
        super("StaffAlert", "????????? ? ??????/??????? ?? ???????",  ModuleCategory.PLAYER);
    }

    public static ArrayList<EntityPlayer> staff = new ArrayList();

    private boolean isJoined;
    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        SPacketPlayerListItem packetPlayInPlayerListItem;
        if (event.getPacket() instanceof SPacketPlayerListItem && (packetPlayInPlayerListItem = (SPacketPlayerListItem) event.getPacket()).getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            this.isJoined = true;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        for (EntityPlayer staffPlayer : GuiPlayerTabOverlay.getPlayers()) {
            if (staffPlayer == null || staffPlayer == mc.player || !staffPlayer.getDisplayName().getUnformattedText().contains("HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ADMIN") && !staffPlayer.getDisplayName().getUnformattedText().contains("?????") && !staffPlayer.getDisplayName().getUnformattedText().contains("??????") && !staffPlayer.getDisplayName().getUnformattedText().contains("?????") || staffPlayer.ticksExisted >= 10 || !this.isJoined)
                continue;
            for (EntityPlayer s : staff) {
                if (s.getName().equalsIgnoreCase(staffPlayer.getName())) {
                    return;
                }
            }
            if (mc.world == null || mc.player == null) {
                staff.remove(staffPlayer);
            } else {
                staff.add(staffPlayer);
            }
            ChatUtils.addChatMessage(ChatFormatting.WHITE + "????????????? " + ChatFormatting.RESET + staffPlayer.getDisplayName().getUnformattedText() + ChatFormatting.WHITE + " ????? ?? ?????? / ????? ?? ??????");
            this.isJoined = false;
        }
    }
}