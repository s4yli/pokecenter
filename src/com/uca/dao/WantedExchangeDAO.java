package com.uca.dao;


import com.uca.entity.WantedExchangeEntity;
import com.uca.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Récupération de données et implémentation de données
 */
public class WantedExchangeDAO extends _Generic<WantedExchangeEntity>
{

    /*
     * Récupération de tous les échange désirés à partir de l'ID de l'échange
     */
    public ArrayList<WantedExchangeEntity> getAllWantedExchangesById(int id) throws ServiceException
    {
        ArrayList<WantedExchangeEntity> entities = new ArrayList<>();

        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM wanted_exchange WHERE id IN (SELECT id FROM exchanges WHERE id = ?);");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not get all wanted exchanges by id.");
        }

        return entities;
    }

    /*
     * Retourne vrai si le pokedex entrée en paramètre est contennu dans l'un des échanges désirés d'un échange
     */
    public boolean isPokemonInOneOfWantedExchange(int pokedex_id, int exchangeID) throws ServiceException
    {
        try
        {
            System.out.println(pokedex_id);
            System.out.println(exchangeID);
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT id FROM wanted_exchange WHERE id_pokedex = ? AND id = ?;");
            preparedStatement.setInt(1, pokedex_id);
            preparedStatement.setInt(2, exchangeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not find the pokemon on one of wanted exchange.");
        }
    }

    /*
     * Insertion d'un échange désiré dans la base de données
     */

    @Override
    public WantedExchangeEntity create(WantedExchangeEntity obj) throws ServiceException
    {
        Connection connection = _Connector.getInstance();

        try
        {
            PreparedStatement statement;

            statement = connection.prepareStatement("INSERT INTO wanted_exchange (id, id_pokedex) VALUES (?, ?);");
            statement.setInt(1,obj.getId());
            statement.setInt(2,obj.getId_pokedex());
            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new RuntimeException("could not insert new wanted exchange.");
        }

        return obj;
    }

    /*
     * Récupération de tous les échange désirés dans le système
     */
    @Override
    public ArrayList<WantedExchangeEntity> getAll() throws ServiceException {
        ArrayList<WantedExchangeEntity> entities = new ArrayList<>();

        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM wanted_exchange;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not get all wanted exchanges.");
        }

        return entities;
    }

    @Override
    public WantedExchangeEntity getById(int id) throws ServiceException {
        throw new ServiceException("Not implemented !");
    }

    @Override
    public void update(int id, WantedExchangeEntity obj) throws ServiceException {
        throw new ServiceException("Not implemented !");
    }

    @Override
    public void clearTable() throws ServiceException {
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM wanted_exchange;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Could not clear table wanted_exchanges.");
        }
    }

    // --------- Private methods

    private WantedExchangeEntity parseFromResultSet(ResultSet resultSet) throws SQLException {
        WantedExchangeEntity entity = new  WantedExchangeEntity();
        entity.setId(resultSet.getInt("id"));
        entity.setId_pokedex(resultSet.getInt("id_pokedex"));
        return entity;
    }

}
