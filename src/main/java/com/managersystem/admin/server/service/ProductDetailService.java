package com.managersystem.admin.server.service;

import com.managersystem.admin.server.entities.RewardItemStore;
import com.managersystem.admin.server.entities.RewardSchedule;
import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.ProductDetail;
import com.managersystem.admin.server.entities.type.PollItemStatus;
import com.managersystem.admin.server.entities.type.Status;
import com.managersystem.admin.server.entities.type.StoreType;
import com.managersystem.admin.server.service.base.BaseService;
import com.managersystem.admin.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ProductDetailService extends BaseService {
  public ProductDetail setProductDetailForUser(User user, UUID detailId) {
    Long now = DateUtils.getNowMillisAtUtc();
    ProductDetail productDetail = productDetailStorage.findById(detailId);
    if(productDetail == null){
      log.warn("===========> setProductDetailForUser: product detail not found {}", detailId);
      return null;
    }
    RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(productDetail.getStoreId());
    if(rewardItemStore == null){
      log.warn("===========> setProductDetailForUser: product store not found {}", productDetail.getStoreId());
      return null;
    }
    if(!rewardItemStore.getStatus().equals(Status.ACTIVE)){
      return null;
    }
    if(now < productDetail.getStartAt()){
      return null;
    }
    if(now > productDetail.getExpireAt()){
      productDetail.setStatus(PollItemStatus.EXPIRE);
    }else{
      productDetail.setGivenAt(now);
      productDetail.setUserId(user.getId());
      productDetail.setStatus(PollItemStatus.RECEIVE);
    }
    productDetailStorage.save(productDetail);
    return productDetail;
  }

  public List<ProductDetail> getProductDetail(int productStoreId, int limit, RewardSchedule rewardSchedule, boolean newPeriod) {
    List<ProductDetail> productDetails = null;
    try {
      if(newPeriod && !rewardSchedule.getIsAccumulative()){
        productDetailStorage.updateItemStatus(rewardSchedule.getRewardSegmentDetailId());
      }
      productDetails = productDetailStorage.getListProductDetailByStatus(productStoreId, PollItemStatus.NEW, limit);
      productDetails.forEach(v -> v.setStatus(PollItemStatus.IN_POOL));
      productDetailStorage.saveAll(productDetails);
    } catch (Exception ex){
      log.error("======>getProductDetail {} {}" , productStoreId, ex);
    }
    return productDetails;
  }
  public void initRandomProductDetail(){
    RewardItemStore rewardItemStore = new RewardItemStore();
    rewardItemStore.setId(2L);
    rewardItemStore.setName("Product test");
    rewardItemStore.setStatus(Status.ACTIVE);
    rewardItemStore.setType(StoreType.PRODUCT);
    List<ProductDetail> productDetails = new ArrayList<>();
    for(int i = 0; i < 1000; i++){
      ProductDetail productDetail = new ProductDetail();
      productDetail.setName(rewardItemStore.getName());
      productDetail.setCode(UUID.randomUUID().toString());
      productDetail.setStartAt(DateUtils.getNowMillisAtUtc());
      productDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + 1000L * 60 * 60 * 24 * 100 );
      productDetail.setStoreId(rewardItemStore.getId());
      productDetail.setStatus(PollItemStatus.NEW);
      productDetails.add(productDetail);
    }
    productDetailStorage.saveAll(productDetails);
    rewardItemStoreStorage.save(rewardItemStore);
  }
}
