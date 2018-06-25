package org.apache.jmeter.protocol.http.util.accesslog;

import java.util.StringTokenizer;

public class SummonTopicsLogParser extends TCLogParser {

    /*
    A typical Summon topics log entry looks like this:
    [24/Jun/2018:00:02:01 -0400][Session:-] 200 1ms 2502 10.28.244.10 HTTP/1.1 GET vip-topicfulfillment.prod.dc04.summon.pqe:8022/topic-fulfillment/content/lookup?topicIds=Kidney_stone-en&client=NS6RL9TH2K&lang=en
     */

    public String cleanURL(String entry) {
        String url = entry;
        if (entry.contains("summon.pqe") && checkMethod(entry)) {
            StringTokenizer tokens = this.tokenize(entry, "\"");
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if (checkMethod(token)) {
                    StringTokenizer token2 = this.tokenize(token, " ");
                    while (token2.hasMoreTokens()) {
                        String t = (String) token2.nextElement();
                        if (t.equalsIgnoreCase(GET)) {
                            RMETHOD = GET;
                        } else if (t.equalsIgnoreCase(POST)) {
                            RMETHOD = POST;
                        } else if (t.equalsIgnoreCase(HEAD)) {
                            RMETHOD = HEAD;
                        }
                        url = t;
                        url = url.replaceAll(".+[0-9]{4}", "");
                    }
                    break;
                }
            }
            return url;
        }
        // we return the original string
        return url;
    }

}

