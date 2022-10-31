package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.akirakozov.sd.refactoring.commands.Commands.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends BaseServlet {

    public static final String PATH_SPEC = "/query";

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
                case MAX_COMMAND:
                    queryResult = productDAO.getMax();
                    break;
                case MIN_COMMAND:
                    queryResult = productDAO.getMin();
                    break;
                case SUM_COMMAND:
                    queryResult = productDAO.getSum();
                    break;
                case COUNT_COMMAND:
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
