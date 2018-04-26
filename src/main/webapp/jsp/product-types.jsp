<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<body>
<div>
<h2>Product Types</h2>

<table>
    <thead>
        <tr>
            <td>Id</td>
            <td>Type Name</td>
        </tr>
    </thead>

    <c:forEach var="productType" items="${productTypes}">
        <tr>
            <td>${productType.productTypeId}</td>
            <td>${productType.productTypeName}</td>
        </tr>
    </c:forEach>
</table>

</div>
</body>
</html>