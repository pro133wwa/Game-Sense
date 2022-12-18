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

public class StaffAlert extends Module {
    private boolean isJoined;

    public StaffAlert() {
        super("StaffAlert", ModuleCategory.PLAYER);
    }

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
            if (staffPlayer == null || staffPlayer == mc.player || !staffPlayer.getDisplayName().getUnformattedText().contains("HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.HELPER") && !staffPlayer.getDisplayName().getUnformattedText().contains("MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ST.MODER") && !staffPlayer.getDisplayName().getUnformattedText().contains("ADMIN") && !staffPlayer.getDisplayName().getUnformattedText().contains("Админ") && !staffPlayer.getDisplayName().getUnformattedText().contains("Хелпер") && !staffPlayer.getDisplayName().getUnformattedText().contains("Модер") || staffPlayer.ticksExisted >= 10 || !this.isJoined)
                continue;
            ChatUtils.addChatMessage(ChatFormatting.WHITE + "Администратор " + ChatFormatting.RESET + staffPlayer.getDisplayName().getUnformattedText() + ChatFormatting.WHITE + " зашел на сервер / вышел из ваниша");
            NotificationRenderer.queue("§6Staff Alert", ChatFormatting.WHITE + "Администратор " + ChatFormatting.RESET + staffPlayer.getDisplayName().getUnformattedText() + ChatFormatting.WHITE + " зашел на сервер / вышел из ваниша", 5, NotificationMode.WARNING);
            this.isJoined = false;
        }
    }
}
