import java.awt.*;

public class BlockShapeT extends BlockShape {


    private Color color = Color.magenta;
    public BlockShapeT() {
        super();
        this.block = new int[][][]{
                {
                        {0, 1, 0, 0},
                        {1, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 1, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {1, 0, 0, 0},
                        {1, 1, 0, 0},
                        {1, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 1, 0, 0},
                        {1, 1, 1, 0},
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
        return "T Shape " + color.toString() + " Block";
    }
}
