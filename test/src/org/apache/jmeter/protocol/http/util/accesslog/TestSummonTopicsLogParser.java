package org.apache.jmeter.protocol.http.util.accesslog;

import static org.junit.Assert.*;

import org.apache.jmeter.junit.JMeterTestCase;
import org.hamcrest.CoreMatchers;
import org.junit.Test;



public class TestSummonTopicsLogParser extends JMeterTestCase {

	private static final SummonTopicsLogParser stlp = new SummonTopicsLogParser();
	
	private static final String URL1 = "[24/Jun/2018:00:02:01 -0400][Session:-] 200 1ms 2502 10.28.244.10 HTTP/1.1 GET vip-topicfulfillment.prod.dc04.summon.pqe:8022/topic-fulfillment/content/lookup?topicIds=Kidney_stone-en&client=NS6RL9TH2K&lang=en";
	
	@Test
    public void testcleanURL() throws Exception {
        String res = stlp.cleanURL(URL1);
        assertThat(res, CoreMatchers.startsWith("/topic-fulfillment/content/lookup?topicIds="));
  
    }
}
