import java.util.ArrayList;
import java.util.Random;

public class BlockGenerator {
    private static Random random = new Random();

    private ArrayList<BlockShape> blockCatalog = new ArrayList<BlockShape>();

    private ArrayList<Block> queue = new ArrayList<Block>();
    private final int initQueueInt = 5;

    BlockGenerator() {
        initBlockCatalog();
        initBlockQueue();
    }

    public void initBlockQueue() {
        for (int i=0; i<initQueueInt; i++) {
            createBlock();
        }
    }

    public void initBlockCatalog() {
        blockCatalog.add(new BlockShapeI());
        blockCatalog.add(new BlockShapeJ());
        blockCatalog.add(new BlockShapeL());
        blockCatalog.add(new BlockShapeO());
        blockCatalog.add(new BlockShapeS());
        blockCatalog.add(new BlockShapeT());
        blockCatalog.add(new BlockShapeZ());
    }

    public void createBlock() {
       queue.add(new Block(blockCatalog.get(random.nextInt(blockCatalog.size()))));
    }

    public Block extractBlock(int x, int y) {
        Block popBlock = queue.get(0);
        queue.remove(0);

        createBlock();

        popBlock.setX(x);
        popBlock.setY(y);

        return popBlock;
    }
}
