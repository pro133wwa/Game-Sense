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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

import static Game.Sense.client.Helper.Utility.Helper.mc;

public class StaffAlert
        extends Module {
    private boolean isJoined;

    public StaffAlert() {
        super("StaffAlert", "\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u044f\u0435\u0442 \u0432\u0430\u0441, \u043a\u043e\u0433\u0434\u0430 \u0445\u0435\u043b\u043f\u0435\u0440 \u0438\u043b\u0438 \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440 \u0437\u0430\u0448\u0435\u043b \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440", ModuleCategory.PLAYER);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        SPacketPlayerListItem packetPlayInPlayerListItem;
        if (event.getPacket() instanceof SPacketPlayerListItem && (packetPlayInPlayerListItem = (SPacketPlayerListItem)event.getPacket()).getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            isJoined = true;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        for (EntityPlayer staffPlayer : GuiPlayerTabOverlay.getPlayers()) {
            if (staffPlayer == null || staffPlayer == Minecraft.player || !staffPlayer.getDisplayName().getUnformattedText().contains("HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("SHELPER-1") && !staffPlayer.getDisplayName().getUnformattedText().contains("SHELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("SHELPER-2") && !staffPlayer.getDisplayName().getUnformattedText().contains("MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("J.MODER") || staffPlayer.ticksExisted >= 10 && !staffPlayer.getDisplayName().getUnformattedText().contains("YT") || !isJoined) continue;
            ChatUtils.addChatMessage(TextFormatting.RED + "Staff " + staffPlayer.getName() + TextFormatting.WHITE + " was connect/vanish");
            NotificationRenderer.queue("Staff Alert", TextFormatting.RED + "Staff " + staffPlayer.getName() + TextFormatting.WHITE + " was connect/vanish", 10, NotificationMode.WARNING);
            isJoined = false;
        }
    }
}