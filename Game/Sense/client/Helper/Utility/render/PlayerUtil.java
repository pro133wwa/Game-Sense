package Game.Sense.client.Helper.Utility.render;

import Game.Sense.client.Helper.Utility.Helper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil implements Helper {
    public static Block getBlock(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.world.getBlockState(new BlockPos(offsetX, offsetY, offsetZ)).getBlock();
    }


}
