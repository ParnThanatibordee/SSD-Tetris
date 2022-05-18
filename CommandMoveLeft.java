public class CommandMoveLeft extends Command{
    public CommandMoveLeft(Block controlBlock) {
        super(controlBlock);
    }

    @Override
    public void execute() {
        getControlBlock().moveleft();
    }
}
