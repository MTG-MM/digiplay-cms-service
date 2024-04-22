package com.wiinvent.gami.domain.entities.type;

import java.util.List;

public enum CharacterCategoryType {
  SKIN,
  PET,
  OUTSIDE,
  DECOR;

  public static List<CharacterType> getSkinCharacterTypes() {
    return List.of(
        CharacterType.SHIRT,
        CharacterType.TROUSER,
        CharacterType.HAT,
        CharacterType.SHOE,
        CharacterType.HAIR,
        CharacterType.SKIN_COLOR,
        CharacterType.EMOTION
    );
  }

  public static List<CharacterType> getDecorCharacterTypes() {
    return List.of(
        CharacterType.DECORATION,
        CharacterType.CAR
    );
  }

  public static List<CharacterType> getOutsideCharacterTypes() {
    return List.of(
        CharacterType.BACKGROUND,
        CharacterType.EFFECT
    );
  }

  public static List<CharacterType> getPetCharacterTypes() {
    return List.of(
        CharacterType.PET
    );
  }
}
