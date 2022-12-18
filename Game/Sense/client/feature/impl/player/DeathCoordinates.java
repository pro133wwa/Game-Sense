package Game.Sense.client.feature.impl.player;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventUpdate;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.notification.NotificationMode;
import Game.Sense.client.ui.notification.NotificationRenderer;
import Game.Sense.client.utils.other.ChatUtils;
import net.minecraft.client.gui.GuiGameOver;

public class DeathCoordinates
        extends Feature {
    public DeathCoordinates() {
        super("DeathCoordinates", "ѕоказывает координаты смерти игрока", FeatureCategory.Misc);
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
