package com.uca.core;

import com.uca.dao.WantedExchangeDAO;
import com.uca.entity.WantedExchangeEntity;
import com.uca.exceptions.ServiceException;

import java.util.ArrayList;

public class WantedExchangeCore
{
    public static ArrayList<WantedExchangeEntity> getAllWantedExchangesById(int id) throws ServiceException
    {
        return new WantedExchangeDAO().getAllWantedExchangesById(id);
    }

    public static WantedExchangeEntity addNewWantedExchange(WantedExchangeEntity newExchange) throws ServiceException
    {
        return new WantedExchangeDAO().create(newExchange);
    }

    public static boolean isPokemonInOneOfWantedExchange(int pokedex_id, int exchange_id) throws ServiceException
    {
        return new WantedExchangeDAO().isPokemonInOneOfWantedExchange(pokedex_id, exchange_id);
    }
}
