package Game.Sense.client.feature.impl.visual;

import Game.Sense.client.event.EventTarget;
import Game.Sense.client.event.events.impl.player.EventTransformSideFirstPerson;
import Game.Sense.client.feature.Feature;
import Game.Sense.client.feature.impl.FeatureCategory;
import Game.Sense.client.ui.settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class ViewModel extends Feature {
    public static NumberSetting rightx = new NumberSetting("RightX", 0, -2, 2, 0.1F, () -> true);
    public static NumberSetting righty = new NumberSetting("RightY", 0.2F, -2, 2, 0.1F, () -> true);
    public static NumberSetting rightz = new NumberSetting("RightZ", 0.2F, -2, 2, 0.1F, () -> true);
    public static NumberSetting leftx = new NumberSetting("LeftX", 0, -2, 2, 0.1F, () -> true);
    public static NumberSetting lefty = new NumberSetting("LeftY", 0.2F, -2, 2, 0.1F, () -> true);
    public static NumberSetting leftz = new NumberSetting("LeftZ", 0.2F, -2, 2, 0.1F, () -> true);

    public ViewModel() {
        super("ViewModel", "��������� ������������� ������� ��������� � ����", FeatureCategory.Visuals);
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
