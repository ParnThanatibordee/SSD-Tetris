import java.awt.*;

class Block {

    private static int CELL_SIZE = 30;

    private int x;
    private int y;
    private boolean stopFall;

    private BlockShape shape;

    public Block(BlockShape shape) {
        stopFall = false;
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
        this.moveDown();
    }

    public void moveDown() {
        this.y++;
    }

    public void moveLeft() {
        this.x--;
    }

    public void moveRight() {
        this.x++;
    }

    public void moveBlocktostart() {
        this.x = 4;
        this.y = 0;
    }

    public boolean isStopFall() {
        return stopFall;
    }

    public void setStopFall(boolean stopFall) {
        this.stopFall = stopFall;
    }

    public String toString() {
        return "" + shape;
    }
}