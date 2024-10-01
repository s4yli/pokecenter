package com.uca.core;

import com.uca.dao.PokemonDAO;
import com.uca.entity.PokemonEntity;
import com.uca.exceptions.ServiceException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PokemonCore
{
    public static ArrayList<PokemonEntity> getPokemonsByUserId(int id) throws ServiceException
    {
        return new PokemonDAO().getPokemonsByUserId(id);
    }

    public static ArrayList<PokemonEntity> getPokemonsByIdAndLevelUp(int idTarget, int idPokemon, int idPlayer) throws ServiceException
    {
        return new PokemonDAO().getPokemonsByIdAndLevelUp(idTarget, idPokemon, idPlayer);
    }

    public static PokemonEntity newPokemon(PokemonEntity newPokemon) throws ServiceException
    {
        return new PokemonDAO().create(newPokemon);
    }

    public static ArrayList<PokemonEntity> getPokemonsOnExchange() throws ServiceException
    {
        return new PokemonDAO().getPokemonsOnExchange();
    }

    public static PokemonEntity getPokemonById(int id) throws ServiceException
    {
        return new PokemonDAO().getById(id);
    }

    public static void changePokemon(int player1ID, int player2ID, int pokemon1ID, int pokemon2ID) throws ServiceException, SQLException {
        new PokemonDAO().changePokemon(player1ID, player2ID, pokemon1ID, pokemon2ID);
    }



}
