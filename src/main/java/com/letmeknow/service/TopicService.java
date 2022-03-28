package com.letmeknow.service;

import com.letmeknow.model.Topic;
import com.letmeknow.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Page<Topic> getTopics(String sort) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(sort));
        return topicRepository.findAll(pageable);
    }

    public Optional<Topic> getOneTopic(UUID id) {
        return exists(id);
    }

    public Topic addNewTopic(Topic topic) {
        var userByName = topicRepository.findByName(topic.getName());
        if (userByName.isPresent()) throw new IllegalStateException("Topic taken.");
        return topicRepository.save(topic);
    }

    public Topic updateTopic(UUID topicID, Topic topicForUpdate) {
        exists(topicID);
        var topicById = topicRepository.getById(topicID);
        final var forUpdateName = topicForUpdate.getName();
        if (areNotEqual(topicById.getName(), forUpdateName)) topicById.setName(forUpdateName);
        return topicRepository.save(topicById);
    }

    private <T> boolean areNotEqual(T first, T second) {
        return second != null && !Objects.equals(first, second);
    }

    private Optional<Topic> exists(UUID id) {
        var byId = topicRepository.findById(id);
        if (byId.isEmpty()) throw new IllegalStateException("User by Topic : " + id + " does not exists.");
        return byId;
    }
}
