package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;

abstract class BaseServlet extends HttpServlet {

    protected final static String DEFAULT_DATABASE_URL = "jdbc:sqlite:test.db";

    protected String databaseUrl;

    public BaseServlet() {
        this.databaseUrl = DEFAULT_DATABASE_URL;
    }

    public BaseServlet(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
}
