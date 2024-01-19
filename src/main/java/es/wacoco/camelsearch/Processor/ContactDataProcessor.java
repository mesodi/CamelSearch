package es.wacoco.camelsearch.Processor;

import com.jayway.jsonpath.JsonPath;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.Map;

public class ContactDataProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        // 1. Retrieve the JSON response from the Exchange object (camel route).
        String jsonResponse = exchange.getIn().getBody(String.class);

        // 2. Use JsonPath to extract the list of contacts from the JSON response.
        // The JsonPath expression "$.contacts[*]" selects all elements in the "contacts" array.
        List<Map<String, Object>> contacts = JsonPath.read(jsonResponse, "$.contacts[*]");

        // 3. Set the extracted list of contacts as the body of the Exchange object.
        exchange.getIn().setBody(contacts);
    }
}
