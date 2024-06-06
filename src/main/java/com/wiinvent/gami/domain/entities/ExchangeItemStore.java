package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.ExchangeStoreType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Data
@Table(name = "exchange_item_store")
public class ExchangeItemStore extends BaseEntity {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "store_type", length = 30)
  @Enumerated(EnumType.STRING)
  private ExchangeStoreType storeType;

  @NotNull
  @Column(name = "reward_items")
  @JdbcTypeCode(SqlTypes.JSON)
//  @Convert(converter = UserRewardItemConverter.class)
  private List<UserRewardItems> rewardItems;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @ColumnDefault("0")
  @Column(name = "coin_price")
  private Integer coinPrice;

  @ColumnDefault("0")
  @Column(name = "point_price")
  private Integer pointPrice;

  @Column(name = "ticket_price")
  private Integer ticketPrice;

  @Column(name = "start_at")
  private Long startAt;

  @Column(name = "end_at")
  private Long endAt;

  @Column(name = "quantity")
  private Long quantity = 0L;

  @Column(name = "limit_exchange")
  private Long limitExchange;

  public void addQuantity(long amount) {
    this.quantity = this.getQuantity() + amount;
  }

  public void minusQuantity(long amount) {
    this.quantity = Math.max(this.getQuantity() - amount, 0);
  }
}
