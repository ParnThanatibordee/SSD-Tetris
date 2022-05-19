import java.awt.*;

public class BlockShapeI extends BlockShape {
    private Color color = Color.RED;
    public BlockShapeI() {
        super();
        this.block = new int[][]{
                {1,1,1,1},
        };
    }

    @Override
    public Color getColor() {
        return color;
    }

    public String getShape() {
        return "I Shape";
    }

}