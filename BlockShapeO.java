import java.awt.*;

public class BlockShapeO extends BlockShape {

    private Color color = Color.PINK;
    public BlockShapeO() {
        super();
        this.block = new int[][][]{
                {
                        {1, 1, 0, 0},
                        {1, 1, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                }
        };
    }



    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "O Shape " + color.toString() + " Block";
    }
}
