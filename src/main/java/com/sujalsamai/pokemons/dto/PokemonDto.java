package com.sujalsamai.pokemons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonDto {
    private int id;
    private String name;
    private String type;
    private List<String> attacks;
}
