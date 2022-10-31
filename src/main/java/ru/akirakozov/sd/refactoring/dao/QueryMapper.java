package ru.akirakozov.sd.refactoring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.akirakozov.sd.refactoring.html.HTMLConstants.BODY_TAG;
import static ru.akirakozov.sd.refactoring.html.HTMLConstants.COLLAPSE_BODY_TAG;

public class QueryMapper {

    public final static String NAME_PARAMETER = "name";

    public final static String PRICE_PARAMETER = "price";

    public final static String SEPARATOR = System.lineSeparator();

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
            final String name = resultSet.getString(NAME_PARAMETER);
            final int price = resultSet.getInt(PRICE_PARAMETER);
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
