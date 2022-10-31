package ru.akirakozov.sd.refactoring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryMapper {

    public final static String NAME_PARAMETER = "name";

    public final static String PRICE_PARAMETER = "price";

    private final static String SEPARATOR = System.lineSeparator();

    private final static String BODY_TAG = "<html><body>" + SEPARATOR;

    private final static String COLLAPSE_BODY_TAG = "</body></html>";

    public final static String SUM_PRICE_HEADER = "Summary price: " + SEPARATOR;

    public final static String COUNT_PRICE_HEADER = "Number of products: " + SEPARATOR;

    public final static String MAX_PRICE_HEADER = "<h1>Product with max price: </h1>" + SEPARATOR;

    public final static String MIN_PRICE_HEADER = "<h1>Product with min price: </h1>" + SEPARATOR;

    public static String mapToList(
            final ResultSet resultSet,
            final String header
    ) throws SQLException {
        final StringBuilder builder = new StringBuilder();
        builder.append(BODY_TAG);
        if (header != null) {
            builder.append(header);
        }
        while (resultSet.next()) {
            String name = resultSet.getString(NAME_PARAMETER);
            int price = resultSet.getInt(PRICE_PARAMETER);
            builder.append(name).append("\t").append(price).append("</br>").append(SEPARATOR);
        }
        builder.append(COLLAPSE_BODY_TAG);
        return builder.toString();
    }

    public static String mapToInteger(
            final ResultSet resultSet,
            final String header
    ) throws SQLException {
        final StringBuilder builder = new StringBuilder();
        builder.append(BODY_TAG);
        if (header != null) {
            builder.append(header);
        }
        if (resultSet.next()) {
            builder.append(resultSet.getInt(1));
        }
        builder.append(COLLAPSE_BODY_TAG);
        return builder.toString();
    }
}
