package com.uca.entity;

import java.sql.Array;
import java.sql.Timestamp;

public class ExchangeEntity
{
    private int             id,
                            pokemon,
                            exchange_state;
    private Timestamp       exchange_date;

    public ExchangeEntity()
    {
        //Ignored !
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Timestamp getExchange_date()
    {
        return exchange_date;
    }

    public void setExchange_date(Timestamp exchange_date)
    {
        this.exchange_date = exchange_date;
    }

    public int getPokemon()
    {
        return pokemon;
    }

    public void setPokemon(int pokemon)
    {
        this.pokemon = pokemon;
    }

    public int getExchange_state()
    {
        return exchange_state;
    }

    public void setExchange_state(int exchange_state)
    {
        this.exchange_state = exchange_state;
    }

}