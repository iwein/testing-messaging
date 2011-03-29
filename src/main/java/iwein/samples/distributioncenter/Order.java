package iwein.samples.distributioncenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author iwein
 */
public class Order {
    private final List<ItemReference> items = new ArrayList<ItemReference>();

    public Order(ItemReference... items) {
        Collections.addAll(this.items, items);
    }

    public List<ItemReference> getItems() {
        return Collections.unmodifiableList(items);
    }
}
