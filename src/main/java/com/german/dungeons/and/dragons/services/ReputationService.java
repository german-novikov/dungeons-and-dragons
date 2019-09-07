package com.german.dungeons.and.dragons.services;

import com.german.dungeons.and.dragons.model.Reputation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ReputationService {
    @Value("${dungeons.rest.url}")
    private String mainUrl;

    private final RestTemplate restTemplate;

    private static final String REPUTATION_ENDPOINT = "/investigate/reputation";

    public Reputation getReputation(String gameId){
        return restTemplate.postForObject(mainUrl + gameId + REPUTATION_ENDPOINT, null,Reputation.class);
    }

}
