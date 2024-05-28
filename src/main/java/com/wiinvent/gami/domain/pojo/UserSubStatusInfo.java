package com.wiinvent.gami.domain.pojo;

import com.wiinvent.gami.domain.entities.PremiumState;
import com.wiinvent.gami.domain.entities.SubState;
import lombok.Data;

@Data
public class UserSubStatusInfo {
  Boolean isPremium = false;
  Boolean isSub = false;

  public UserSubStatusInfo(PremiumState premiumState, SubState subState) {
    if (premiumState != null) {
      isPremium = true;
    }
    if (subState != null) {
      isSub = true;
    }
  }
}
