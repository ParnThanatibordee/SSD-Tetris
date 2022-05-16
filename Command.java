public abstract class Command {
    private Block controlBlock;

    public Command(Block controlBlock) {
        this.controlBlock = controlBlock;
    }

    public Block getControlBlock() {
        return controlBlock;
    }

    public abstract void execute();
}
