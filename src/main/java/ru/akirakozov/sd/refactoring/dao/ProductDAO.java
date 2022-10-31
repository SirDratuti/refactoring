package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.html.HTMLConstants;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;

final public class ProductDAO {

    private final String databaseUrl;

    public ProductDAO(final String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    private final static String INIT_TABLE_REQUEST = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    private final static String CLEAR_REQUEST = "DELETE FROM PRODUCT";

    private final static String GET_ALL_QUERY = "SELECT * FROM PRODUCT";

    private final static String MAX_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";

    private final static String MIN_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";

    private final static String SUM_QUERY = "SELECT SUM(price) FROM PRODUCT";

    private final static String COUNT_QUERY = "SELECT COUNT(*) FROM PRODUCT";

    private final static String ADD_QUERY_PREFIX = "INSERT INTO PRODUCT (NAME, PRICE) VALUES ";

    public void init() throws SQLException {
        update(INIT_TABLE_REQUEST);
    }

    public void clear() throws SQLException {
        update(CLEAR_REQUEST);
    }

    public void add(final Product product) throws SQLException {
        update(ADD_QUERY_PREFIX + toSQL(product));
    }

    public String getAll() throws SQLException {
        return query(GET_ALL_QUERY, null, QueryMapper::mapToList);
    }

    public String getMax() throws SQLException {
        return query(MAX_QUERY, HTMLConstants.MAX_PRICE_HEADER, QueryMapper::mapToList);
    }

    public String getMin() throws SQLException {
        return query(MIN_QUERY, HTMLConstants.MIN_PRICE_HEADER, QueryMapper::mapToList);
    }

    public String getSum() throws SQLException {
        return query(SUM_QUERY, HTMLConstants.SUM_PRICE_HEADER, QueryMapper::mapToInteger);
    }

    public String getCount() throws SQLException {
        return query(COUNT_QUERY, HTMLConstants.COUNT_PRICE_HEADER, QueryMapper::mapToInteger);
    }

    private String toSQL(final Product product) {
        return "(\"" + product.getName() + "\", " + product.getPrice() + ")";
    }

    private void update(final String update) throws SQLException {
        try (final Connection connection = connect()) {
            try (final Statement statement = connection.createStatement()) {
                statement.executeUpdate(update);
            }
        }
    }

    private String query(
            final String query,
            final String header,
            final ThrowingFunction<ResultSet, String, String> mapper
    ) throws SQLException {
        try (final Connection connection = connect()) {
            try (final Statement statement = connection.createStatement()) {
                try (final ResultSet result = statement.executeQuery(query)) {
                    return mapper.invoke(result, header);
                }
            }
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    private interface ThrowingFunction<ResultSet, Header, Result> {
        Result invoke(final ResultSet resultSet, final Header header) throws SQLException;
    }
}
