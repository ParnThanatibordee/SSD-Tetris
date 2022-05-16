import java.util.ArrayList;
import java.util.Random;

public class BlockFactory {
    private ArrayList<Block> queue = new ArrayList<Block>();
    private ArrayList<BlockShape> blockCatalog = new ArrayList<BlockShape>();
    private Random random = new Random();
    private final int initQueueInt = 5;

    public BlockFactory() {
        // apply object pool ได้
        initBlockQueue();
    }

    public void initBlockQueue() {
        for (int i=0; i<initQueueInt; i++) {
            createBlock();
        }
    }

    public void createBlock() {
        queue.add(new Block(blockCatalog.get(random.nextInt(blockCatalog.size()))));
    }

    public Block extractBlock() {
        int popIndex = random.nextInt(queue.size());
        Block popBlock = queue.get(popIndex);
        queue.remove(popIndex);

        return popBlock;
    }
}
