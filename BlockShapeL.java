import java.awt.*;

public class BlockShapeL extends BlockShape {
    private Color color = Color.CYAN;

    public BlockShapeL() {
        super();
        this.block = new int[][][]{
                {
                        {1, 0, 0, 0},
                        {1, 0, 0, 0},
                        {1, 1, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 0, 1, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {1, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {1, 1, 1, 0},
                        {1, 0, 0, 0},
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
        return "L Shape " + color.toString() + " Block";
    }
}
