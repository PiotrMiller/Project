<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css\WebPageCSS.css">
    <title>BetterThanBestBuy - Electronic Web Store</title>

</head>

<body>
    <div class="container">
        <div class="top">
            <div class="header">
                <h2>BetterThanBestBuy</h2>
                <h4>Electronic Web Store</h4>
            </div>

            <ul class="main-nav">
                <li><a class="home" onclick="window.location.href='http://localhost:8080/main'">home</a></li>
                <li><a onclick="window.location.href='http://localhost:8080/productTypes'">products types </a></li>
                <li><a onclick="window.location.href='http://localhost:8080/brands'">brands</a></li>
                <li style="float: right"><a class="lastWithoutBorder" href="#contact">contact</a></li>
                <li style="float: right"><a class="leftBorder" href="#cart">cart</a></li>
            </ul>
        </div>

        <div class="main_text">

            <h3 class="About_us">
                Witam w wirtualnym sklepie internetowym.<br>
                Sklep powstał w celu nauki tworzenia aplikacji webowych poprzez praktykę.<br>
                Przy tworzeniu tego projektu wykorzystuję następujące jezyki programowania: HTML, CSS, Java, SQL.<br>
                Jest to strona dynamiczna, która pobiera zawartość bazy danych a następnie wyświetla ją.<br>
                Strona nieustannie rozwija się wraz z moimi umiejętnościami z zakresu programowania.<br>
                <br>
            </h3>

        </div>

        <div id="contact">
            e-mail: piotrmiller90@gmail.com
        </div>

    </div>

</body>
</html>

<!-- java -jar target/shop-app-1.0-SNAPSHOT-jar-with-dependencies.jar -->