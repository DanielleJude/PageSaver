package com.example.eloisedietz.myapplication.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.eloisedietz.myapplication.backend.OfyService.ofy;

/*
This class deletes an entry from the datastore
 */

public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntryDatastore datastore = new EntryDatastore();
        String id = request.getParameter(Entry.ID);
        String phoneid = request.getParameter(Entry.PHONE_ID);
        boolean wasDeleted = datastore.delete(id, phoneid);

        String regId = request.getParameter(Entry.REG_ID);
        RegistrationRecord regRecord = ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();

        MessagingEndpoint messagingEndpoint = new MessagingEndpoint();
        if(wasDeleted)
             messagingEndpoint.sendMessage("Deleted rowID " + id + " phone id " + phoneid, regRecord);
        else
            messagingEndpoint.sendMessage("Failed to delete", regRecord);

        response.sendRedirect("/query.do");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

}

