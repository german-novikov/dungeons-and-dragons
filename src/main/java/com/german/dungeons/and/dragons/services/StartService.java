package com.german.dungeons.and.dragons.services;

import com.german.dungeons.and.dragons.model.GameStartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class StartService {

    private final RestTemplate restTemplate;

    @Value("${dungeons.rest.url}")
    private String mainUrl;

    private static final String GAME_START_ENDPOINT = "game/start";

    public GameStartResponse statGame() {
        try {
            return restTemplate.postForObject(mainUrl + GAME_START_ENDPOINT, null, GameStartResponse.class);
        }catch (Exception ex) {
            log.error(String.format("Request exception: %s", ex));
            return new GameStartResponse();
        }
    }
}
