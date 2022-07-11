package org.dxticket.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dxticket.DTO.DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiConsumer {
    public final static String API_EVENT_ENDPOINT = "http://localhost:9001/events";
    public final static String API_VENUE_ENDPOINT = "http://localhost:9002/venue";

    private static Logger log = LoggerFactory.getLogger(ApiConsumer.class);
    private final RestTemplate restTemplate;
    private final String apiEndpoint;
    private final DTO dtoType;

    public ApiConsumer(String endpoint, DTO dtoType) {
        this.restTemplate =  new RestTemplate();
        this.apiEndpoint = endpoint;
        this.dtoType = dtoType;
    }

    public List<DTO> getList () {
        log.info("Fetching list of type {}", dtoType.getClass());
        Object[] objects = null;
        try {
           objects = restTemplate.getForObject(apiEndpoint, Object[].class);
        } catch (Throwable t) {
            log.info(t.getMessage());
        }
        log.info(String.valueOf(objects));
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, dtoType.getClass()))
                .collect(Collectors.toList());
    }
}
