import java.awt.*;

public class Block {

    public static int CELL_SIZE = 30;

    private int x;
    private int y;
    private boolean stopFall;

    private BlockShape shape;

    public Block(BlockShape shape) {
        this.setShape(shape);
        this.stopFall = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setShape(BlockShape shape) {
        this.shape = shape;
    }

    public BlockShape getShape() {
        return this.shape;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void movedown() {
        this.y++;
    }

    public void moveleft() {
        this.x--;
    }

    public void moveright() {
        this.x++;
    }

    public boolean isStopFall() {
        return stopFall;
    }

    public void setStopFall(boolean stopFall) {
        this.stopFall = stopFall;
    }

    public int getHeight() {
        return this.shape.getHeight();
    }

    public int getWidth() {
        return this.shape.getWidth();
    }

    @Override
    public String toString() {
        return "Block " + shape.getShape();
    }
}