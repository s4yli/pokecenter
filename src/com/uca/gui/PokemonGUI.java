package com.uca.gui;

import com.uca.core.PokemonCore;
import com.uca.core.UserCore;
import com.uca.entity.UserEntity;
import com.uca.exceptions.ServiceException;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonGUI
{
    public static String getPokemonsByUserId(UserEntity connectedUser, int idRecherche) throws IOException, TemplateException
    {
        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);
        input.put("id_recherche", idRecherche);

        try
        {
            input.put("pokemons", PokemonCore.getPokemonsByUserId(idRecherche));
        }
        catch (ServiceException e)
        {
            input.put("error", e.getMessage());
        }

        return _Factory.genericGUI(input, "pokemons/pokemons.ftl");
    }

    public static String getPokemonsByIdAndLevelUp(UserEntity connectedUser, int idTarget, int idPokemon, int idPlayer) throws IOException, TemplateException
    {
        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);
        input.put("id_recherche", idTarget);

        try
        {
            input.put("pokemons", PokemonCore.getPokemonsByIdAndLevelUp(idTarget, idPokemon, idPlayer));
        }
        catch (ServiceException e)
        {
            input.put("error", e.getMessage());
        }
        return _Factory.genericGUI(input, "pokemons/pokemons.ftl");
    }

}
