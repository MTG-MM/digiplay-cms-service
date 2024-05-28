package com.wiinvent.gami.domain.pojo;

import com.wiinvent.gami.domain.entities.PremiumState;
import com.wiinvent.gami.domain.entities.SubState;
import com.wiinvent.gami.domain.entities.type.UserType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserSubStatusInfo {
  Boolean isPremium = false;
  Boolean isSub = false;
  List<UserType> userTypes;

  public UserSubStatusInfo(PremiumState premiumState, SubState subState) {
    userTypes = new ArrayList<>(List.of(UserType.FREE));
    if (premiumState != null) {
      isPremium = true;
      userTypes.add(UserType.PREMIUM);
    }
    if (subState != null) {
      isSub = true;
      userTypes.add(UserType.SUB);
    }
  }
}
