import java.awt.*;

public class BlockShapeZ extends BlockShape {

    private Color color = Color.YELLOW;
    public BlockShapeZ() {
        super();
        this.block = new int[][]{
                {1, 1, 0},
                {0, 1, 1},
        };
    }

    @Override
    public Color getColor() {
        return color;
    }
}
