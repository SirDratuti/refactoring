package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractServletTest {

    protected final static String SEPARATOR = System.lineSeparator();

    protected final static String NAME_PARAMETER = "name";

    protected final static String PRICE_PARAMETER = "price";

    protected final static String COMMAND_PARAMETER = "command";

    protected final static String DATABASE_URL = "jdbc:sqlite:testing.db";

    protected final static String INIT_TABLE_REQUEST = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    protected final static String CLEAR_REQUEST = "DELETE FROM PRODUCT";

    protected final static String GET_ALL_QUERY = "SELECT * FROM PRODUCT";

    protected final static String ADD_QUERY_PREFIX = "INSERT INTO PRODUCT (NAME, PRICE) VALUES ";

    protected void init() throws SQLException {
        update(INIT_TABLE_REQUEST);
    }

    protected void clear() throws SQLException {
        update(CLEAR_REQUEST);
    }

    protected void add(final Product product) throws SQLException {
        update(ADD_QUERY_PREFIX + toSQL(product));
    }

    protected List<Product> getAll() throws SQLException {
        return query(GET_ALL_QUERY);
    }

    private String toSQL(final Product product) {
        return "(\"" + product.getName() + "\", " + product.getPrice() + ")";
    }

    private static void update(final String update) throws SQLException {
        try (final Connection connection = connect()) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(update);
            }
        }
    }

    private static List<Product> query(final String query) throws SQLException {
        try (final Connection connection = connect()) {
            try (final Statement statement = connection.createStatement()) {
                try (final ResultSet result = statement.executeQuery(query)) {
                    final List<Product> results = new ArrayList<>();
                    while (result.next()) {
                        final String name = result.getString(NAME_PARAMETER);
                        final int price = result.getInt(PRICE_PARAMETER);
                        results.add(new Product(name, price));
                    }
                    return results;
                }
            }
        }
    }

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }
}
