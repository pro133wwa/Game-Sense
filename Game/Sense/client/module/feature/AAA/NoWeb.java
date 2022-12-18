package Game.Sense.client.module.feature.AAA;

import Game.Sense.client.Helper.EventTarget;
import Game.Sense.client.Helper.events.impl.player.EventMove;
import Game.Sense.client.Helper.events.impl.player.EventPreMotion;
import Game.Sense.client.module.Module;
import Game.Sense.client.module.feature.ModuleCategory;
import Game.Sense.client.UI.Settings.impl.ListSetting;
import Game.Sense.client.UI.Settings.impl.NumberSetting;
import Game.Sense.client.Helper.Utility.movement.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class NoWeb extends Module {

    public ListSetting noWebMode;
    public NumberSetting webSpeed;

    public NoWeb() {
        super("NoWeb", "Позволяет быстро ходить в паутине", ModuleCategory.PLAYER);
        noWebMode = new ListSetting("NoWeb Mode", "Matrix", () -> true, "Matrix", "Matrix New", "NCP");
        webSpeed = new NumberSetting("Web Speed", 0.8F, 0.1F, 2, 0.1F, () -> noWebMode.currentMode.equals("Matrix New"));
        addSettings(noWebMode, webSpeed);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        String mode = noWebMode.getOptions();
        this.setSuffix(mode);
        if (mode.equalsIgnoreCase("Matrix New")) {
            BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.6, mc.player.posZ);
            Block block = mc.world.getBlockState(blockPos).getBlock();
            if (mc.player.isInWeb) {
                mc.player.motionY += 2F;
            } else if (Block.getIdFromBlock(block) == 30) {
                MovementUtils.setSpeed(webSpeed.getNumberValue());
                if (mc.gameSettings.keyBindJump.isKeyDown())
                    return;
                mc.player.isInWeb = false;
                mc.gameSettings.keyBindJump.pressed = false;
            }
        }
    }

    @EventTarget
    public void onMove(EventMove event) {
        String mode = noWebMode.getOptions();
        this.setSuffix(mode);
        if (isEnabled()) {
            if (mode.equalsIgnoreCase("Matrix")) {
                if (mc.player.isInWeb) {
                    mc.player.motionY += 2F;
                } else {
                    if (mc.gameSettings.keyBindJump.isKeyDown())
                        return;
                    mc.player.isInWeb = false;
                }
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    return;
                }
                if (mc.player.isInWeb && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                    MovementUtils.setEventSpeed(event, 0.483);
                }
            } else if (mode.equalsIgnoreCase("NCP")) {
                if (mc.player.onGround && mc.player.isInWeb) {
                    mc.player.isInWeb = true;
                } else {
                    if (mc.gameSettings.keyBindJump.isKeyDown())
                        return;
                    mc.player.isInWeb = false;
                }
                if (mc.player.isInWeb && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                    MovementUtils.setSpeed(0.403);
                }
            }
        }
    }
}
