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
        Object[] objects = restTemplate.getForObject(apiEndpoint, Object[].class);
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, dtoType.getClass()))
                .collect(Collectors.toList());
    }
}
