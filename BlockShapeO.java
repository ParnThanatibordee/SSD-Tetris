import java.awt.*;

public class BlockShapeO extends BlockShape {

    private Color color = Color.PINK;

    public BlockShapeO() {
        super();
        this.block = new int[][]{
                {1,1},
                {1,1},


        };
    }



    @Override
    public Color getColor() {
        return color;
    }

    public String getShape() {
        return "O Shape";
    }
}
