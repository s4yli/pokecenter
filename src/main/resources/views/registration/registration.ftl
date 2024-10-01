<#ftl encoding="utf-8">
<#include "/header/header.ftl">

<body xmlns="http://www.w3.org/1999/html">

    <div class="register-content">
            <#if !connectedUser??>
                <h1>Créer un compte</h1>
                <form class="register" action="/registration" method="post">
                    <label for="username">Pseudonyme :</label>
                    <input id="username" type="text" name="pseudonyme">

                    <label for="password">Mot de passe :</label>
                    <input id="password" type="password" name="mot-de-passe">

                    <input type="submit" value="S'inscrire"/>
                </form>
            <#else>
                <p class="already-register">Vous êtes déjà inscrit ! <a href="/">Retour à l'accueil.</a></p>
            </#if>
    </div>

</body>

</html>
