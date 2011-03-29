package iwein.samples.distributioncenter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author iwein
 */
@ContextConfiguration(
        locations = {
                "classpath:TEST-distribution-center.xml",
                "classpath:distribution-center.xml"
        })
@RunWith(SpringJUnit4ClassRunner.class)
public class DistributionCenterTest {
    private static final Logger log = LoggerFactory.getLogger(DistributionCenterTest.class);

    @Autowired
    MessageChannel orders;

    @Autowired
    PollableChannel passedOrders;

    @Test
    public void shouldLoadContext() throws Exception {
        //just let Spring verify that the context is loaded properly
    }

    @Test
    public void shouldPassOrderThroughSystem() throws Exception {
        ItemReference item1 = new ItemReference("foo");
        ItemReference item2 = new ItemReference("bar");
        Order order = new Order(item1, item2);
        Message<?> message = MessageBuilder.withPayload(order).build();
        orders.send(message);
        assertThat((Delivery) passedOrders.receive().getPayload(), hasItemNamed("foo"));
    }

    private Matcher<Delivery> hasItemNamed(final String itemName) {
        return new BaseMatcher<Delivery>() {
            @Override
            public boolean matches(Object o) {
                if(o instanceof Delivery){
                    return ((Delivery)o).getItems().contains(new Item(itemName));
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("A delivery containing an item named ");
                description.appendValue(itemName);
            }
        };
    }

}
