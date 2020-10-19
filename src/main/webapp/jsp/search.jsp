<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <jsp:include page="/jsp/header.jsp" />

        <h1>Idan's Restaurant</h1>
        <h2>Find your favorite dish!</h2>
        <form method="GET" action="/searchResults">
            <p>Search for dishes: <input type="text" name="searchQuery" placeholder="search.."/></p>
            <input type="submit" value="search" />
        </form>

        <jsp:include page="/jsp/footer.jsp" />
    </body>
</html>