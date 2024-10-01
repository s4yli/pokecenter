<#ftl encoding="utf-8">
<html>

<header>
    <title>PokeCenter</title>
    <link href="/style.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/b56819fe89.js" crossorigin="anonymous"></script>
</header>

<head>
    <div class="topbar">
        <ul>
            <li><a href="/">Accueil</a></li>
            <li>|</li>
            <li><a href="/players">Liste des joueurs</a></li>
            <li>|</li>
            <li><a href="/exchange">Échanges</a></li>
            <#if !connectedUser??>
                <li>|</li>
                <li><a href="/registration">S'inscrire</a></li>
            <#else>
                <li>|</li>
                <li><a href="/create-exchange">Créer un échange</a></li>
                <li>|</li>
                <li><a href="/player/${connectedUser.id}/pokemons">Mes pokémons</a></li>
            </#if>
        </ul>

        <h1 id="logo-title">
            Poke<span id="Center">Center</span>
        </h1>

        <#if !connectedUser??>
            <form class="login" action="/login" method="post">
                <label for="username">Pseudonyme :</label>
                <input id="username" type="text" name="pseudonyme">

                <label for="password">Mot de passe :</label>
                <input id="password" type="password" name="mot-de-passe">

                <input type="submit" value="Se connecter"/>
            </form>
        <#else>
            <ul class="sayHello">
                <li>Bonjour, ${connectedUser.identifier}</li>
                <li>|</li>
                <li><a href="/logout">Déconnexion</a></li>
            </ul>
        </#if>
    </div>

    <div class="trait-separateur"></div>
</head>
