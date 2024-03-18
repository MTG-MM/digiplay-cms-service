package com.wiinvent.gami.app.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.RewardItem;
import com.wiinvent.gami.domain.entities.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.type.RewardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {
  private Long itemId;
  private String itemName;
  private Long position;
  private Long value;
  private String imageUrl;
  private String code;
  private RewardType rewardType;
  @JsonIgnore
  private RewardItem rewardItem;
  @JsonIgnore
  private RewardSegmentDetail rewardSegmentDetail;

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardType = rewardItem.getRewardType();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail, VoucherDetail voucherDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardType = rewardItem.getRewardType();
    code = voucherDetail.getCode();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail, ProductDetail productDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardType = rewardItem.getRewardType();
    code = productDetail.getCode();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }
}
