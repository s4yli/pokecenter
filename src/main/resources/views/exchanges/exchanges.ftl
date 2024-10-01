<#ftl encoding="utf-8">
<#include "/header/header.ftl">




<body xmlns="http://www.w3.org/1999/html">
    <div class="content-exchange">
        <#if error??>
            <div class="error">Une erreur est survenue : ${error}</div>
        <#else>
            <ul class="exchange-liste">
                <#list pokemonsOnExchange as pokemon>
                    <#list exchanges as exchange>
                        <#if pokemon.id == exchange.pokemon>
                            <#if exchange.exchange_state == 0>
                                <li><img src="${pokemon.sprite}" alt=""> ${pokemon.pokemon_name} </li>
                                <#if connectedUser??>
                                    <a href="/exchange/${exchange.id}/wanted">Échanger</a>
                                </#if>
                            </#if>
                        </#if>
                    </#list>
                </#list>
            </ul>
            <#if pokemonsOnExchange?size == 0>
                <p>Aucun échange en cours.</p>
            </#if>
        </#if>
    </div>
</body>

</html>

