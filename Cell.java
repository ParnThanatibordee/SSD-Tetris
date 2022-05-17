public class Cell {
    private boolean covered;
    private int blockColor;

    public Cell() {
        covered = false;

        blockColor = 0;
    }
    public boolean isCovered() {
        return covered;
    }
    public void setCovered(boolean covered) {
        this.covered = covered;
    }
    public int getBlockColor() {
        return blockColor;
    }
    public void setBlockColor(int blockColor) {
        this.blockColor = blockColor;
    }

}

