import java.awt.*;

public class BlockShapeJ extends BlockShape {

    private Color color = Color.BLUE;
    public BlockShapeJ() {
        super();
        this.block = new int[][]{
                {1,0,0},
                {1,1,1},
        };
    }



    @Override
    public Color getColor() {
        return color;
    }
}
