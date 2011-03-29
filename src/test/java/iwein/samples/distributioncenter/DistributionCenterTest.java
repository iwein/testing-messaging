package iwein.samples.distributioncenter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
        Message<?> message = MessageBuilder.withPayload("foo").build();
        orders.send(message);
        assertThat(passedOrders.receive().getPayload(), is(message.getPayload()));
    }

}
