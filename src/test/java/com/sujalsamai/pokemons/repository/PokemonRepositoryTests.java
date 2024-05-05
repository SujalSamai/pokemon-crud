package com.sujalsamai.pokemons.repository;

import com.sujalsamai.pokemons.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // This disables the default behavior of replacing DataSource with an embedded database
public class PokemonRepositoryTests {
    @Autowired
    private PokemonRepository pokemonRepository;

    private Pokemon pokemon;
    private List<String> attacks;

    @BeforeEach
    public void setupData()
    {
        attacks = new ArrayList<>();
        attacks.add("Tackle");
        attacks.add("Thunderbolt");

        pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric")
                .attacks(attacks.toString())
                .build();
    }

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon()
    {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .attacks(attacks.toString())
                .build();
        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        //Assert

        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_GetAll_ReturnMultiplePokemons()
    {

        Pokemon pokemon2 = Pokemon.builder()
                .name("Charmander")
                .type("Fire")
                .attacks(List.of("Ember", "Flamethrower").toString())
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon() {

        pokemonRepository.save(pokemon);

        Pokemon pokemonList = pokemonRepository.findById(pokemon.getId()).get();

        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull() {

        pokemonRepository.save(pokemon);

        Pokemon pokemonList = pokemonRepository.findByType(pokemon.getType()).get();

        Assertions.assertThat(pokemonList).isNotNull();
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnUpdatedPokemon() {

        Pokemon earlyPokemon = pokemonRepository.save(pokemon);

        Pokemon savePokemon = pokemonRepository.findById(pokemon.getId()).get();
        savePokemon.setType("Electric");
        savePokemon.setName("Raichu");
        savePokemon.setAttacks(List.of("Thunderbolt", "Thundershock").toString());

        Pokemon updatedPokemon = pokemonRepository.save(savePokemon);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
        Assertions.assertThat(updatedPokemon.getAttacks()).isNotNull();
        Assertions.assertThat(updatedPokemon.getName()).isNotEqualTo("pikachu");
    }

    @Test
    public void PokemonRepository_DeletePokemon_ReturnPokemonIsEmpty(){
        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> returnedPokemon = pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(returnedPokemon).isEmpty();
    }
}
