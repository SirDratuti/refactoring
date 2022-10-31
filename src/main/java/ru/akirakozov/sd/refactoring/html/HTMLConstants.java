package ru.akirakozov.sd.refactoring.html;

import static ru.akirakozov.sd.refactoring.dao.QueryMapper.SEPARATOR;

public class HTMLConstants {
    public final static String BODY_TAG = "<html><body>" + SEPARATOR;

    public final static String COLLAPSE_BODY_TAG = "</body></html>";

    public final static String SUM_PRICE_HEADER = "Summary price: " + SEPARATOR;

    public final static String COUNT_PRICE_HEADER = "Number of products: " + SEPARATOR;

    public final static String MAX_PRICE_HEADER = "<h1>Product with max price: </h1>" + SEPARATOR;

    public final static String MIN_PRICE_HEADER = "<h1>Product with min price: </h1>" + SEPARATOR;
}
