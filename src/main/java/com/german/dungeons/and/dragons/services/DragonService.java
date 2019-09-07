package com.german.dungeons.and.dragons.services;

import com.german.dungeons.and.dragons.enums.ItemType;
import com.german.dungeons.and.dragons.exceptions.PurchaseException;
import com.german.dungeons.and.dragons.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DragonService {
    private DragonData dragonData;
    @Value("${dungeons.rest.url}")
    private String mainUrl;

    private static final String GAME_START_ENDPOINT = "game/start";

    private final RestTemplate restTemplate;
    private final ShopService shopService;
    private final GuildService guildService;
    private final StartService startService;


    public void startGame(String dragonName) {
        GameStartResponse gameStart = startService.statGame();
        if(gameStart != null){
            dragonData = DragonData.builder().gameId(gameStart.getGameId()).level(gameStart.getLevel()).lives(gameStart.getLives())
                    .gold(gameStart.getGold()).name(dragonName).score(gameStart.getScore()).failedTask(0).build();
            trainDragon();
        }
    }

    public void trainDragon() {
        while(dragonData.getLives() > 0){
            guildService.openMessageboard(dragonData.getGameId());
            chooseItemToBuyAndBuy();
            Result result = guildService.solveTask(dragonData.getGameId());
            if (result != null) {
                dragonData.setGold(result.getGold());
                dragonData.setLives(result.getLives());
                dragonData.setScore(result.getScore());
                log.info("Score: " + dragonData.getScore() + " lives: " + dragonData.getLives() + " level: " + dragonData.getLevel()
                        + " gold: " + dragonData.getGold());
                if(result.getLives() == 0){
                    log.error("High score: " + result.getScore());
                }
                if(!result.getSuccess()) {
                    dragonData.setFailedTask(dragonData.getFailedTask() + 1);
                }
            }
        }
    }

    private void chooseItemToBuyAndBuy() {
        List<Item> itemsInShop = shopService.viewAllItemsInShop(dragonData.getGameId());
        if(dragonData.getLives() <= 2){
            buyItemAndReturnSuccessOrNot(ItemType.HEALING_POTION);
        }
        if(guildService.getNumberOfEncryptedTasks() > 4 ){
            if(dragonData.getLevel() >= 15){
                buyItemAndReturnSuccessOrNot(ItemType.BOOK_OF_MEGATRICKS);
            }else{
                buyItemAndReturnSuccessOrNot(ItemType.BOOK_OF_TRICKS);
            }
        }
        if(dragonData.getFailedTask() >= 2) {
            if(dragonData.getLevel() >= 15){
                if(buyItemAndReturnSuccessOrNot(ItemType.CLAW_HONING)){
                    dragonData.setFailedTask(0);
                }
            }else{
                if(buyItemAndReturnSuccessOrNot(ItemType.CLAW_SHARPENING)){
                    dragonData.setFailedTask(0);
                }
            }
        }
        if(guildService.getNumberOfHighRiskTask() > 4) {
            if(dragonData.getLevel() >= 15){
                buyItemAndReturnSuccessOrNot(ItemType.POTION_OF_AWESOME_WINGS);
            }else{
                buyItemAndReturnSuccessOrNot(ItemType.POTION_OF_STRONGER_WINGS);
            }
        }
    }

    private boolean buyItemAndReturnSuccessOrNot(ItemType itemType) {
        try{
            ResultOfBuying resultOfBuying = shopService.buyItem(itemType,dragonData.getGameId(), dragonData.getGold());
            dragonData.setGold(resultOfBuying.getGold());
            dragonData.setLevel(resultOfBuying.getLevel());
            return true;
        }catch (PurchaseException ex){
            log.info(ex.getMessage());
            return false;
        }
    }
}
