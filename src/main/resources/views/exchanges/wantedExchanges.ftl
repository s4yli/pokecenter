<#ftl encoding="utf-8">
<#include "/header/header.ftl">

<body xmlns="http://www.w3.org/1999/html">

        <div class="content-wantedExchange">
            <#if error??>
                <div class="error">Une erreur est survenue : ${error}</div>
            <#else>
                <#if connectedUser??>
                        <ul class="wantedExchange-liste">
                                <p>Pour l'ID d'échange : ${exchangeID}</p>
                                <p>Le joueur veut le(s) pokémon(s) suivant(s) (identifiants pokedex):</p>
                                <#list wantedExchanges as wExchange>
                                        <li>${wExchange.id_pokedex}</li>
                                </#list>
                        </ul>
                        <p>Vos pokémons :</p>
                        <table class="table-pokemons">
                                <thead>
                                <tr>
                                        <th>Identifiant</th>
                                        <th>Pokédex</th>
                                        <th>Nom</th>
                                        <th>Niveau</th>
                                        <th>Sprite</th>
                                </tr>
                                </thead>
                                <#list userPokemons as pokemon>
                                        <tbody>
                                        <tr>
                                                <td>${pokemon.id}</td>
                                                <td>${pokemon.id_pokedex}</td>
                                                <td>${pokemon.pokemon_name}</td>
                                                <td>${pokemon.pokemon_level}</td>
                                                <td><img src="${pokemon.sprite}" alt="sprite du pokedex " + ${pokemon.id_pokedex}></td>
                                        </tr>
                                        </tbody>
                                </#list>
                        </table>
                        <form class="validate-exchange" action="/validate-exchange" method="post">
                                <input type="hidden" name="exchangeID" value="${exchangeID}">
                                <label for="id_pokemon">Entrer l'ID du pokémon de votre collection que vous voulez donner en échange :</label>
                                <input id="id_pokemon" type="text" name="id_pokemon">

                                <input type="submit" value="Valider l'échange"/>
                        </form>
                <#else>
                        <div class="error">Vous n'avez pas le droit d'accès à cette page.</div>
                        <a href="/">Retour</a>
                </#if>
            </#if>
        </div>

</body>

</html>
