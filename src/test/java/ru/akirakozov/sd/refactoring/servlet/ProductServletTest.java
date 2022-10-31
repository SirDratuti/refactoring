package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.commands.Commands.*;
import static ru.akirakozov.sd.refactoring.dao.QueryMapper.*;
import static ru.akirakozov.sd.refactoring.html.HTMLConstants.*;


import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.Main;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

final class ProductServletTest {

    private final static String SEPARATOR = System.lineSeparator();

    private final static String COMMAND_PARAMETER = "command";

    private final static String DATABASE_URL = "jdbc:sqlite:testing.db";

    private final static String OK_STATUS = "OK";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private final static ProductDAO productDAO = new ProductDAO(DATABASE_URL);

    private static Thread serverThread;

    @BeforeAll
    static void initServer() {
        final String[] arguments = {DATABASE_URL};
        serverThread = new Thread(() -> {
            try {
                Main.main(arguments);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

    }

    @AfterAll
    static void shutdownServer() {
        serverThread.interrupt();
    }

    @BeforeEach
    void initMocks() throws SQLException {
        MockitoAnnotations.openMocks(this);
        productDAO.init();
    }

    @AfterEach
    void clearAll() throws SQLException {
        productDAO.clear();
    }

    @Test
    public void checkResponseCode() throws IOException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initAddRequest();
        initResponse(writer);

        new AddProductServlet(DATABASE_URL).doGet(request, response);
        writer.flush();
        assertEquals(OK_STATUS, stringWriter.toString().trim());
    }

    @Test
    public void checkObjectAddedCorrectly() throws SQLException, IOException {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initAddRequest();
        initResponse(writer);

        new AddProductServlet(DATABASE_URL).doGet(request, response);
        writer.flush();
        assertEquals(OK_STATUS, stringWriter.toString().trim());

        final String allProducts = productDAO.getAll();
        assertGetQuery(allProducts, toHtml(new Product("Xiaomi", 200)));
    }

    @Test
    public void checkGetServlet() throws SQLException, IOException {
        fill();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initResponse(writer);

        new GetProductsServlet(DATABASE_URL).doGet(request, response);
        writer.flush();

        final String allProducts = productDAO.getAll();
        assertEquals(stringWriter.toString().trim(), allProducts);
    }

    @Test
    public void checkMaxQuery() throws SQLException, IOException {
        fill();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initQueryRequest(MAX_COMMAND);
        initResponse(writer);

        new QueryServlet(DATABASE_URL).doGet(request, response);
        writer.flush();

        final Product expected = new Product("Pixel6", 500);
        assertMaxQuery(stringWriter.toString().trim(), toHtml(expected));
    }

    @Test
    public void checkMinQuery() throws SQLException, IOException {
        fill();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initQueryRequest(MIN_COMMAND);
        initResponse(writer);

        new QueryServlet(DATABASE_URL).doGet(request, response);
        writer.flush();

        final Product expected = new Product("Xiaomi", 100);
        assertMinQuery(stringWriter.toString().trim(), toHtml(expected));
    }

    @Test
    public void checkSumQuery() throws SQLException, IOException {
        fill();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initQueryRequest(SUM_COMMAND);
        initResponse(writer);

        new QueryServlet(DATABASE_URL).doGet(request, response);
        writer.flush();

        final int expected = 900;
        assertSumQuery(stringWriter.toString().trim(), toPrice(expected));
    }

    @Test
    public void checkCountQuery() throws SQLException, IOException {
        fill();
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);

        initQueryRequest(COUNT_COMMAND);
        initResponse(writer);

        new QueryServlet(DATABASE_URL).doGet(request, response);
        writer.flush();

        final int expected = 3;
        assertCountQuery(stringWriter.toString().trim(), toPrice(expected));
    }

    private String toPrice(final int price) {
        return String.valueOf(price);
    }

    private String toHtml(final Product product) {
        return product.getName() + "\t" + product.getPrice() + "</br>" + SEPARATOR;
    }

    private void assertGetQuery(final String result, final String expected) {
        assertEquals(
                result,
                BODY_TAG + expected + COLLAPSE_BODY_TAG
        );
    }

    private void assertSumQuery(final String result, final String expected) {
        assertEquals(
                result,
                BODY_TAG + SUM_PRICE_HEADER + expected + COLLAPSE_BODY_TAG
        );
    }

    private void assertCountQuery(final String result, final String expected) {
        assertEquals(
                result,
                BODY_TAG + COUNT_PRICE_HEADER + expected + COLLAPSE_BODY_TAG
        );
    }

    private void assertMinQuery(final String result, final String expected) {
        assertEquals(
                result,
                BODY_TAG + MIN_PRICE_HEADER + expected + COLLAPSE_BODY_TAG
        );
    }

    private void assertMaxQuery(final String result, final String expected) {
        assertEquals(
                result,
                BODY_TAG + MAX_PRICE_HEADER + expected + COLLAPSE_BODY_TAG
        );
    }

    private void initAddRequest() {
        when(request.getParameter(NAME_PARAMETER)).thenReturn("Xiaomi");
        when(request.getParameter(PRICE_PARAMETER)).thenReturn("200");
    }

    private void initQueryRequest(final String command) {
        when(request.getParameter(COMMAND_PARAMETER)).thenReturn(command);
    }

    private void initResponse(final PrintWriter writer) throws IOException {
        when(response.getWriter()).thenReturn(writer);
    }

    private void fill() throws SQLException {
        productDAO.add(new Product("Pixel6", 500));
        productDAO.add(new Product("Xiaomi", 100));
        productDAO.add(new Product("Honor", 300));
    }
}
