import java.awt.*;

public class BlockShapeS extends BlockShape {

    private Color color = Color.ORANGE;
    public BlockShapeS() {
        super();
        this.block = new int[][][]{
                {
                        {1, 0, 0, 0},
                        {1, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 1, 1, 0},
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
        return "S Shape " + color.toString() + " Block";
    }
}
