import java.sql.*;

/**
 * Created by Piotr on 27.02.2018.
 */
public class ProductTypesDao {

    /**
     * Lepiej rozwiązać sprawę zapytania SQL wstawiając od razu String z argumentu metody
     * czy lepiej wstawić znak "?" i dalej preparedStatement.setString(1, typeName);
     * tak jak w metodzie getProductTypeName ?
     * Któreś jest bardziej poprawne, szybsze, czy to raczej bez znaczenia?
     */

    public boolean isProductTypeExistsByName(String typeName) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result = false;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT TypeName FROM product_types WHERE TypeName = \"" + typeName.toLowerCase() + "\"");
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (resultSet.next()) {
                String name = resultSet.getString("TypeName");
                if (name.toLowerCase().equals(typeName.toLowerCase())) {
                    System.out.println("\"" + typeName + "\" product type already exist in database");
                    result = true;
                }
            } else
                System.out.println("Product type \"" + typeName + "\" doesn't exist in database");
            return result;
        } finally {
            preparedStatement.close();
        }
    }

    public boolean isProductTypeExistsById(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result = false;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT TypeID FROM product_types WHERE TypeID = " + id);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (!resultSet.next()) {
                System.out.println("Product type with id: \"" + id + "\" doesn't exist in database");
            } else {
                result = true;
            }
            return result;
        } finally {
            preparedStatement.close();
        }
    }

    public void addNewProductType(String productType) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product_types (TypeName) VALUES (\"" + productType + "\")");
        try {
            if (!isProductTypeExistsByName(productType)) {
                preparedStatement.executeUpdate();
                System.out.println("New product type \"" + productType + "\" is successfully added to the database.");
            }
        } finally {
            preparedStatement.close();
        }
    }

    /**
     * Metoda pobiera z bazy danych, z tabeli product_types nazwę typu produktu i zwracająca String z tą nazwą.
     * Jako argument przyjmuje int id.
     * Jeśli podam id którego nie ma, zwróci pusty resultSet.
     * Pytanie jest następujące, czy powinienem w tej sytuacji wyrzucić wyjątek i zakończyć działanie programu tak jak jest teraz
     * czy lepiej byłoby wyświetlić po porstu że nie ma wyniku dla tego id i przerwać dalsze działanie metody ?
     * Czyli ogólnie, czy lepiej tak jak w tej metodzie, czy tak jak jest w metodzie deleteProductType gdzie po prostu wyświetlam komunikat.
     *
     * @param id
     * @return String z nazwą typu w wierszu o wskazanym id.
     * @throws SQLException
     */
    public String getProductTypeName(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT TypeName FROM product_types WHERE TypeID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (!resultSet.next()) {
                throw new SQLException("There is no product type with id: " + id + " in database.");

            } else {
                String productTypeName = resultSet.getString("TypeName");
                return productTypeName;
            }
        } finally {
            preparedStatement.close();
        }
    }

    public int getNumberOfProductsOfSpecificType(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products WHERE TypeID = " + id);
        int count = 0;
        try {
            if (isProductTypeExistsById(id)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                count = resultSet.getInt(1);
                return count;
            }
            return count;
        } finally {
            preparedStatement.close();
        }
    }

    public void deleteProductType(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product_types WHERE TypeID = ?");
        try {
            String typeName = getProductTypeName(id);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Product type \"" + typeName + "\" is successfully deleted.");

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            String typeName = getProductTypeName(id);
            int numberOfProducts = getNumberOfProductsOfSpecificType(id);
            System.out.println("You can't delete product type \"" + typeName + "\", because you have " + numberOfProducts + " products of that type in your database !!");

        } finally {
            preparedStatement.close();
        }
    }

    public void updateProductType(int id, String productTypeName) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT TypeName FROM product_types WHERE TypeID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (!resultSet.next()) {
                throw new SQLException("There is no record with id: " + id + " in database.");
            } else {
                String oldProductTypeName = resultSet.getString("TypeName");
                preparedStatement = connection.prepareStatement("UPDATE product_types SET TypeName =(\"" + productTypeName + "\") WHERE TypeID = (\"" + id + "\")");
                preparedStatement.executeUpdate();
                System.out.println("Product type name with ID \"" + id + "\" updeted successfully, from \"" + oldProductTypeName + "\" to \"" + productTypeName + "\".");
            }
        } finally {
            preparedStatement.close();
        }
    }
}