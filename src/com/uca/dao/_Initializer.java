package com.uca.dao;

import java.sql.*;

public class _Initializer
{

    public static void Init()
    {
        Connection connection = _Connector.getInstance();

        try
        {
            PreparedStatement statement;

            // Initialisation des tables

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id int primary key auto_increment, identifier varchar(32) unique, password varchar(255), last_connexion timestamp, nbxp int, CHECK (nbxp >= 0));");
            statement.executeUpdate();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS pokemons (id int primary key auto_increment, id_pokedex int, pokemon_level int, pokemon_name varchar(64), sprite varchar(255), user_id int, FOREIGN KEY (user_id) REFERENCES users(id), CHECK (pokemon_level <=100));");
            statement.executeUpdate();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS exchanges (id int primary key auto_increment, pokemon_id int unique, exchange_date timestamp DEFAULT NOW(), exchange_state int DEFAULT 1, FOREIGN KEY (pokemon_id) REFERENCES pokemons(id));");
            statement.executeUpdate();

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS wanted_exchange (id int, id_pokedex int, FOREIGN KEY (id) REFERENCES exchanges(id), CHECK (id_pokedex >= 0 AND id_pokedex <=1008));");
            statement.executeUpdate();
        }

        catch (Exception e)
        {
            System.out.println(e.toString());
            throw new RuntimeException("Could not create database !");
        }
    }
}
