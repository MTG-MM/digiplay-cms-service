package com.wiinvent.gami.domain.entities.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_star")
@NoArgsConstructor
@AllArgsConstructor
public class GameStar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "game_id")
  private Integer gameId;

  @Column(name = "`like`")
  private Integer like = 0;

  @Column(name = "dislike")
  private Integer dislike = 0;

  @Column(name = "comment")
  private Integer comment = 0;

  public void addLike(int amount){
    like += amount;
  }
  public void addDislike(int amount){
    dislike += amount;
  }

  public void subtractLike(int amount){
    like = Math.max(like - amount, 0);
  }
  public void subtractDislike(int amount){
    dislike = Math.max(dislike - amount, 0);
  }

  public GameStar(Integer gameId) {
    this.gameId = gameId;
    this.like = 0;
    this.dislike = 0;
    this.comment = 0;
  }
}