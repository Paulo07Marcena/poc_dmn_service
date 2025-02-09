package dmn_poc_service.application.controllers;

import dmn_poc_service.application.dtos.EventReceived;
import dmn_poc_service.domain.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public void postEvent(@RequestBody EventReceived event) {
        eventService.processEvent(event.toDomain());
    }
}
