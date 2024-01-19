package es.wacoco.camelsearch.Service;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamelService {
    private final ProducerTemplate producerTemplate;

    public CamelService(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }


    public String CallSearchRoute(String firstname) {
        // This method calls the 'direct:search' route, passing 'firstname' as the body of the message.
        // The method expects a String response from the route.
        return producerTemplate.requestBody("direct:search", firstname, String.class);
    }

    public String CallPatentRoute() {
        // This method calls the 'direct:patent' route without passing any specific body.
        // The method expects a String response from the route.
        return producerTemplate.requestBody("direct:patent", null, String.class);
    }

    public String callContactRoute() {
        // This method calls the 'direct:contact' route without passing any specific body.
        // The method expects a String response from the route.
        return producerTemplate.requestBody("direct:contact", null, String.class);
    }

}
