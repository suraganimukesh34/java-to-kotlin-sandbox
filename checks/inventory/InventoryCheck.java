import com.example.inventory.Category;
import com.example.inventory.ColdWarehouse;
import com.example.inventory.InsufficientStockException;
import com.example.inventory.InventoryService;
import com.example.inventory.Item;
import com.example.inventory.StockListener;
import com.example.inventory.StockNotifier;
import com.example.inventory.StockRepository;
import com.example.inventory.StockUtils;
import com.example.inventory.Warehouse;

import java.util.List;

/**
 * Behavior check for the inventory module, written as Java so it exercises the module the way
 * Java callers do. It must pass against the original Java and against the Kotlin conversion.
 */
public class InventoryCheck {
    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.out.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) throws Exception {
        StockRepository repo = new StockRepository();
        Item apple = new Item("a1", "Apple", Category.FOOD, 10);
        Item saw = new Item("t1", "Saw", Category.TOOLS, 3);
        repo.add(apple);
        repo.add(saw);
        repo.add(null);
        check(repo.findById("a1") == apple, "findById returns the stored item");
        check(repo.findById("zz") == null, "findById returns null for an unknown id");
        check(repo.findByCategory(Category.FOOD).size() == 1, "findByCategory finds the food item");
        check(repo.findByCategory(Category.CLOTHES).isEmpty(), "findByCategory is empty for an unused category");
        check(repo.lowStock(5).size() == 1 && repo.lowStock(5).get(0).getId().equals("t1"), "lowStock finds the saw");
        check(repo.total(Category.FOOD) == 10 && repo.total(Category.CLOTHES) == 0, "total per category");

        check(new Item("a1", "Other", Category.TOOLS, 99).equals(apple), "items are equal when their ids match");
        check(!new Item("a2", "Apple", Category.FOOD, 10).equals(apple), "items differ when their ids differ");
        check(apple.hashCode() == "a1".hashCode(), "hashCode is the id's hashCode");
        check(apple.toString().equals("Item(a1 x10)"), "toString format");
        check(apple.withQuantity(4).getQuantity() == 4 && apple.getQuantity() == 10, "withQuantity copies");

        check(Category.fromCode("F") == Category.FOOD, "fromCode finds a category");
        check(Category.fromCode("?") == null, "fromCode returns null for an unknown code");
        check(Category.FOOD.isPerishable() && !Category.TOOLS.isPerishable(), "perishable flags");
        check(Category.FOOD.getCode().equals("F"), "category code");

        check(StockUtils.LOW_STOCK_THRESHOLD == 5, "threshold constant");
        check(StockUtils.isLow(saw) && !StockUtils.isLow(apple), "isLow");
        check(StockUtils.label(apple).equals("Apple [F]"), "label");

        Warehouse cold = new ColdWarehouse(new StockRepository());
        check(cold.getName().equals("cold"), "warehouse name");
        check(cold.store(apple) && !cold.store(saw) && !cold.store(null), "cold warehouse stores only perishables");
        check(cold.count() == 10, "warehouse count");
        Warehouse custom = new Warehouse("custom", new StockRepository()) {
            @Override
            public boolean canStore(Item item) {
                return true;
            }
        };
        check(custom.store(saw) && custom.count() == 3, "Java can still subclass the abstract Warehouse");

        InventoryService service = new InventoryService(repo);
        StockNotifier notifier = new StockNotifier();
        service.addListener(notifier.asListener());
        service.addListener(new StockListener() {
            @Override
            public void onLowStock(Item item) {
            }

            @Override
            public void onOutOfStock(Item item) {
            }
        });
        Item after = service.remove("a1", 6);
        check(after.getQuantity() == 4, "remove returns the updated item");
        check(repo.findById("a1").getQuantity() == 4, "remove updates the repository");
        check(notifier.getMessages().equals(List.of("low:a1")), "a low-stock notification is sent");
        try {
            service.remove("a1", 50);
            check(false, "removing too much must throw InsufficientStockException");
        } catch (InsufficientStockException e) {
            check(e.getShortBy() == 46 && e.getMessage().equals("Not enough Apple"), "exception details");
        }
        try {
            service.remove("nope", 1);
            check(false, "removing an unknown item must throw");
        } catch (IllegalArgumentException e) {
            check(e.getMessage().equals("Unknown item: nope"), "unknown item message");
        }
        service.remove("a1", 4);
        check(notifier.getMessages().equals(List.of("low:a1", "out:a1")), "an out-of-stock notification is sent");

        System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
        System.exit(failures == 0 ? 0 : 1);
    }
}
