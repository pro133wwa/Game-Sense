package Game.Sense.client.module.feature.COMBAT.event;


import Game.Sense.client.Helper.events.callables.EventCancellable;
import net.minecraft.item.ItemStack;

public class EventItemEat extends EventCancellable {
    public EventItemEat(ItemStack stack) {itemStack = stack;}
    public ItemStack itemStack = null;
}
