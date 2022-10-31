package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class QueryServlet extends BaseServlet {

    public QueryServlet() {
        super();
    }

    public QueryServlet(final String databaseUrl) {
        super(databaseUrl);
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final String command = request.getParameter("command");
        final String queryResult;
        try {
            switch (command) {
                case "max":
                    queryResult = productDAO.getMax();
                    break;
                case "min":
                    queryResult = productDAO.getMin();
                    break;
                case "sum":
                    queryResult = productDAO.getSum();
                    break;
                case "count":
                    queryResult = productDAO.getCount();
                    break;
                default:
                    queryResult = "Unknown command: " + command;
                    break;
            }
            response.getWriter().println(queryResult);
            setContentType(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
