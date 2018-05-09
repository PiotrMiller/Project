package http;

import classes.ProductType;
import dao.ProductTypesDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "ProductTypesServlet",
        urlPatterns = {"/productTypes"}
)
public class ProductTypesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {

        ProductTypesDao ptd = new ProductTypesDao();
        List<ProductType> productTypes = ptd.getAll();
        httpRequest.setAttribute("productTypes", productTypes);

        String nextJSP = "/jsp/product-types.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(httpRequest, httpResponse);

    }

}
