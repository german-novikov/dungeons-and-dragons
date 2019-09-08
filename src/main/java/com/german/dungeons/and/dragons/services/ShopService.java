package com.german.dungeons.and.dragons.services;

import com.german.dungeons.and.dragons.enums.ItemType;
import com.german.dungeons.and.dragons.exceptions.PurchaseException;
import com.german.dungeons.and.dragons.model.Item;
import com.german.dungeons.and.dragons.model.ResultOfBuying;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {
    private static final String SHOP_ENDPOINT = "/shop";
    private static final String ITEM_BUY_ENDPOINT = "/buy/";

    private final RestTemplate restTemplate;
    private List<Item> items = new ArrayList<>();
    @Value("${dungeons.rest.url}")
    private String mainUrl;

    public void viewAllItemsInShop(String gameId) {
        String url = mainUrl + gameId + SHOP_ENDPOINT;
        try{
            items = Arrays.asList(restTemplate.getForObject(url, Item[].class));
        }catch (Exception ex) {
            log.error(String.format("Messageboard get failed with the exception: ", ex));
        }
    }

    public ResultOfBuying buyItem(ItemType itemType, String gameId, Integer gold) throws PurchaseException {
        if (isEnoughGold(itemType.getValue(), gold)) {
        String url = mainUrl + gameId + SHOP_ENDPOINT +ITEM_BUY_ENDPOINT + itemType.getValue();
            try{
                return restTemplate.postForObject(url,null, ResultOfBuying.class);
            }catch (Exception ex){
                throw new PurchaseException(String.format("Item %s purchase from url %s failed with the exception", itemType.getValue(), url) );
            }
        }
        throw new PurchaseException(String.format("Not enough money to buy the item %s", itemType.getValue()));
    }

    private boolean isEnoughGold(String itemId, Integer gold) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst().get().getCost() < gold;
    }
}
