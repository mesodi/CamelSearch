package es.wacoco.camelsearch.Processor;

import com.jayway.jsonpath.JsonPath;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.Map;

public class PatentDataProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        // 1. Retrieve the JSON response from the Exchange object (camel route).
        String jsonResponse = exchange.getIn().getBody(String.class);

        // 2. Use JsonPath to extract the list of patents from the JSON response.
        // The JsonPath expression "$.patents[*]" selects all elements in the "patents" array.
        List<Map<String, Object>> patents = JsonPath.read(jsonResponse, "$.patents[*]");

        // 3. Set the extracted list of patents as the body of the Exchange object.
        exchange.getIn().setBody(patents);
    }
}
