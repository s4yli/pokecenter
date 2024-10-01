# POKECENTER

## Fonctionnalités importantes

- Récupération des données depuis un site externe.
- Donner un Pokémon au hasard chaque jour et incrémenter de 5 le nombre d’XP que le joueur peut gagner.
- Échange de Pokémon (un utilisateur peut proposer un échange visible par tous).
- Valider un échange.
- Inscription / Connexion / Déconnexion.
- Faire monter en niveau un Pokémon.

## Principales classes de l'application

Les principales classes de l’application seront :

- `PokemonEntity` (Nom ; ID unique ; ID Pokédex ; UtilisateurID ; Sprite (image) ; Niveau Pokémon)
- `UserEntity` (ID unique ; Identifiant ; Mot de passe ; Date de dernière connexion pour récompense ; Nombre de Pokémon XP)
- `ExchangeEntity` (ID unique ; IDPokémon ; Date ; Statut de l’échange ; Date de création de l’échange)
- `WantedExchangeEntity` (ID, Pokedex)

## Tables de la base de données

- `users` :
  - `id` int primary key auto_increment
  - `identifiant` varchar(32) unique
  - `password` varchar(255)
  - `last_connection` timestamp

- `pokemons`:
  - `id` int primary key auto_increment,
  - `id_pokedex` int
  - `pokemon_level` int	check (pokemon_level <= 100),
  - `pokemon_name` varchar(64),
  - `sprite` varchar(255),
  - `user_id` varchar(32) reference users.id

- `exchanges`
  - `id` int primary key auto_increment,
  - `pokemon_id` int reference pokemon.id unique,
  - `date` timestamp DEFAULT NOW(),
  - `state` int DEFAULT 1
		
- `wanted-exchange`
  - `id_echange` int references echange.id,
  - `id_pokedex` int check (id_pokedex >= 0 AND id_pokedex <= 1008)

## Routes de l'API

- **GET** `/exchange`  
  Retourne la liste des Pokémon mis en échange.

- **GET** `/exchange/:id/wanted`  
  Retourne la liste des échanges désirés à partir de l’identifiant d’un échange, contient également un formulaire “validate-exchange”.

- **POST** `/validate-exchange`  
  Valide l’échange si le joueur possède le Pokémon désiré.

- **GET** `/registration`  
  Retourne la page d’inscription (formulaire).

- **POST** `/registration`  
  Insère le joueur dans la base de données.

- **GET** `/create-exchange`  
  Retourne la page de création d’échange (plusieurs formulaires).

- **POST** `/create-exchange`  
  Insère un échange dans la base de données avec le statut à 1 pour indiquer que l’échange est toujours en cours de validation.

- **POST** `/add-pokemon`  
  Ajoute une instance `wanted-exchange` contenant un Pokedex associé à l’identifiant d’un échange.

- **POST** `/insert-exchange`  
  Finalisation de l’échange, l’échange est mis à jour au statut = 0 pour indiquer que l’échange est bien en cours et est disponible dans la liste d’échange.

- **POST** `/login`  
  Connecte le joueur.

- **GET** `/logout`  
  Déconnecte le joueur.

## Comment ça fonctionne

1) Pour lancer le serveur :
    ```bash
    ./gradlew run
    ```

2) Si vous rencontrez une erreur de permission, exécutez la commande suivante pour donner les permissions nécessaires et exécuter :
    ```bash
    chmod +x ./gradlew && ./gradlew run
    ```

3) Le serveur se lance sur le port 8100 de votre machine. Ouvrez votre navigateur et accédez à l'URL suivante : [http://localhost:8100]