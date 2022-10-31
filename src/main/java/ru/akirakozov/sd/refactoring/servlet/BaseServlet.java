package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {

    public final static String DEFAULT_DATABASE_URL = "jdbc:sqlite:test.db";

    protected final String databaseUrl;

    protected final ProductDAO productDAO;

    public BaseServlet() {
        this(DEFAULT_DATABASE_URL);
    }

    public BaseServlet(final String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.productDAO = new ProductDAO(databaseUrl);
    }

    protected void setContentType(final HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
