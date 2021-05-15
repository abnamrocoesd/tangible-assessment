package com.abnamro.assessment.microservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/viewpersons")
public class MicroserviceController {

    @Value("${persons.api.url}")
    private String defaultURL;

    private static final Logger logger = LogManager.getLogger("MicroserviceController");

    @GetMapping
    public ResponseEntity<String> viewpersons() {
        String apiUrl = retrieveURLfromConfigMap();
        if(!StringUtils.hasText(apiUrl)){
            apiUrl = defaultURL;
        }
        logger.info("Use API-URL: {}", apiUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        return ResponseEntity.ok("Persons: " + response.getBody());
    }

    /**
     * Retrieve API-url
     * 
     * API-url can be preconfigured in a Kubernetes object (ConfigMap).
     * In this case it can be retrieved via env-variable, for example PERSONS_API_URL
     * (This is a very simple implementation)
     *
     * @return API Url
     */
    private String retrieveURLfromConfigMap() {
        String apiUrl = System.getenv("PERSONS_API_URL");
        return apiUrl;
    }


}