package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public final class GetProductsServlet extends BaseServlet {

    public static final String PATH_SPEC = "/get-product";

    public GetProductsServlet() {
        super();
    }

    public GetProductsServlet(final String databaseUrl) {
        super(databaseUrl);
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        try {
            final String queryResult = productDAO.getAll();
            response.getWriter().println(queryResult);
            setContentType(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
