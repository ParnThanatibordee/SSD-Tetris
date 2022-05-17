import java.awt.*;

public class Block {

    private static int CELL_SIZE = 30;

    private int x;
    private int y;

    private BlockShape shape;

    public Block(int x, int y, BlockShape shape) {
        this.setX(x);
        this.setY(y);
        this.setShape(shape);
    }

    public void render(Graphics g) {
        BlockShape shape = this.shape;
        for (int i = 0; i < shape.getHeight(); i++) {
            for (int j = 0; j < shape.getWidth(); j++) {
                if (shape.isBlock(i, j)) {
                    int xPos = (this.x + j) * CELL_SIZE;
                    int yPos = (this.y + i) * CELL_SIZE;

                    g.setColor(shape.getColor());
                    g.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.WHITE);
                    g.drawRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
                }
            }
        }
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

    public void tick() {
        this.movedown();
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

    public void moveBlocktostart() {
        this.x = 4;
        this.y = 0;
    }

    public boolean isStopFall() {
        return stopFall;
    }



}