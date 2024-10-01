package com.uca.entity;



public class PokemonEntity 
{
    
    private String          pokemon_name,
                            sprite;
    private int             id,
                            pokemon_level,
                            id_pokedex,
                            user_id;


    public PokemonEntity()
    {
        //Ignored !
    }

    public PokemonEntity(String name, int pokedexId, int user, String sprite)
    {
        this.pokemon_name = name;
        this.id_pokedex = pokedexId;
        this.user_id = user;
        this.sprite = sprite;
        this.pokemon_level = 1;
    }


    public String getPokemon_name()
    {
        return this.pokemon_name;
    }

    public void setPokemon_name(String pokemon_name)
    {
        this.pokemon_name = pokemon_name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId_pokedex()
    {
        return id_pokedex;
    }

    public void setId_pokedex(int id_pokedex)
    {
        this.id_pokedex = id_pokedex;
    }

    public int getPokemon_level()
    {
        return pokemon_level;
    }

    public void setPokemon_level(int pokemon_level)
    {
        this.pokemon_level = pokemon_level;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getSprite()
    {
        return sprite;
    }

    public void setSprite(String sprite)
    {
        this.sprite = sprite;
    }
}
