package com.example.eloisedietz.myapplication.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
This class deletes an entry from the datastore
 */

public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntryDatastore datastore = new EntryDatastore();
        String id = request.getParameter("id");
        datastore.delete(id);
        MessagingEndpoint msg = new MessagingEndpoint();
        msg.sendMessage(id);
        response.sendRedirect("/query.do");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

}

