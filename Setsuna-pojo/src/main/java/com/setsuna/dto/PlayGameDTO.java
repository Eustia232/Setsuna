package com.setsuna.dto;

import com.setsuna.entity.Couple;
import com.setsuna.enums.Game;
import lombok.Data;

@Data
public class PlayGameDTO {

    Couple couple;

    Game game;

}
