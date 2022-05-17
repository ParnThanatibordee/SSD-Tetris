import java.util.ArrayList;
import java.util.Random;

public class BlockFactory {
    private ArrayList<Block> queue = new ArrayList<Block>();
    private ArrayList<BlockShape> blockCatalog = new ArrayList<BlockShape>();
    private Random random = new Random();
    private final int initQueueInt = 5;

    public BlockFactory() {
        // apply object pool ได้
        initBlockCatalog();
        initBlockQueue();
    }

    public void initBlockQueue() {
        for (int i=0; i<initQueueInt; i++) {
            createBlock();
        }
    }

    public void initBlockCatalog() {

    }

    public void createBlock() {
        queue.add(new Block(x, y, blockCatalog.get(random.nextInt(blockCatalog.size()))));
    }

    public Block extractBlock() {
        Block popBlock = queue.get(0);
        queue.remove(0);

        createBlock();

        return popBlock;
    }
}
