package http;

import classes.Product;
import dao.ProductsDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "BrandsServlet",
        urlPatterns = {"/brands"}
)

public class BrandsServlet extends HttpServlet{

    @Override
    protected void doGet (HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {

        ProductsDao productsDao = new ProductsDao();
        List<Product> productsBrand = productsDao.getAll();
        httpRequest.setAttribute("productBrand", productsBrand);

        String nextJSP = "/brands.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(httpRequest, httpResponse);
    }
}
