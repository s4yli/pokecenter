package com.uca.gui;
import com.uca.core.ExchangeCore;
import com.uca.core.PokemonCore;
import com.uca.core.WantedExchangeCore;
import com.uca.entity.UserEntity;
import com.uca.exceptions.ServiceException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExchangeGUI
{
    public static String getAllExchanges(UserEntity connectedUser) throws IOException, TemplateException
    {

        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);

        try
        {
            input.put("pokemonsOnExchange", PokemonCore.getPokemonsOnExchange());
            input.put("exchanges", ExchangeCore.getAll());
        }
        catch (ServiceException e)
        {
            input.put("error", e.getMessage());
        }

        return _Factory.genericGUI(input, "exchanges/exchanges.ftl");
    }


    public static String getAllWantedExchangesById(UserEntity connectedUser, int id) throws IOException, TemplateException
    {

        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);
        input.put("exchangeID", id);

        try
        {
            input.put("wantedExchanges", WantedExchangeCore.getAllWantedExchangesById(id));
            input.put("userPokemons", PokemonCore.getPokemonsByUserId(connectedUser.getId()));
        }
        catch (ServiceException e)
        {
            input.put("error", e.getMessage());
        }

        return _Factory.genericGUI(input, "exchanges/wantedExchanges.ftl");
    }

    public static String getCreateExchange(UserEntity connectedUser) throws IOException, TemplateException
    {
        Map<String, Object> input = new HashMap<>();
        input.put("connectedUser", connectedUser);

        try
        {
            input.put("userPokemons", PokemonCore.getPokemonsByUserId(connectedUser.getId()));
            input.put("userExchanges", ExchangeCore.getAllExchangeById(connectedUser.getId()));
        }
        catch (ServiceException e)
        {
            input.put("error", e.getMessage());
        }

        return _Factory.genericGUI(input, "exchanges/create-exchange.ftl");

    }
}
