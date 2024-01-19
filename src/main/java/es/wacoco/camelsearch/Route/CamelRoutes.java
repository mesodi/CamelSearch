package es.wacoco.camelsearch.Route;

import es.wacoco.camelsearch.Processor.ContactDataProcessor;
import es.wacoco.camelsearch.Processor.PatentDataProcessor;
import es.wacoco.camelsearch.Processor.SearchProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoutes extends RouteBuilder {
    @Override
    public void configure() {

        //Search Route
        from("direct:search")
                .process(new SearchProcessor())
                .to("log:searchLog?level=INFO");

        // Patent Route
        from("direct:patent")
                .to("http://localhost:3000/patent")
                .process(new PatentDataProcessor())
                .to("log:patentLog?level=INFO");

        // Contact Route
        from("direct:contact")
                .to("http://localhost:3000/contact")
                .process(new ContactDataProcessor())
                .to("log:contactLog?level=INFO");
    }
}

