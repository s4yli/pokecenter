<#ftl encoding="utf-8">
<#include "/header/header.ftl">

<body xmlns="http://www.w3.org/1999/html">

    <div class="create-exchange">
        <#if error??>
            <div class="error">Une erreur est survenue : ${error}</div>
        <#else>
            <#if !connectedUser??>
                <div class="error">Vous n'avez pas le droit d'accès à cette page.</div>
                <a href="/">Retour</a>
            <#else>
                <p>Vos pokémons :</p>
                <table class="table-pokemons">
                    <thead>
                    <tr>
                        <th>Identifiant</th>
                        <th>Pokedex</th>
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
                            <td><img src="${pokemon.sprite}" alt="sprite du pokedex "+ ${pokemon.id_pokedex}></td>
                        </tr>
                        </tbody>
                    </#list>
                </table>
                <form class="create-exchange" method="post">
                    <label for="pokemon_id">Identifiant de votre pokémon à placer en échange :</label>
                    <input id="pokemon_id" type="text" name="pokemon_id">

                    <input type="submit" value="Créer"/>
                </form>

            <#assign hasExchanges = false>
            <#list userExchanges as exchange>
                <#if exchange.exchange_state == 1>
                    <#assign hasExchanges = true>
                </#if>
            </#list>

            <#if hasExchanges>
                <p class="lien-externe">Vous pouvez voir en <a href="https://www.pokepedia.fr/Liste_des_Pok%C3%A9mon_dans_l%27ordre_du_Pok%C3%A9dex_National" target="_blank">cliquant ici</a> tous le pokémons avec leur pokédex. </p>
                <p>Voici vos échanges en cours <strong>(non validé)</strong> :</p>
            </#if>

            <#list userExchanges as exchange>
                <#if exchange.exchange_state == 1>
                    <ul class="pokemon-in-exchange">
                        <li>ID du pokémon mis en échange : ${exchange.pokemon}</li>
                    </ul>
                    <form class="add-pokemon"action="/add-pokemon" method="post">
                        <input type="hidden" name="exchange_id" value="${exchange.id}">
                        <label for="id_pokedex">Pokedex du pokémon que vous souhaitez en échange entre 0 et 1008 compris (vous pouvez en entrer plusieurs avant de valider) :</label>
                        <input id="id_pokedex" type="text" name="id_pokedex">

                        <input type="submit" value="+"/>
                    </form>
                    <form class="insert-exchange" action="/insert-exchange" method="post">
                        <input type="hidden" name="exchange_id" value="${exchange.id}">
                        <input type="submit" value="Valider"/>
                    </form>
                </#if>
            </#list>
            </#if>
        </#if>
    </div>

</body>

</html>

