package com.ita.u1.internetLibrary.web;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

import com.ita.u1.internetLibrary.model.Reader;

import com.ita.u1.internetLibrary.dao.ReaderDAO;
import org.apache.commons.io.IOUtils;
import org.postgresql.core.Utils;

@MultipartConfig
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();

        if(params.containsKey("btnGetListReaders")) {
            loadListOfReaders(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();

        if(params.containsKey("name")) {
            addNewReader(request, response);
        }

        if(params.containsKey("btnSubmitFile")) {
            /*Part filePart = request.getPart("file");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            byte[] bytes = fileContent.readAllBytes();
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));*/
        }
    }

    protected void addNewReader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Reader reader = new Reader(request.getParameter("surname"), request.getParameter("name"),
                request.getParameter("patronymic"), request.getParameter("passport-id"),
                request.getParameter("email"), request.getParameter("address"), request.getParameter("birthday"));
        try {
            ReaderDAO.updateTableOfReaders(reader);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("readerRegistration.jsp");
    }

    protected void loadListOfReaders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Reader> listOfReaders = new ArrayList<>();
        try {
            ReaderDAO.selectAllReaders(listOfReaders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("listOfReaders", listOfReaders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listOfReaders.jsp");
        dispatcher.forward(request, response);
    }
}
