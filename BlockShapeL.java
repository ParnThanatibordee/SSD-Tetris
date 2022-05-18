import java.awt.*;

public class BlockShapeL extends BlockShape {
    private Color color = Color.CYAN;

    public BlockShapeL() {
        super();
        this.block = new int[][]{
                {1,0},
                {1,0},
                {1,1},
        };
    }

    @Override
    public Color getColor() {
        return color;
    }
}
