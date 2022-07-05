package org.dxticket.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dxticket.model.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventsConsumer {
    final static String API_EVENT_ENDPOINT = "http://localhost:9001/events";
    private static Logger log = LoggerFactory.getLogger(EventsConsumer.class);
    private final RestTemplate restTemplate =  new RestTemplate();

    //TODO IO Connection error handling

    public List<EventDTO> getEventList () {
        log.debug("Fetching eventlist");
        Object[] objects = restTemplate.getForObject(API_EVENT_ENDPOINT, Object[].class);
        ObjectMapper mapper = new ObjectMapper();

        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, EventDTO.class))
                .collect(Collectors.toList());
    }
}
