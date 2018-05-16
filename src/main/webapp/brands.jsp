<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css\ProductsBrands.css">
    <title>BetterThanBestBuy - Electronic Web Store / Brands</title>

</head>

    <body>
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

        <div class="center">

            <div class="main_text">
              <h3>Mamy w swojej ofercie produkty następujących marek:</h3>
            </div>

        </div>

        <div id="contact">
            e-mail: piotrmiller90@gmail.com
        </div>

    </body>
</html>

<!-- java -jar target/shop-app-1.0-SNAPSHOT-jar-with-dependencies.jar -->