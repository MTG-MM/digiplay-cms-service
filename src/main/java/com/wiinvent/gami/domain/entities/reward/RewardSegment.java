package com.wiinvent.gami.domain.entities.reward;

import com.wiinvent.gami.domain.entities.BaseEntity;
import com.wiinvent.gami.domain.entities.type.PeriodType;
import com.wiinvent.gami.domain.entities.type.ResourceType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Table(name = "reward_segment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RewardSegment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "point_require")
  private Integer pointRequirement= 0;

  @Column(name = "coin_require")
  private Integer coinRequirement= 0;

  @Column(name = "turn_require")
  private Integer turnRequirement = 0;

  @Column(name = "ticket_require")
  private Integer ticketRequirement;

  @Column(name = "is_accumulative_priority", columnDefinition = "BIT")
  private Boolean isAccumulativePriority = true; //Có tích lũy vào quà default nếu quà không hợp lệ không

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Lob
  @Column(name = "resource_type")
  @Enumerated(EnumType.STRING)
  private ResourceType resourceType;

  @Column(name = "lucky_point")
  private Long luckyPoint= 0L;

  @Lob
  @Column(name = "period_type")
  @Enumerated(EnumType.STRING)
  private PeriodType periodType = PeriodType.ALL;

  @Column(name = "period_value")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Integer> periodValue = new ArrayList<>();


}
