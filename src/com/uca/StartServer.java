package com.uca;

import com.uca.JWT.Login;
import com.uca.core.ExchangeCore;
import com.uca.core.PokemonCore;
import com.uca.core.UserCore;
import com.uca.core.WantedExchangeCore;
import com.uca.dao.ExchangeDAO;
import com.uca.dao.PokemonDAO;
import com.uca.dao.UserDAO;
import com.uca.dao.WantedExchangeDAO;
import com.uca.dao._Initializer;
import com.uca.entity.ExchangeEntity;
import com.uca.entity.WantedExchangeEntity;
import com.uca.entity.PokemonEntity;
import com.uca.entity.UserEntity;
import com.uca.exceptions.ServiceException;
import com.uca.gui.*;
import com.uca.service.GetPokemonData;
import spark.Request;
import java.util.Scanner;

import java.io.IOException;
import java.sql.Timestamp;
import static spark.Spark.*;

public class StartServer {

    /*
    * GET '/'
    *    → Page d’accueil
    *
    * GET 'players'
    *    → Retourne la liste des joueurs
    *
    * GET '/player/:id_player/pokemons'
    *    → Retourne la liste de pokémons appartenant au joueur avec l’identifiant = :id_player
    *
    * GET '/player/:id_player/pokemons/:id_pokemon/xp'
    *    → Retourne la liste de pokémons appartenant au joueur avec l’identifiant = :id_player et incrémente le niveau du pokemon d’identifiant = :id_pokemon de 1. Décrémente le nombre d’xp du joueur qui a pexé le pokémon de 1.
    *
    * GET '/exchange'
    *    → Retourne la liste des pokémons mis en échange
    *
    * GET '/exchange/:id/wanted'
    *    → Retourne la liste des échanges désirés à partir de l’identifiant d’un échange, contient également un formulaire 'validate-exchange'.
    * POST '/validate-exchange'
    *    → Valide l’échange si le joueur possède le pokémon désiré
    *
    * GET '/registration'
    *    → Retourne la page d’inscription (formulaire)
    * POST '/registration'
    *    → Insère le joueur dans la base de données
    *
    * GET '/create-exchange'
    *    → Retourne la page de création d’échange (plusieurs formulaire)
    * POST '/create-exchange'
    *    → Insère un échange dans la base de données avec le statut à 1 pour indiquer que l’échange est toujours en cours de validation
    * POST '/add-pokemon'
    *    → Ajoute une instance wanted-exchange contenant un pokedex associé à l’identifiant d’un échange
    * POST '/insert-exchange'
    *    → Finalisation de l’échange, l’échange est mis à jour au statut = 0 pour indiquer que l’échange est bien en cours et est disponible dans la liste d’échange.
    *
    *
    * POST '/login'
    *    → Connecte le joueur
    * GET '/logout'
    *    → Déconnecte le joueur
    */


