package Game.Sense.client.module.feature.COMBAT;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.BooleanSetting;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AntiBot extends Module {
    public static List<Entity> isBotPlayer = new ArrayList<>();
    public ListSetting antiBotMode = new ListSetting("AntiBot Mode", "Matrix", () -> true, "Matrix", "Wellmore");
    public BooleanSetting removeWorld = new BooleanSetting("Remove from World", true, () -> antiBotMode.currentMode.equalsIgnoreCase("Wellmore"));

    public AntiBot() {
        super("AntiBot", "Удаляет ботов созданных анти-читом", ModuleCategory.COMBAT);
        addSettings();
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        for (Entity entity : mc.world.playerEntities) {
            if (antiBotMode.currentMode.equalsIgnoreCase("Matrix")) {
                if (!entity.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + entity.getName()).getBytes(StandardCharsets.UTF_8))) && entity instanceof EntityOtherPlayerMP) {
                    isBotPlayer.add(entity);
                }
            } else if (antiBotMode.currentMode.equalsIgnoreCase("Wellmore")) {
                for (Entity entity2 : mc.world.loadedEntityList) {
                    String Test = entity2.getDisplayName().getFormattedText();
                    if (!entity2.isInvisible() && entity2 != mc.player && entity2.ticksExisted <= 30 && mc.player.getDistanceToEntity(entity2) <= 8) {
                        if (!Test.startsWith("§7")) {
                            mc.world.removeEntity(entity2);
                        }
                    }
                }
            }
        }
    }
}

