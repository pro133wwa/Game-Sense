package Game.Sense.client.event.events.impl.player;

import Game.Sense.client.event.events.callables.EventCancellable;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventLiquidSolid
        extends EventCancellable {
    private final BlockLiquid blockLiquid;
    private final BlockPos pos;
    private AxisAlignedBB collision;

    public EventLiquidSolid(BlockLiquid blockLiquid, BlockPos pos) {
        this.blockLiquid = blockLiquid;
        this.pos = pos;
    }

    public BlockLiquid getBlock() {
        return this.blockLiquid;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public AxisAlignedBB setColision(AxisAlignedBB expand) {
        return this.collision;
    }
}
