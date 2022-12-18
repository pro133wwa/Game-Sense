package Game.Sense.client.mine.cmd;

@FunctionalInterface
public interface Command {
    void execute(String... strings);
}