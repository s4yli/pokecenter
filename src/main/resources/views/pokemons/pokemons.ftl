<#ftl encoding="utf-8">
<#include "/header/header.ftl">

<body xmlns="http://www.w3.org/1999/html">

        <#if error??>
                <div class="error">Une erreur est survenue : ${error}</div>
                <a href="/player/${id_recherche}/pokemons">Retour</a>
        <#else>
                <#if !connectedUser??>
                        <p>Le(s) pokémon(s) que le joueur possède :</p>
                <#elseif connectedUser.id != id_recherche>
                        <p>Le(s) pokémon(s) que le joueur possède :</p>
                </#if>
                <table class="table-pokemons">
                        <thead>
                                <tr>
                                        <th>Pokedex</th>
                                        <th>Nom</th>
                                        <th>Niveau</th>
                                        <th>Sprite</th>
                                        <#if connectedUser??>
                                                <th>Action</th>
                                        </#if>
                                </tr>
                        </thead>
                        <#list pokemons as pokemon>
                                <#if !connectedUser??>
                                        <tbody>
                                                <tr>
                                                        <td>${pokemon.id_pokedex}</td>
                                                        <td>${pokemon.pokemon_name}</td>
                                                        <td>${pokemon.pokemon_level}</td>
                                                        <td><img src="${pokemon.sprite}" alt="sprite du pokedex "+ ${pokemon.id_pokedex}></td>
                                                </tr>
                                        </tbody>
                                <#else>
                                        <tbody>
                                                <tr>
                                                        <td>${pokemon.id_pokedex}</td>
                                                        <td>${pokemon.pokemon_name}</td>
                                                        <td>${pokemon.pokemon_level}</td>
                                                        <td><img src="${pokemon.sprite}" alt="sprite du pokedex "+ ${pokemon.id_pokedex}></td>
                                                        <td><a href="/player/${id_recherche}/pokemons/${pokemon.id}/xp">Niveau supérieur</a></td>
                                                </tr>
                                        </tbody>
                                </#if>
                        </#list>
                </table>
                <#if pokemons?size == 0>
                        <p>Ce joueur n'a aucun pokémon.</p>
                </#if>
        </#if>
</body>

</html>
