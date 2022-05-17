import java.awt.*;

public class BlockShapeI extends BlockShape {
    private Color color = Color.RED;
    public BlockShapeI() {
        super();
        this.block = new int[][][]{
                {
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0}
                },
                {
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
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
        return "I Shape " + color.toString() + " Block";
    }
}
