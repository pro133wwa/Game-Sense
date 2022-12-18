package Game.Sense.client.UI.UwU.Compont;

import Game.Sense.client.UI.NursultanGui.component.Component;
import Game.Sense.client.UI.NursultanGui.component.impl.ModuleComponent;

import java.util.Comparator;

public class SorterHelper implements Comparator<Component> {

    @Override
    public int compare(Component component, Component component2) {
        if (component instanceof ModuleComponent && component2 instanceof ModuleComponent) {
            return component.getName().compareTo(component2.getName());
        }
        return 0;
    }
}