package com.uca.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

public class UserEntity 
{
    private int                             id,
                                            nbxp;
    private String                          password,
                                            identifier;
    private Timestamp                       last_connexion;


    public UserEntity() 
    {
        //Ignored !
    }

    public UserEntity(String identifier, String password)
    {
        this.identifier = identifier;
        this.password = password;
        this.nbxp = 5;
        this.last_connexion = new Timestamp(System.currentTimeMillis());
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getNbxp()
    {
        return nbxp;
    }

    public void setNbxp(int nb_xp)
    {
        this.nbxp = nb_xp;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public Timestamp getLast_connexion()
    {
        return last_connexion;
    }

    public void setLast_connexion(Timestamp last_connexion)
    {
        this.last_connexion = last_connexion;
    }

    public boolean is24HoursElapsed(Timestamp formerTimestamp, Timestamp newTimestamp)
    {
        long diffInMillis = Math.abs(newTimestamp.getTime() - formerTimestamp.getTime());
        long millisIn24Hours = 24L * 60L * 60L * 1000L;
        return diffInMillis >= millisIn24Hours;
    }
    public void addDailyXP(){
        this.nbxp += 5;
    }
}
