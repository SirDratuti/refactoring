package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends BaseServlet {

    public AddProductServlet() {
        super();
    }

    public AddProductServlet(final String databaseUrl) {
        super(databaseUrl);
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        final String name = request.getParameter("name");
        final int price = Integer.parseInt(request.getParameter("price"));

        try {
            final Product product = new Product(name, price);
            productDAO.add(product);
            setContentType(response);
            response.getWriter().println("OK");
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
