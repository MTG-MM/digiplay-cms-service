package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.AchievementType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.pojo.AchievementInfo;
import com.wiinvent.gami.domain.utils.Converter.AchievementInfoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "achievement")
public class Achievement extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Size(max = 500)
  @Column(name = "description", length = 500)
  private String description;

  @ColumnDefault("INACTIVE")
  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;

  @Column(name = "achievement_info")
  @Convert(converter = AchievementInfoConverter.class)
  private List<AchievementInfo> achievementInfo;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private AchievementType type;
}
