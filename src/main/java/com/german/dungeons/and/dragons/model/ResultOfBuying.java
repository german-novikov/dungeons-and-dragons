package com.german.dungeons.and.dragons.model;

import lombok.Data;

@Data
public class ResultOfBuying {
  private boolean shoppingSuccess;
  private Integer gold;
  private Integer lives;
  private Integer level;
  private Integer turn;

}
