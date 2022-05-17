public class CommandMoveRight extends Command{
    public CommandMoveRight(Block controlBlock) {
        super(controlBlock);
    }

    @Override
    public void execute() {
        getControlBlock().moveRight();
    }
}
