package com.managersystem.admin.server.stores;

import com.managersystem.admin.server.entities.VoucherStore;
import com.managersystem.admin.server.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherStoreStorage extends BaseStorage {

  public List<VoucherStore> findAll() {
    return voucherStoreRepository.findAll();
  }

  public void save(VoucherStore voucherStore) {
    voucherStoreRepository.save(voucherStore);
  }

  public Page<VoucherStore> findAll(Pageable pageable) {
    return voucherStoreRepository.findAll(pageable);
  }

  public VoucherStore findById(Long id) {
    return voucherStoreRepository.findById(id).orElse(null);
  }
}
