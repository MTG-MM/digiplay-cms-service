package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.reward.RewardSchedule;
import com.wiinvent.gami.domain.entities.reward.RewardSegmentDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.pojo.ProductExcelData;
import com.wiinvent.gami.domain.pojo.VoucherExcelData;
import com.wiinvent.gami.domain.response.ProductDetailResponse;
import com.wiinvent.gami.domain.response.VoucherDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.reward.RewardItemStoreService;
import com.wiinvent.gami.domain.utils.DateUtils;
import com.wiinvent.gami.domain.utils.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
      productDetail.setStatus(RewardItemStatus.EXPIRE);
    }else{
      productDetail.setGivenAt(now);
      productDetail.setUserId(user.getId());
      productDetail.setStatus(RewardItemStatus.RECEIVE);
    }
    productDetailStorage.save(productDetail);
    return productDetail;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public List<ProductDetail> getProductDetail(int productStoreId, int limit, RewardSchedule rewardSchedule, RewardSegmentDetail rewardSegmentDetail, boolean newPeriod) {
    List<ProductDetail> productDetails = null;
    try {
      if(newPeriod && Boolean.FALSE.equals(rewardSchedule.getIsAccumulative())){
        productDetailStorage.updateItemStatus(rewardSegmentDetail.getRewardSegmentId(), rewardSegmentDetail.getRewardItemId(), rewardSchedule.getQuantity());
      }
      productDetails = productDetailStorage.getListProductDetailByStatus(productStoreId, RewardItemStatus.NEW, limit);
      productDetails.forEach(v -> {
        v.setStatus(RewardItemStatus.IN_POOL);
        v.setRewardItemId(rewardSegmentDetail.getRewardItemId());
        v.setRewardSegmentId(rewardSegmentDetail.getRewardSegmentId());
        v.setGivenToPool(DateUtils.getNowMillisAtUtc());
      });
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
    for(int i = 0; i < 100000; i++){
      ProductDetail productDetail = new ProductDetail();
      productDetail.setName(rewardItemStore.getName());
      productDetail.setCode(UUID.randomUUID().toString());
      productDetail.setStartAt(DateUtils.getNowMillisAtUtc());
      productDetail.setExpireAt(DateUtils.getNowMillisAtUtc() + 1000L * 60 * 60 * 24 * 100 );
      productDetail.setStoreId(rewardItemStore.getId());
      productDetail.setStatus(RewardItemStatus.NEW);
      productDetails.add(productDetail);
    }
    productDetailStorage.saveAll(productDetails);
    rewardItemStoreStorage.save(rewardItemStore);
  }

  public PageResponse<ProductDetailResponse> getAllProductDetails(Long storeId, String name, String code, Pageable pageable) {
    Page<ProductDetail> productDetails = productDetailStorage.findAlProductDetails(storeId, name, code, pageable);
    return PageResponse.createFrom(modelMapper.toPageProductDetailResponse(productDetails));
  }

  public Boolean importProduct(MultipartFile file, Long storeId) throws IOException {
   RewardItemStore rewardItemStore = rewardItemStoreStorage.findById(storeId);
   if(rewardItemStore == null){
     throw new ResourceNotFoundException("Cannot found Product with id: " + storeId);
   }
   List<ProductExcelData> productExcelData = ExcelUtils.readExcel(file, ProductExcelData.getHeader(), ProductExcelData.class);
   List<ProductDetail> productDetails = new ArrayList<>();
   for (ProductExcelData excelData : productExcelData) {
     ProductDetail productDetail = toProductDetail(excelData, storeId);
     productDetails.add(productDetail);
   }
   productDetailStorage.saveAll(productDetails);
   return true;
  }

  public ProductDetail toProductDetail(ProductExcelData productExcelData, Long storeId) {
    ProductDetail productDetail = new ProductDetail();
    productDetail.setCode(productExcelData.getCode());
    productDetail.setName(productExcelData.getName());
    productDetail.setStoreId(storeId);
    productDetail.setStartAt(DateUtils.convertStringToLongUTC(productExcelData.getStartAt()));
    productDetail.setExpireAt(DateUtils.convertStringToLongUTC(productExcelData.getExpireAt()));
    return productDetail;
  }
}
