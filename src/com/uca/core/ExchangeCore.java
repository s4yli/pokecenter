package com.uca.core;

import com.uca.entity.ExchangeEntity;
import com.uca.dao.ExchangeDAO;
import com.uca.exceptions.ServiceException;
import java.util.ArrayList;

public class ExchangeCore
{
    public static ExchangeEntity createNewExchange(ExchangeEntity newExchange) throws ServiceException
    {
        return new ExchangeDAO().create(newExchange);
    }

    public static ArrayList<ExchangeEntity> getAll() throws ServiceException
    {
        return new ExchangeDAO().getAll();
    }

    public static void updateState(int id, int newState) throws ServiceException
    {
        new ExchangeDAO().updateState(id, newState);
    }

    public static ArrayList<ExchangeEntity> getAllExchangeById(int id) throws ServiceException
    {
        return new ExchangeDAO().getAllExchangeById(id);
    }

    public static int getIdOfExchangeCreator(int exchangeID) throws ServiceException
    {
        return new ExchangeDAO().getIdOfExchangeCreator(exchangeID);
    }

    public static ExchangeEntity getExchangeById(int id) throws ServiceException
    {
        return new ExchangeDAO().getById(id);
    }

    public static void deleteById(int id) throws ServiceException
    {
        new ExchangeDAO().deleteById(id);
    }

    public static boolean isPokemonOnExchange(int idPokemon) throws ServiceException
    {
        return new ExchangeDAO().isPokemonOnExchange(idPokemon);
    }
}
