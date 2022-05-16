import java.util.Random;

public class BlockGenerator {
    private static Random random = new Random();

    BlockGenerator() {
    }

    public static Block generateBlock(int x, int y) {
        int type = random.nextInt(7);
        if (type == 0) {
            return new Block(x, y, new BlockShapeI());
        } else if (type == 1) {
            return new Block(x, y, new BlockShapeJ());
        } else if (type == 2) {
            return new Block(x, y, new BlockShapeL());
        } else if (type == 3) {
            return new Block(x, y, new BlockShapeO());
        } else if (type == 4) {
            return new Block(x, y, new BlockShapeS());
        } else if (type == 5) {
            return new Block(x, y, new BlockShapeT());
        } else {
            return new Block(x, y, new BlockShapeZ());
        }
    }

    public static Block generateBlock(){
        return generateBlock(4, 0);
    }
}
