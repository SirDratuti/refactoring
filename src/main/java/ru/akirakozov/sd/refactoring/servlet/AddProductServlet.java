package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.QueryMapper;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends BaseServlet {

    public static final String PATH_SPEC = "/add-product";

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
        final String name = request.getParameter(QueryMapper.NAME_PARAMETER);
        final int price = Integer.parseInt(request.getParameter(QueryMapper.PRICE_PARAMETER));

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
