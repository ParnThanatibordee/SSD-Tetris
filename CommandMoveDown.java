public class CommandMoveDown extends Command{
    public CommandMoveDown(Block controlBlock) {
        super(controlBlock);
    }

    @Override
    public void execute() {
        getControlBlock().movedown();
    }
}
