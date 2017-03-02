package com.example.eloisedietz.myapplication.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by eloisedietz on 2/24/17.
 */

public class AddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Entry entry = Entry.requestToEntry(request);

        EntryDatastore entryDatastore = new EntryDatastore();
        entryDatastore.addEntry2Datastore(entry);

        getServletContext().getRequestDispatcher("/query.do").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }
}
