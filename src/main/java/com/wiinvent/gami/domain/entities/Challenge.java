package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "challenge")
public class Challenge extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "game_id", nullable = false)
  private Integer gameId;

  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @Size(max = 500)
  @Column(name = "image_url", length = 500)
  private String imageUrl;

  @Size(max = 500)
  @Column(name = "thumb_url", length = 500)
  private String thumbUrl;

  @Lob
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private Status status;
}
