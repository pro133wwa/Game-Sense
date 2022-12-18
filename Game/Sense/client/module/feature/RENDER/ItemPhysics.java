package Game.Sense.client.module.feature.RENDER;


import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;

public class ItemPhysics extends Module {
    public static NumberSetting physicsSpeed;

    public ItemPhysics() {
        super("ItemPhysics", ModuleCategory.RENDER);
                physicsSpeed = new NumberSetting("Physics Speed", 0.5F, 0.1F, 5.0F, 0.5F, () -> true);
        addSettings(physicsSpeed);
    }
}
