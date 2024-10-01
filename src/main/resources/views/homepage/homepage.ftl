<#ftl encoding="utf-8">
<#include "/header/header.ftl">

<body xmlns="http://www.w3.org/1999/html">

    <div class="content-homepage">

        <div class="accueil">
            <div>
                <i class="fa-solid fa-users accueil-icon"></i><br/>
                <a href="/players">Liste des joueurs</a>
            </div>
            <div>
                <i class="fa-solid fa-newspaper accueil-icon"></i><br/>
                <a href="/exchange">Liste des échanges</a>
            </div>
            <#if connectedUser??>
                <div>
                    <i class="fa-solid fa-exchange accueil-icon"></i><br/>
                    <a href="/create-exchange">Créer un échange</a>
                </div>
            </#if>
        </div>
    </div>

</body>

</html>