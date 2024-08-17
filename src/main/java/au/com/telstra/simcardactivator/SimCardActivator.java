package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimActivationController {

    private final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        // Create the payload for actuator
        SimActivationRequest actuatorRequest = new SimActivationRequest();
        actuatorRequest.setIccid(request.getIccid());

        // Send POST request to actuator
        ResponseEntity<SimActivationResponse> response = restTemplate.postForEntity(
                ACTUATOR_URL,
                actuatorRequest,
                SimActivationResponse.class
        );

        // Check the response from actuator
        if (response.getBody() != null && response.getBody().isSuccess()) {
            return ResponseEntity.ok("SIM card activated successfully.");
        } else {
            return ResponseEntity.status(500).body("SIM card activation failed.");
        }
    }
}
