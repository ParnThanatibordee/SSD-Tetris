import java.awt.*;

public class Cell {
    private boolean covered;
    private boolean blocked;
    private int blockColor;

    public Cell() {
        covered = true;
        blocked = false;
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

