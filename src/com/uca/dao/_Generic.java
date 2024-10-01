package com.uca.dao;

import com.uca.exceptions.ServiceException;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class _Generic<T> {

    public Connection connect = _Connector.getInstance();

    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param obj
     */
    public abstract T create(T obj) throws ServiceException;

    /**
     * Permet de récupérer une entité par son identifiant
     * @param id Identifiant de la ressource à récupérer
     */
    public abstract T getById(int id) throws ServiceException;

    /**
     * Permet de mettre à jour
     * @param obj Objet à mettre à jour
     * @param id Identifiant de la ressouece
     */
    public abstract void update(int id, T obj) throws ServiceException;

    /**
     * Permet de récupérer toutes les entités de la table.
     */
    public abstract ArrayList<T> getAll() throws ServiceException;

    public abstract void clearTable() throws ServiceException;
}
