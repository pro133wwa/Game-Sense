package Game.Sense.client.module.feature;

public enum ModuleCategory {
    COMBAT("COMBAT"),
    MOVEMENT("MOVEMENT"),
    RENDER("RENDER"),
    PLAYER("PLAYER");


    private final String displayName;

    ModuleCategory(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
