package com.letmeknow.controller;

import com.letmeknow.model.Topic;
import com.letmeknow.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public Page<Topic> getTopics(@RequestParam(required = false, value = "sort", defaultValue = "name") String sort) {
        return topicService.getTopics(sort);
    }

    @GetMapping(path = "/{id}")
    public Optional<Topic> getOneTopic(@PathVariable("id") UUID id) {
        return topicService.getOneTopic(id);
    }

    @PostMapping
    public Topic registerNewTopic(@RequestBody Topic topic) {
        return topicService.addNewTopic(topic);
    }

    @PatchMapping(path = "/{topicID}")
    public Topic updateTopic(
            @PathVariable("topicID") UUID topicID,
            @RequestBody Topic topic) {
        return topicService.updateTopic(topicID, topic);
    }
}
