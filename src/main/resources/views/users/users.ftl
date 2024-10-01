<#ftl encoding="utf-8">
<#include "/header/header.ftl">


<body xmlns="http://www.w3.org/1999/html">

    <div class="content-players">
        <#if error??>
            <div class="error">Une erreur est survenue : ${error}</div>
        <#else>
                <ul class="joueur-liste">
                    <#list users as user>
                            <#if !connectedUser??>
                                <li>Identifiant : ${user.id} - Pseudonyme : ${user.identifier} - <a href="/player/${user.id}/pokemons">Pokémons</a></li>
                            <#else>
                                    <#if connectedUser.id != user.id>
                                        <li>Identifiant : ${user.id} - Pseudonyme : ${user.identifier} - <a href="/player/${user.id}/pokemons">Pokémons</a></li>
                                    </#if>
                            </#if>
                    </#list>
                </ul>
        </#if>
    </div>

</body>

</html>