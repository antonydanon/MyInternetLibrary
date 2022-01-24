package com.ita.u1.internetLibrary.web;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@MultipartConfig
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("btnReaderRegistration")){
            RequestExecutor.openReaderRegistration(request, response);
        }
        if(params.containsKey("btnGetListReaders") || params.containsKey("btnGetNextPageReaders") || params.containsKey("btnSortReaders")) {
            RequestExecutor.loadListOfReaders(request, response);
        }
        if(params.containsKey("btnGetMainPage") || params.containsKey("btnGetNextPage") || params.containsKey("btnSort")) {
            RequestExecutor.loadListOfBooks(request, response);
        }
        if(params.containsKey("requestOnOrder")) {
            RequestExecutor.registerOrdersOfReaders(request, response);
        }
        if(params.containsKey("btnGetListReadersWithoutDebts")){
            RequestExecutor.getListOfReadersWithoutDebts(request, response);
        }
        if(params.containsKey("btnGetListOfAvailableBooks")){
            RequestExecutor.getListOfAvailableBooks(request, response);
        }
        if(params.containsKey("btnGetWindowOfOrderRegistration")){
            RequestExecutor.openWindowOfOrderRegistration(request, response);
        }
        if(params.containsKey("btnGetWindowOfBookReturning")){
            RequestExecutor.openWindowOfBookReturning(request, response);
        }
        if(params.containsKey("btnGetReadersListWithDebts")){
            RequestExecutor.getListOfReadersWithDebts(request, response);
        }
        if(params.containsKey("btnGetBookRegistration")){
            RequestExecutor.openBookRegistration(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if(params.containsKey("name")) {
            RequestExecutor.addNewReader(request, response);
        }
        if(params.containsKey("makeRegistrationOfBook")) {
            RequestExecutor.makeRegistrationOfBook(request, response);
        }
        if(params.containsKey("returnBooks")){
            RequestExecutor.makeReturnOfBooks(request, response);
        }
    }
}
