package com.kosinov.keycloak.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosinov.keycloak.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserCachedRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<User> findByUserName(String userName) {
        String stringValue = redisTemplate.opsForValue().get(userName);

        if (stringValue == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(objectMapper.readValue(stringValue, User.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public User save(User user) {
        try {
            redisTemplate.opsForValue().set(user.getUserName(), objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Scheduled(fixedDelay = 1L)
    public void clearAll() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys == null) {
            return;
        }

        System.out.printf("Из кэша будет удално %s записей", keys.size());

        redisTemplate.delete(keys);
    }

}
