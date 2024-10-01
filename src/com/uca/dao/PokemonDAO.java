package com.uca.dao;

import com.uca.entity.PokemonEntity;
import com.uca.exceptions.ServiceException;
import java.sql.*;
import java.util.ArrayList;

/*
 * Récupération de données et implémentation de données
 */

public class PokemonDAO extends _Generic<PokemonEntity>
{
    /*
     * Récupération des pokémons à partir de l'identifiant d'un joueur
     */
    public ArrayList<PokemonEntity> getPokemonsByUserId(int userId) throws ServiceException
    {
        try
        {
            ArrayList<PokemonEntity> entities   = new ArrayList<>();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM pokemons WHERE user_id = ?;");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }
            return entities;
        } 
        
        catch (SQLException e) 
        {
            throw new ServiceException("Could not get pokemons by the user_id.");
        }

    }

    /*
     * Récupération des pokémons qui sont dans les échanges
     */
    public ArrayList<PokemonEntity> getPokemonsOnExchange() throws ServiceException
    {
        try
        {
            ArrayList<PokemonEntity> entities   = new ArrayList<>();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM pokemons JOIN exchanges ON exchanges.pokemon_id = pokemons.id ORDER BY exchange_date DESC;");
            ResultSet resultSet                 = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }

            return entities;
        }
        catch (SQLException e)
        {
            throw new ServiceException("Could not get all pokemons on the exchange.");
        }

    }

    /*
     * Échange de pokémons entre deux joueurs
     */
    public void changePokemon(int player1ID, int player2ID, int pokemon1ID, int pokemon2ID) throws ServiceException, SQLException
    {
        try
        {
            this.connect.setAutoCommit(false);

            // Échange du pokémon du joueur 1 vers le joueur 2
            PreparedStatement preparedStatement1 = this.connect.prepareStatement("UPDATE pokemons SET user_id = ? WHERE id = ? AND user_id = ?;");
            preparedStatement1.setInt(1, player2ID);
            preparedStatement1.setInt(2, pokemon1ID);
            preparedStatement1.setInt(3, player1ID);
            preparedStatement1.executeUpdate();

            // Échange du pokémon du joueur 2 vers le joueur 1
            PreparedStatement preparedStatement2 = this.connect.prepareStatement("UPDATE pokemons SET user_id = ? WHERE id = ? AND user_id = ?;");
            preparedStatement2.setInt(1, player1ID);
            preparedStatement2.setInt(2, pokemon2ID);
            preparedStatement2.setInt(3, player2ID);
            preparedStatement2.executeUpdate();

            preparedStatement2.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new ServiceException("Could not change pokemons between player 1 : " + player1ID + "and player 2 : " + player2ID);
        }
        finally
        {
            this.connect.setAutoCommit(true);
        }
    }

    /*
     * Récupération des pokémons par identifiant du joueur et augmentation du pokémon ciblé, décrémentation du nb d'xp du joueur qui xp le pokémon
     */
    public ArrayList<PokemonEntity> getPokemonsByIdAndLevelUp(int idTarget, int idPokemon, int idPlayer) throws ServiceException
    {
        Connection connection = _Connector.getInstance();

        try
        {
            PreparedStatement statementPlayer;
            statementPlayer = connection.prepareStatement("UPDATE users SET nbxp = nbxp - 1 WHERE id = ?;");
            statementPlayer.setInt(1, idPlayer);
            statementPlayer.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new ServiceException("You might not have enough xp to increase the level of the pokemon.");
        }

        try
        {
            PreparedStatement statementPokemon;
            statementPokemon = connection.prepareStatement("UPDATE pokemons SET pokemon_level = pokemon_level + 1 WHERE id = ?");
            statementPokemon.setInt(1, idPokemon);
            statementPokemon.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new ServiceException("The pokemon has reached it's maximum level.");
        }



        ArrayList<PokemonEntity> entities = getPokemonsByUserId(idTarget);

        return entities;
    }

    /*
     * Insertion d'un pokémon dans la base de données
     */

    @Override
    public PokemonEntity create(PokemonEntity obj) throws ServiceException
    {
        Connection connection = _Connector.getInstance();
        try
        {
            PreparedStatement statement;

            statement = connection.prepareStatement("INSERT INTO pokemons(id_pokedex, pokemon_level, pokemon_name, sprite, user_id) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1,obj.getId_pokedex());
            statement.setInt(2,obj.getPokemon_level());
            statement.setString(3,obj.getPokemon_name());
            statement.setString(4, obj.getSprite());
            statement.setInt(5, obj.getUser_id());
            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            throw new ServiceException("Could not insert new pokemon !");
        }

        return obj;
    }

    /*
     * Récupération de tous les pokémons
     */

    @Override
    public ArrayList<PokemonEntity> getAll() throws ServiceException
    {
        try
        {
            ArrayList<PokemonEntity> entities   = new ArrayList<>();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM pokemons ORDER BY id_pokedex;");
            ResultSet resultSet                 = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                entities.add(parseFromResultSet(resultSet));
            }

            return entities;
        }
        catch (SQLException e)
        {
            throw new ServiceException("Could not get all pokemons.");
        }
    }

    /*
     * Récupération d'un pokémon à partir de son identifiant
     */
    @Override
    public PokemonEntity getById(int id) throws ServiceException
    {
        try
        {
            PokemonEntity pokemon;
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM pokemons WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            pokemon = parseFromResultSet(resultSet);
            return pokemon;
        }
        catch (SQLException e)
        {
            throw new ServiceException("This pokemon does not exist");
        }
    }

    @Override
    public void update(int id, PokemonEntity obj) throws ServiceException
    {
        throw new ServiceException("Not implemented !");
    }

    @Override
    public void clearTable() throws ServiceException {
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM pokemons;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Could not clear table pokemons.");
        }
    }

    // Méthodes privées
    private PokemonEntity parseFromResultSet(ResultSet resultSet) throws SQLException
    {
        PokemonEntity entity = new PokemonEntity();
        entity.setId(resultSet.getInt("id"));
        entity.setId_pokedex(resultSet.getInt("id_pokedex"));
        entity.setPokemon_level(resultSet.getInt("pokemon_level"));
        entity.setPokemon_name(resultSet.getString("pokemon_name"));
        entity.setSprite(resultSet.getString("sprite"));
        entity.setUser_id(resultSet.getInt("user_id"));
        return entity;
    }
}

