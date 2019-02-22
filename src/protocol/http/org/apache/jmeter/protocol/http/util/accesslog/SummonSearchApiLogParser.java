package org.apache.jmeter.protocol.http.util.accesslog;

import java.util.StringTokenizer;

public class SummonSearchApiLogParser extends TCLogParser {

    /*
    A typical Summon search API access log entry looks like this:
    [22/Feb/2019:13:40:25 -0500][Session:2.0-SUMMON-SESSION-81b787ad4bebbf7e9adb50de702bab06] 200 1076ms 47338 10.29.80.53 HTTP/1.1 GET api1.ci.dc04.summon.pqe:8011/2.0.0/search?s.ff=IsFullText%2Cor%2C1%2C2&s.ff=IsScholarly%2Cor%2C1%2C2&s.ff=ContentType%2Cor%2CBook%3AeBook%3ABook+%2F+eBook%2C5&s.ff=Discipline%2Cor%2C1%2C5&s.ff=SubjectTerms%2Cor%2C1%2C5&s.ff=Language%2Cor%2C1%2C5&s.ff=Library%2Cor%2C1%2C5&s.rec.topic.max=2&s.rec.qs.max=8&s.rec.db.max=2&s.role=authenticated&s.ps=10&s.l=en&s.ho=t&s.hs=%3Cb%3E&s.he=%3C%2Fb%3E&s.pn=1&s.exp=true&s.light=true&s.q=spokane+washington&s.bx=true&s.rec.jt=true&s.ru=newspaper%3A5%2Cimage%3A26%2Creference%3A5&s.rff=PublicationDate%2C1920%3A2019&__request_id=cbd0cd49897640239fcf3599bf5e65cb XFF:172.24.169.68 XTC:172.24.169.68 XCC:172.24.169.68
     */

    public String cleanURL(String entry) {
        String url = entry;
        int count = 0;
        if (entry.contains("summon.pqe") && checkMethod(entry)) {
            StringTokenizer tokens = this.tokenize(entry, "\"");
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if (checkMethod(token)) {
                    StringTokenizer token2 = this.tokenize(token, " ");
                    while (token2.hasMoreTokens() && count < 9) {
                    	count+=1;
                        String t = (String) token2.nextElement();
                        if (t.equalsIgnoreCase(GET)) {
                            RMETHOD = GET;
                        } else if (t.equalsIgnoreCase(POST)) {
                            RMETHOD = POST;
                        } else if (t.equalsIgnoreCase(HEAD)) {
                            RMETHOD = HEAD;
                        }
                        url = t;
                        url = url.replaceFirst(".+/2.0.0/search\\?", "");
                    }
                    break;
                }
            }
            return url;
        }
        return url;
    }

}

