package iwein.samples.distributioncenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author iwein
 */
public class Delivery {

    private final List<Item> items = new ArrayList<Item>();

    public Delivery(Collection<Item> items) {
        this.items.addAll( items);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "items=" + items +
                '}';
    }
}
