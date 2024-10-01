package com.uca.dao;

import com.uca.entity.UserEntity;
import com.uca.exceptions.ServiceException;

import java.sql.*;
import java.util.ArrayList;

/*
 * Récupération de données et implémentation de données
 */
public class UserDAO extends _Generic<UserEntity>
{
    /*
     * Récupération d'un joueur à partir de son pseudonyme
     */
    public UserEntity getByIdentifier(String identifier) throws ServiceException
    {
        try
        {
            UserEntity user = new UserEntity();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM users WHERE identifier = ?;");
            preparedStatement.setString(1, identifier);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                user = parseFromResultSet(resultSet);
            }
            return user;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not get player by identifier.");
        }
    }

    /*
     * MAJ de la dernière connexion pour récompenses et met à jour son nb-xp à +5 au passage
     */
    public void updateLastConnexionAndXP(int id, Timestamp t) throws ServiceException
    {
        try
        {
            PreparedStatement preparedStatement = this.connect.prepareStatement("UPDATE users SET last_connexion=?, nbxp=nbxp+? WHERE id=?;");
            preparedStatement.setTimestamp(1, t);
            preparedStatement.setInt(2, 5);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not update last connexion.");
        }
    }

    /*
     * Récupération de tous les joueurs
     */
    @Override
    public ArrayList<UserEntity> getAll() throws ServiceException
    {
        try
        {
            ArrayList<UserEntity> entities      = new ArrayList<>();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM users ORDER BY id ASC;");
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
            throw new ServiceException("Could not get all users.");
        }
    }

    /*
     * Récupération d'un joueur à partir de son ID
     */

    @Override
    public UserEntity getById(int id) throws ServiceException
    {
        try
        {
            UserEntity user = new UserEntity();
            PreparedStatement preparedStatement = this.connect.prepareStatement("SELECT * FROM users WHERE id = ? LIMIT 1;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                user = parseFromResultSet(resultSet);
            }
            return user;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new ServiceException("Could not find user : " + id);
        }
    }

    /*
     * Insertion d'un joueur dans la base de données
     */

    @Override
    public UserEntity create(UserEntity obj) throws ServiceException
    {
        Connection connection = _Connector.getInstance();

        try
        {
            PreparedStatement statement;

            statement = connection.prepareStatement("INSERT INTO users(identifier, password, last_connexion, nbxp) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,obj.getIdentifier());
            statement.setString(2,obj.getPassword());
            statement.setTimestamp(3,obj.getLast_connexion());
            statement.setInt(4, obj.getNbxp());
            statement.executeUpdate();

            // On récupère l'ID de l'utilisateur crée pour pouvoir lui donner un pokémon dès son inscription
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            obj.setId(resultSet.getInt(1));
        }

        catch (SQLException e)
        {
            throw new RuntimeException("The pseudonym already exists.");
        }

        return obj;
    }

    @Override
    public void update(int id, UserEntity obj) throws ServiceException {
        throw new ServiceException("Not implemented !");
    }

    @Override
    public void clearTable() throws ServiceException {
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("DELETE FROM users;");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Could not clear table users.");
        }
    }

    // --------- Private methods

    private UserEntity parseFromResultSet(ResultSet resultSet) throws SQLException {
        UserEntity entity = new UserEntity();
        entity.setId(resultSet.getInt("id"));
        entity.setIdentifier(resultSet.getString("identifier"));
        entity.setPassword(resultSet.getString("password"));
        entity.setLast_connexion(resultSet.getTimestamp("last_connexion"));
        entity.setNbxp(resultSet.getInt("nbxp"));
        return entity;
    }

}
