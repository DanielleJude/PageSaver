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
        String id = request.getParameter("id");
        String phone = request.getParameter("phone_id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String rating = request.getParameter("rating");
        String comment = request.getParameter("comment");
        String status = request.getParameter("status");
        String quote = request.getParameter("quote");

        Entry entry = new Entry();
        //entry.setId(""+-1);
        entry.setId(id);
        entry.setPhoneId(phone);
        entry.setAuthor(author) ;
        entry.setTitle(title);
        entry.setGenre(genre);
        entry.setRating(rating);
        entry.setComment(comment);
        entry.setStatus(status);
        //entry.setQuote(quote);

        EntryDatastore entryDatastore = new EntryDatastore();
        entryDatastore.addEntry2Datastore(entry);

        getServletContext().getRequestDispatcher("/query.do").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }
}
