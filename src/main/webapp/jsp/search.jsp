<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Idan's Restaurant - find your favorite dish!</title>
</head>
<body>
    <jsp:include page="/header.jsp" />

    <h1>Idan's Restaurant</h1>
    <h2>Find your favorite dish!</h2>
    <form method="GET" action="/searchResults">
        <p>Search for dishes: <input type="text" name="searchQuery" placeholder="search.."/></p>
        <input type="submit" value="search" />
    </form>

    <jsp:include page="/footer.jsp" />
</body>
</html>