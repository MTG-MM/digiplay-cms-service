package com.wiinvent.gami.domain.entities.gvc;

import com.wiinvent.gami.domain.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "gvc_history")
public class GvcHistory extends BaseEntity {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Size(max = 50)
  @Column(name = "partner_transaction_id", length = 50)
  private String partnerTransactionId;

  @Size(max = 16)
  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "point")
  private Integer point;

  @Column(name = "point_balance")
  private Long pointBalance;

  @Column(name = "coin")
  private Integer coin;

  @Column(name = "coin_balance")
  private Long coinBalance;

  @Size(max = 50)
  @Column(name = "gvc_package_code", length = 50)
  private String gvcPackageCode;

  @Size(max = 500)
  @Column(name = "note", length = 500)
  private String note;

}