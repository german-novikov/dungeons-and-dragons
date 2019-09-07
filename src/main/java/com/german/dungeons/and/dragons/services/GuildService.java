package com.german.dungeons.and.dragons.services;

import com.german.dungeons.and.dragons.model.Result;
import com.german.dungeons.and.dragons.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuildService {

    private List<Task> messageboard;
    private Long numberOfEncryptedTasks = new Long(0);
    private Long numberOfHighRiskTask = new Long(0);

    @Value("${dungeons.rest.url}")
    private String mainUrl;

    private final RestTemplate restTemplate;

    private static final String MESSAGEBOARD_ENDPOINT = "/messages";
    private static final String SOLVE_TASK_ENDPOINT = "/solve/";

    private void viewMessageboard(String gameId){
        String url = mainUrl + gameId + MESSAGEBOARD_ENDPOINT;
        try{
            messageboard = Arrays.asList(restTemplate.getForObject(url, Task[].class));
        }catch (Exception ex){
            log.error("Can not see messageboard: " + ex);
        }

    }

    public Result solveTask(String gameId){
        String url = mainUrl + gameId + SOLVE_TASK_ENDPOINT + messageboard.get(0).getAdId();
        log.info(messageboard.get(0).getProbability().getTitle());
        try{
            return restTemplate.postForObject(url, null, Result.class);
        }catch (Exception ex) {
            log.error("Can not solve the task: " + messageboard.get(0).getAdId() + " stack: " + ex + " URL: " + url);
        }
        return null;
    }

    public void openMessageboard(String gameId){
        viewMessageboard(gameId);
        countEncryptedTask();
        countHighRiskTask();
        sortMessageBoard();
    }

    private void countEncryptedTask(){
        numberOfEncryptedTasks = messageboard.stream().filter(task -> task.getEncrypted() > 0).count();
    }

    private void sortMessageBoard(){
        messageboard = messageboard.stream().filter(task -> task.getEncrypted() == 0).collect(Collectors.toList());
        Collections.sort(messageboard, (Task a, Task b) -> {
            if(b.getProbability().getLevel() > a.getProbability().getLevel()){
                return -1;
            }else if(a.getProbability().getLevel().equals(b.getProbability().getLevel())){
                return a.getReward().compareTo(b.getReward());
            }
            return 1;
        });
    }

    public Long getNumberOfEncryptedTasks() {
        return numberOfEncryptedTasks;
    }

    private void countHighRiskTask(){
        numberOfEncryptedTasks = messageboard.stream().filter(task -> task.getProbability().getLevel() > 4).count();
    }

    public Long getNumberOfHighRiskTask() {
        return numberOfHighRiskTask;
    }
}
