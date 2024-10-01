package com.uca.core;

import com.uca.dao.UserDAO;
import com.uca.entity.UserEntity;
import com.uca.exceptions.ServiceException;


import java.sql.Timestamp;
import java.util.ArrayList;

public class UserCore
{

    public static ArrayList<UserEntity> getAll() throws ServiceException
    {
        return new UserDAO().getAll();
    }

    public static UserEntity getByIdentifier(String identifier) throws ServiceException
    {
        return new UserDAO().getByIdentifier(identifier);
    }

    public static UserEntity registerPlayer(UserEntity newUser) throws ServiceException
    {
        return new UserDAO().create(newUser);
    }

    public static void updateLastConnexionAndXP(int id, Timestamp t) throws ServiceException
    {
        new UserDAO().updateLastConnexionAndXP(id, t);
    }
}
