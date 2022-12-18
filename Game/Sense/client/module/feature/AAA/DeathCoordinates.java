package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventUpdate;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.dop.Notif.NotificationMode;
import Game.Sense.client.UI.dop.Notif.NotificationRenderer;
import Game.Sense.client.Helper.Utility.other.ChatUtils;
import net.minecraft.client.gui.GuiGameOver;

public class DeathCoordinates
        extends Module {
    public DeathCoordinates() {
        super("DeathCoordinates", "ѕоказывает координаты смерти игрока", ModuleCategory.OTHER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.getHealth() < 1.0f && mc.currentScreen instanceof GuiGameOver) {
            int x = mc.player.getPosition().getX();
            int y = mc.player.getPosition().getY();
            int z = mc.player.getPosition().getZ();
            if (mc.player.deathTime < 1) {
                NotificationRenderer.queue("Death Coordinates", "X: " + x + " Y: " + y + " Z: " + z, 10, NotificationMode.INFO);
                ChatUtils.addChatMessage("Death Coordinates: X: " + x + " Y: " + y + " Z: " + z);
            }
        }
    }
}
