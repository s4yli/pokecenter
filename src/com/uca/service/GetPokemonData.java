package com.uca.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URL;


public class GetPokemonData
{
    public static String[] getData() throws IOException
    {
        String randomNumber = GetRandomNumber.stringRandomNumber(1008);
        String StringURLSprite = "https://pokeapi.co/api/v2/pokemon/" + randomNumber;
        String StringURLNameFR = "https://pokeapi.co/api/v2/pokemon-species/" + randomNumber;

        String[] data = new String[3];
        String jsonUrl = StringURLNameFR;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new URL(jsonUrl));
        String name = "";
        for (JsonNode nameNode : rootNode.get("names"))
        {
            if (nameNode.get("language").get("name").asText().equals("fr"))
            {
                name = nameNode.get("name").asText();
                break;
            }
        }

        jsonUrl = StringURLSprite;
        mapper = new ObjectMapper();
        rootNode = mapper.readTree(new URL(jsonUrl));
        String frontDefault = rootNode.get("sprites").get("front_default").asText();

        data[0] = name;
        data[1] = frontDefault;
        data[2] = randomNumber;
        return data;
    }

    
}
