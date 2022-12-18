package Game.Sense.client.module.feature.RENDER;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventTransformSideFirstPerson;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class ViewModel extends Module {
    public static NumberSetting rightx = new NumberSetting("RightX", 0, -2, 2, 0.1F, () -> true);
    public static NumberSetting righty = new NumberSetting("RightY", 0.2F, -2, 2, 0.1F, () -> true);
    public static NumberSetting rightz = new NumberSetting("RightZ", 0.2F, -2, 2, 0.1F, () -> true);
    public static NumberSetting leftx = new NumberSetting("LeftX", 0, -2, 2, 0.1F, () -> true);
    public static NumberSetting lefty = new NumberSetting("LeftY", 0.2F, -2, 2, 0.1F, () -> true);
    public static NumberSetting leftz = new NumberSetting("LeftZ", 0.2F, -2, 2, 0.1F, () -> true);

    public ViewModel() {
        super("ViewModel", "Позволяет редактировать позицию предметов в руке", ModuleCategory.RENDER);
        addSettings(rightx, righty, rightz, leftx, lefty, leftz);
    }

    @EventTarget
    public void onSidePerson(EventTransformSideFirstPerson event) {
        if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightx.getNumberValue(), righty.getNumberValue(), rightz.getNumberValue());
        }
        if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(-leftx.getNumberValue(), lefty.getNumberValue(), leftz.getNumberValue());
        }
    }
}
