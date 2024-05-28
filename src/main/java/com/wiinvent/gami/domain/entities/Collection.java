package com.wiinvent.gami.domain.entities;

import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "collection")
@Data
public class Collection extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 255)
  @Column(name = "name")
  private String name;

  @Lob
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private CollectionType type;

  @Size(max = 255)
  @Column(name = "description")
  private String description;

  @Size(max = 255)
  @Column(name = "image_url")
  private String imageUrl;

  @Lob
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "external_id")
  private Long externalId;

  @Column(name = "piece_number")
  private Long pieceNumber;

  @Column(name = "lucky_point")
  private Long luckyPoint;

  @Column(name = "collection_piece")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Long> collectionPieces;

}
