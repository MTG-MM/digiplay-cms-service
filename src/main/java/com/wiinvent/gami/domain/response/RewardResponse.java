package com.wiinvent.gami.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

  @Schema(description = "ID của món quà")
  private Long itemId;

  @Schema(description = "Tên món quà")
  private String itemName;

  @Schema(description = "Vị trí")
  private Long position;

  @Schema(description = "Giá trị")
  private Long value;

  @Schema(description = "URL hình ảnh")
  private String imageUrl;

  @Schema(description = "Mã")
  private String code;

  @Schema(description = "Loại món quà")
  private RewardItemType rewardItemType;

  @JsonIgnore
  private RewardItem rewardItem;

  @JsonIgnore
  private RewardSegmentDetail rewardSegmentDetail;

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardItemType = rewardItem.getRewardType();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }

  public RewardResponse(RewardItem rewardItem, RewardSegmentDetail segmentDetail, VoucherDetail voucherDetail){
    itemId = rewardItem.getId();
    itemName = rewardItem.getRewardName();
    value = rewardItem.getValue();
    imageUrl = rewardItem.getImageUrl();
    rewardItemType = rewardItem.getRewardType();
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
    rewardItemType = rewardItem.getRewardType();
    code = productDetail.getCode();
    position = segmentDetail.getPosition();
    this.rewardItem = rewardItem;
    this.rewardSegmentDetail = segmentDetail;
  }
}
