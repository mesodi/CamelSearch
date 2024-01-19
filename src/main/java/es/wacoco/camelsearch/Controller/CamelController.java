package es.wacoco.camelsearch.Controller;

import es.wacoco.camelsearch.Service.CamelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//http://localhost:8080/swagger-ui.html
public class CamelController {
    private final CamelService camelService;

    public CamelController(CamelService camelService) {
        this.camelService = camelService;
    }
    @Operation(summary = "Get patent data", description = "Retrieves patent data from the patent route")
    @GetMapping("/patent")
    public String getCompanyData() {
        return camelService.CallPatentRoute();
    }

    @Operation(summary = "Get contact data", description = "Retrieves contact data from the contact route")
    @GetMapping("/contact")
    public String getContactData() {
        return camelService.callContactRoute();
    }

    @Operation(summary = "Search by name", description = "Searches for patent and contact data by name")
    @GetMapping("/search")
    public String searchByName(@RequestParam String firstname) {
        return camelService.CallSearchRoute(firstname);
    }
}