    public static void main(String[] args) {
        // Effacer la base de données si besoin
        // clearDatabase();
        
        //Configure Spark
        staticFiles.location("/static/");
        port(8100);


        _Initializer.Init();

        //Defining our routes

        get("/", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);
            return HomepageGUI.getHomepage(connectedUser);
        });

        get("/players", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);
            return UserGUI.getAllUsers(connectedUser);
        });

        get("/player/:id_player/pokemons", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            int idPlayer = parseIdFromRequest(req, "id_player");
            if (idPlayer == -1) {
                res.status(400);
                return null;
            }

            return PokemonGUI.getPokemonsByUserId(connectedUser, idPlayer);
        });

        get("/player/:id_player/pokemons/:id_pokemon/xp", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            if (connectedUser == null) {
                res.redirect("/", 301);
                return null;
            }

            int idTarget = parseIdFromRequest(req, "id_player");
            if (idTarget == -1) {
                res.status(400);
                return null;
            }

            int idPokemon = parseIdFromRequest(req, "id_pokemon");
            if (idPokemon == -1) {
                res.status(400);
                return null;
            }

            return PokemonGUI.getPokemonsByIdAndLevelUp(connectedUser, idTarget, idPokemon, connectedUser.getId());
        });

        get("/exchange", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);
            return ExchangeGUI.getAllExchanges(connectedUser);
        });

        get("/exchange/:id/wanted", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            int id = parseIdFromRequest(req, "id");
            if (id == -1) {
                res.status(400);
                return null;
            }

            return ExchangeGUI.getAllWantedExchangesById(connectedUser, id);
        });

        get("/registration", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);
            return UserGUI.getRegistration(connectedUser);
        });

        get("/create-exchange", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            if (connectedUser == null) {
                res.redirect("/", 301);
                return null;
            }

            return ExchangeGUI.getCreateExchange(connectedUser);
        });

        post("/create-exchange", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            if (connectedUser == null) {
                res.redirect("/", 301);
                return null;
            }

            if (isAnyFieldEmpty(req, new String[]{"pokemon_id"}))
            {
                res.redirect("/students", 301);
                return null;
            }

            try
            {
                PokemonEntity pokemon = PokemonCore.getPokemonById(Integer.parseInt(req.queryParams("pokemon_id")));
                ExchangeEntity newExchange = new ExchangeEntity();

                newExchange.setExchange_date(new Timestamp(System.currentTimeMillis()));
                newExchange.setExchange_state(1);
                newExchange.setPokemon(pokemon.getId());

                if (pokemon.getUser_id() == connectedUser.getId())
                {
                    ExchangeCore.createNewExchange(newExchange);
                }

                else
                {
                    throw new ServiceException("This pokemon is not yours.");
                }
            }
            catch (Exception e)
            {
                return "Une erreur est survenue : " + e.getMessage();
            }

            res.redirect("/create-exchange", 301);

            return "";
        });

        post("/add-pokemon", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            if (connectedUser == null) {
                res.redirect("/", 301);
                return null;
            }

            if (isAnyFieldEmpty(req, new String[]{"id_pokedex", "exchange_id"}))
            {
                res.redirect("/students", 301);
                return null;
            }

            WantedExchangeEntity addWantedExchange = new WantedExchangeEntity();
            addWantedExchange.setId_pokedex(Integer.parseInt(req.queryParams("id_pokedex")));
            addWantedExchange.setId(Integer.parseInt(req.queryParams("exchange_id")));

            WantedExchangeCore.addNewWantedExchange(addWantedExchange);

            res.redirect("/create-exchange", 301);

            return "";
        });

        post("/insert-exchange", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            if (connectedUser == null) {
                res.redirect("/", 301);
                return null;
            }

            if (isAnyFieldEmpty(req, new String[]{"exchange_id"}))
            {
                res.redirect("/students", 301);
                return null;
            }

            try
            {
                ExchangeCore.updateState(Integer.parseInt(req.queryParams("exchange_id")), 0);
            }
            catch (Exception e)
            {
                return "Une erreur est survenue : " + e.getMessage();
            }

            res.redirect("/create-exchange", 301);

            return "";
        });

        post("/validate-exchange", (req, res) -> {
            UserEntity connectedUser = getAuthenticatedUser(req);

            if (connectedUser == null) {
                res.redirect("/", 301);
                return null;
            }

            if (isAnyFieldEmpty(req, new String[]{"id_pokemon", "exchangeID"}))
            {
                res.redirect("/students", 301);
                return null;
            }

            try
            {
                PokemonEntity pokemon = PokemonCore.getPokemonById(Integer.parseInt(req.queryParams("id_pokemon")));
                ExchangeEntity exchange = ExchangeCore.getExchangeById(Integer.parseInt(req.queryParams("exchangeID")));

                int player1ID = pokemon.getUser_id();
                int player2ID = ExchangeCore.getIdOfExchangeCreator(exchange.getId());
                int pokemon1ID = pokemon.getId();
                int pokemon2ID = exchange.getPokemon();

                System.out.println((WantedExchangeCore.isPokemonInOneOfWantedExchange(pokemon.getId_pokedex(), exchange.getId())));
                System.out.println(player1ID == connectedUser.getId());
                System.out.println((!ExchangeCore.isPokemonOnExchange(pokemon1ID)));


                // On vérifie que le pokémon entrée est bien dans la liste d'échange attendus du créateur d'échange et qu'il soit le propriétaire du pokémon et que le pokémon ne soit pas actuellement dans la liste des échanges
                if ((WantedExchangeCore.isPokemonInOneOfWantedExchange(pokemon.getId_pokedex(), exchange.getId()) && player1ID == connectedUser.getId() && (!ExchangeCore.isPokemonOnExchange(pokemon1ID))))
                {
                    PokemonCore.changePokemon(player1ID, player2ID, pokemon1ID, pokemon2ID);
                    ExchangeCore.deleteById(exchange.getId());
                }
                else
                {
                    throw new ServiceException("The exchange did not occur.");
                }

            }
            catch (Exception e)
            {
                return "Une erreur est survenue : " + e.getMessage();
            }

            res.redirect("/", 301);

            return "";
        });

        post("/registration", (req, res) -> {
            String username = req.queryParams("pseudonyme");
            String password = req.queryParams("mot-de-passe");
            UserEntity newUser = new UserEntity(username, password);

            try
            {
                newUser = UserCore.registerPlayer(newUser);

                // Nouveau pokémon pour l'utilisateur inscrit
                newPokemonToUser(newUser.getId());
                res.redirect("/", 301);
            }
            catch (Exception e)
            {
                return "Une erreur est survenue : " + e.getMessage();
            }
            return "";
        });

        post("/login", (req,res) ->
        {
            String username = req.queryParams("pseudonyme");
            String password = req.queryParams("mot-de-passe");

            if (username != null || password != null)
            {
                UserEntity user = new UserEntity(username, password);

                try
                {
                    res.cookie("/", "auth", Login.doLogin(user), 36000, false, true);

                    // Connexion réussi, il faut alors actualiser la valeur last_connexion
                    // et vérifier si 24 heures ce sont écoulées pour donner un pokémon et incrémenter son nbxp de 5

                    user = UserCore.getByIdentifier(username);
                    Timestamp newTimestamp = new Timestamp(System.currentTimeMillis());
                    Timestamp formerTimestamp = user.getLast_connexion();

                    if (user.is24HoursElapsed(formerTimestamp, newTimestamp))
                    {
                        newPokemonToUser(user.getId());
                        UserCore.updateLastConnexionAndXP(user.getId(), newTimestamp);
                    }

                }
                catch (Exception e)
                {
                    res.redirect("/", 301);
                }
            }
            res.redirect("/", 301);
            return "";
        });

        get("/logout", (req, res) -> {
           res.removeCookie("auth");
           res.redirect("/", 301);
           return "";
        });

    }

    // Méthodes privées

    private static int parseIdFromRequest(spark.Request req, String idName) {
        String id = req.params(":" + idName);
        // Si l'id est null ou pas un entier, alors on retourne une erreur
        if (id == null) {
            return -1;
        }

        int myId;

        try {
            myId = Integer.parseInt(id);
        } catch (Exception e) {
            return -1;
        }

        return myId;
    }
    
    private static boolean isAnyFieldEmpty(spark.Request req, String[] fields) {
        for (String f : fields) {
            if (req.queryParams(f) == null || req.queryParams(f).isBlank()) {
                return true;
            }
        }
        return false;
    }

    private static UserEntity getAuthenticatedUser(Request req)
    {
        String token = req.cookie("auth");
        return token == null ? null : Login.introspect(token);
    }

    private static void newPokemonToUser(int idPlayer) throws IOException, ServiceException {
        String[] nameAndSpriteAndPokedexID = GetPokemonData.getData();
        PokemonEntity newPokemon = new PokemonEntity(nameAndSpriteAndPokedexID[0], Integer.parseInt(nameAndSpriteAndPokedexID[2]), idPlayer, nameAndSpriteAndPokedexID[1]);
        PokemonCore.newPokemon(newPokemon);
    }

    /**
     * Clears all tables in the database by invoking the clearTable method on each DAO.
     */
    private static void clearDatabase() {
        try {
            new UserDAO().clearTable();
            new WantedExchangeDAO().clearTable();
            new ExchangeDAO().clearTable();
            new PokemonDAO().clearTable();
            System.out.println("All tables have been cleared.");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}