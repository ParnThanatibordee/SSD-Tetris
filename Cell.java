import java.awt.*;

public class Cell {
    private boolean covered;

    public Cell() {
        covered = false;
    }
    public boolean isCovered() {
        return covered;
    }
    public void setCovered(boolean covered) {
        this.covered = covered;
    }
}
