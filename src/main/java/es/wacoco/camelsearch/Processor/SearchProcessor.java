package es.wacoco.camelsearch.Processor;

import net.minidev.json.JSONObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import java.util.List;
import java.util.Map;

public class SearchProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // 1. Retrieve the search term from the Exchange object.
        // The Exchange object is the carrier of the message within the Camel route.
        String searchName = exchange.getIn().getBody(String.class);

        // 2. Check if the search term is empty or null.
        if (searchName == null || searchName.trim().isEmpty()) {
            exchange.getIn().setBody("Search term cannot be empty.");
            return;
        }

        // 3. Create a ProducerTemplate to send requests to other routes.
        // The ProducerTemplate is a Camel utility that facilitates sending messages to endpoints.
        ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate();

        // 4. Fetch patent and contact data from their respective routes.
        // Here, we are programmatically invoking the 'patent' and 'contact' Camel routes and waiting for their responses.
        List<Map<String, Object>> patentData = producerTemplate.requestBody("direct:patent", null, List.class);
        List<Map<String, Object>> contactData = producerTemplate.requestBody("direct:contact", null, List.class);

        // 5. Filter the fetched patent data to find a record that matches the search term.
        // This is done by applying the filter logic defined in the filterPatentData method.
        Map<String, Object> filteredPatentData = filterPatentData(patentData, searchName);

        // 6. Find a matching contact record based on the search term.
        // Similarly, the contact data is filtered to find a matching record based on the name.
        Map<String, Object> matchedContactData = findMatchingContact(contactData, searchName);

        // 7. Construct a combined response with the filtered patent and contact data.
        // The response is built as a JSON object, combining data from both patent and contact searches.
        JSONObject combinedResult = new JSONObject();
        combinedResult.put("Patent", filteredPatentData);
        combinedResult.put("Contact", matchedContactData != null ? matchedContactData : "Sorry, no matching contact found for the person");

        // 8. Set the combined response as the body of the Exchange object.
        // The modified Exchange is then passed along in the Camel route, carrying the search results.
        exchange.getIn().setBody(combinedResult.toJSONString());
    }


    /**
     * Filters the list of patent data to find a patent where any inventor's name matches the given name.
     *
     * @param data The list of patent data maps.
     * @param name The name to search for among the inventors.
     * @return The first patent data map where the inventor's name matches the given name, or null if no match is found.
     */
    private Map<String, Object> filterPatentData(List<Map<String, Object>> data, String name) {
        return data.stream()
                .filter(map -> ((List<String>) map.get("inventors")).stream().anyMatch(inventor -> inventor.contains(name)))
                .findFirst()
                .orElse(null);
    }


    /**
     * Filters the list of contact data to find a contact where either the first name or last name matches the given name.
     *
     * @param data The list of contact data maps.
     * @param name The name to search for in the contact data.
     * @return The first contact data map where either the first name or last name matches the given name, or null if no match is found.
     */
    private Map<String, Object> findMatchingContact(List<Map<String, Object>> data, String name) {
        return data.stream()
                .filter(map -> map.get("firstname").toString().contains(name) || map.get("lastname").toString().contains(name))
                .findFirst()
                .orElse(null);
    }

}

