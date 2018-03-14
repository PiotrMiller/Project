package http;

import classes.ProductType;

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

        List<ProductType> productTypes = new ArrayList<>();
        productTypes.add(new ProductType("Typ Pierwszy"));
        productTypes.add(new ProductType("Typ Drugi"));
        productTypes.add(new ProductType("Typ Trzeci"));
        httpRequest.setAttribute("productTypes", productTypes);

        String nextJSP = "/jsp/product-types.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(httpRequest, httpResponse);

    }

}
