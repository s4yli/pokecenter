package com.uca.dao;

import com.uca.entity.ExchangeEntity;
import com.uca.exceptions.ServiceException;
import java.sql.*;
import java.util.ArrayList;

/*
 * Récupération de données et implémentation de données
 */
public class ExchangeDAO extends _Generic<ExchangeEntity>
{
    /*
     * Suppression d'un échange à partir d'un identifiant
     */
    public void deleteById(int id) throws ServiceException
    {
        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM wanted_exchange WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = this.connect.prepareStatement("DELETE FROM exchanges WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not delete wanted_exchange or exchanges.");
        }
    }

    /*
     * Récupération de l'identifiant du joueur à partir de l'identifiant de l'échange qu'il a crée
     */
    public int getIdOfExchangeCreator(int exchangeID) throws ServiceException
    {
        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT users.id FROM users JOIN pokemons ON users.id = pokemons.user_id JOIN exchanges ON pokemons.id = exchanges.pokemon_id WHERE exchanges.id = ?;");
            preparedStatement.setInt(1, exchangeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int result = resultSet.getInt("id");
            return result;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not find the exchange creator.");
        }
    }

    /*
     * Récupération de tous les échanges appartenant à l'identifiant d'un utilisateur
     */

    public ArrayList<ExchangeEntity> getAllExchangeById(int id) throws ServiceException
    {
        try
        {
            ArrayList<ExchangeEntity> entities  = new ArrayList<>();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT e.id, e.pokemon_id, e.exchange_date, e.exchange_state FROM exchanges AS e INNER JOIN pokemons AS p ON e.pokemon_id = p.id INNER JOIN users AS u ON p.user_id = u.id WHERE u.id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }

            return entities;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not get all exchanges by id : " + id);
        }
    }

    /*
     * MAJ du status de l'échange
     */

    public void updateState(int id, int newState) throws  ServiceException
    {
        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE exchanges SET exchange_state=? WHERE id=?;");
            preparedStatement.setInt(1, newState);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not update state of exchange - id : " + id);
        }
    }

    /*
     * Retourne vrai si le pokémon est dans la liste des échanges
     */
    public boolean isPokemonOnExchange(int idPokemon) throws ServiceException
    {
        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM pokemons INNER JOIN exchanges ON pokemons.id = exchanges.pokemon_id WHERE pokemons.id = ?");
            preparedStatement.setInt(1, idPokemon);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return true;
            }
            return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("ID of pokemon doesnt exists.");
        }
    }

    /*
     * Récupération de tous les échanges dans le système
     */
    @Override
    public ArrayList<ExchangeEntity> getAll() throws ServiceException
    {
        try
        {
            ArrayList<ExchangeEntity> entities  = new ArrayList<>();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM exchanges ORDER BY exchange_date DESC;");
            ResultSet resultSet                 = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }

            return entities;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not get all exchanges.");
        }
    }

    /*
     * Insertion d'un échange dans la base de données
     */

    @Override
    public ExchangeEntity create(ExchangeEntity obj) throws ServiceException
    {
        Connection connection = _Connector.getInstance();

        try
        {
            PreparedStatement statement;

            statement = connection.prepareStatement("INSERT INTO exchanges (pokemon_id, exchange_date, exchange_state) VALUES (?, ?, ?);");
            statement.setInt(1,obj.getPokemon());
            statement.setTimestamp(2,obj.getExchange_date());
            statement.setInt(3,obj.getExchange_state());
            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new ServiceException("Could not insert new exchange, maybe because this pokemon is already on the exchange or this pokemon does not exist or this pokemon is not yours.");
        }

        return obj;
    }

    /*
     * Récupération d'un échange à partir de son identifiant
     */
    @Override
    public ExchangeEntity getById(int id) throws ServiceException
    {
        try
        {
            ExchangeEntity exchange;
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM exchanges WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            exchange = parseFromResultSet(resultSet);
            return exchange;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not find pokemon : " + id);
        }
    }

    @Override
    public void update(int id, ExchangeEntity obj) throws ServiceException
    {
        throw new ServiceException("Not implemented !");
    }

    @Override
    public void clearTable() throws ServiceException {
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM exchanges;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Could not clear table exchanges.");
        }
    }

    // Méthodes privées

    private ExchangeEntity parseFromResultSet(ResultSet resultSet) throws SQLException
    {
        ExchangeEntity entity = new ExchangeEntity();
        entity.setId(resultSet.getInt("id"));
        entity.setPokemon(resultSet.getInt("pokemon_id"));
        entity.setExchange_date(resultSet.getTimestamp("exchange_date"));
        entity.setExchange_state(resultSet.getInt("exchange_state"));
        return entity;
    }
}


