import java.awt.*;

public abstract class BlockShape {

    public int[][] block;
    private int currentShape;
    private Color color;

    public BlockShape() {
        this.setShape(0);
    }

    public void setShape(int shape) {
        currentShape = shape;
    }

    public int getCurrentShape() {
        return currentShape;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return block[0].length;
    }

    public int getHeight() {
        return block.length;
    }

    public boolean isBlock(int x, int y) {
        return block[x][y] == 1;
    }

    public String getShape() {
        return "Unknown Shape";
    }

}
