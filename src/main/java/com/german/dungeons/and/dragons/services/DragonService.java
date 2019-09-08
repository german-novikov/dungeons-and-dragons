package com.german.dungeons.and.dragons.services;

import com.german.dungeons.and.dragons.enums.ItemType;
import com.german.dungeons.and.dragons.exceptions.PurchaseException;
import com.german.dungeons.and.dragons.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class DragonService {
    private DragonData dragonData;
    @Value("${dungeons.rest.url}")
    private String mainUrl;

    private final ShopService shopService;
    private final GuildService guildService;
    private final StartService startService;


    public void startGame(String dragonName) {
        GameStartResponse gameStart = startService.statGame();
        if(!StringUtils.isEmpty(gameStart.getGameId())){
            dragonData = DragonData.builder().gameId(gameStart.getGameId()).level(gameStart.getLevel()).lives(gameStart.getLives())
                    .gold(gameStart.getGold()).name(dragonName).score(gameStart.getScore()).failedTask(0).build();
            trainDragon();
        }
    }

    public void trainDragon() {
        while(dragonData.getLives() > 0){
            updateMessageBoard();
            chooseItemToBuyAndBuy();
            ResultOfSolvingTask result = guildService.solveTask(dragonData.getGameId());
            if (result.getSuccess() != null) {
                dragonData.setGold(result.getGold());
                dragonData.setLives(result.getLives());
                dragonData.setScore(result.getScore());
                log.info(dragonData.toString());
                if(result.getLives() == 0){
                    log.info(String.format("High score of dragon %s is: %s",dragonData.getName(), result.getScore()));
                }
                if(!result.getSuccess()) {
                    dragonData.setFailedTask(dragonData.getFailedTask() + 1);
                }
            }
        }
    }

    private void chooseItemToBuyAndBuy() {
        shopService.viewAllItemsInShop(dragonData.getGameId());
        if(dragonData.getLives() <= 2){
            buyItem(ItemType.HEALING_POTION);
        }
        if(guildService.getNumberOfEncryptedTasks() > 4 ){
            if(dragonData.getLevel() >= 15){
                buyExpensiveItem(ItemType.BOOK_OF_MEGATRICKS, ItemType.BOOK_OF_TRICKS);
            }else{
                buyItem(ItemType.BOOK_OF_TRICKS);
            }
        }
        if(dragonData.getFailedTask() >= 2) {
            if(dragonData.getLevel() >= 15){
                if(buyExpensiveItem(ItemType.CLAW_HONING, ItemType.CLAW_SHARPENING).isShoppingSuccess()){
                    dragonData.setFailedTask(0);
                }
            }else{
                if(buyItem(ItemType.CLAW_SHARPENING).isShoppingSuccess()){
                    dragonData.setFailedTask(0);
                }
            }
        }
        if(guildService.getNumberOfHighRiskTask() > 4) {
            if(dragonData.getLevel() >= 15){
                buyExpensiveItem(ItemType.POTION_OF_AWESOME_WINGS, ItemType.POTION_OF_STRONGER_WINGS);
            }else{
                buyItem(ItemType.POTION_OF_STRONGER_WINGS);
            }
        }
    }

    private ResultOfBuying buyItem(ItemType itemType) {
        ResultOfBuying resultOfBuying;
        try{
            resultOfBuying = shopService.buyItem(itemType,dragonData.getGameId(), dragonData.getGold());
            dragonData.setGold(resultOfBuying.getGold());
            dragonData.setLevel(resultOfBuying.getLevel());
            updateMessageBoard();
        }catch (PurchaseException ex){
            log.info(ex.getMessage());
            resultOfBuying = new ResultOfBuying();
            resultOfBuying.setShoppingSuccess(false);
        }
        return resultOfBuying;
    }

    private ResultOfBuying buyExpensiveItem(ItemType expensiveItem, ItemType cheapSubstituteItem) {
        ResultOfBuying resultOfBuying = buyItem(expensiveItem);
        if (!resultOfBuying.isShoppingSuccess()) {
            return buyItem(cheapSubstituteItem);
        }
        return resultOfBuying;
    }

    private void updateMessageBoard(){
        guildService.openMessageboard(dragonData.getGameId());
    }
}
