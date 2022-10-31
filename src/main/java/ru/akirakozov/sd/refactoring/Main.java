package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.BaseServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {

    private static final int DEFAULT_PORT = 8081;

    public static void main(final String[] args) throws Exception {
        final ProductDAO productDAO = new ProductDAO(getUrl(args));
        productDAO.init();

        Server server = new Server(DEFAULT_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), AddProductServlet.PATH_SPEC);
        context.addServlet(new ServletHolder(new GetProductsServlet()), GetProductsServlet.PATH_SPEC);
        context.addServlet(new ServletHolder(new QueryServlet()), QueryServlet.PATH_SPEC);

        server.start();
        server.join();
    }

    private static String getUrl(final String[] args) {
        if (args.length == 0) {
            return BaseServlet.DEFAULT_DATABASE_URL;
        } else {
            return args[0];
        }
    }
}
