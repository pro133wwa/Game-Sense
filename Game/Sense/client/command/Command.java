package Game.Sense.client.command;

@FunctionalInterface
public interface Command {
    void execute(String... strings);
}