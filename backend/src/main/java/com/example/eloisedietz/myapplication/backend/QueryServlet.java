package com.example.eloisedietz.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by eloisedietz on 2/24/17.
 */

public class QueryServlet extends HttpServlet {

    /**
     * This class prints out a query of the database
     */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        EntryDatastore datastore = new EntryDatastore();
        ArrayList<Entry> result = datastore.query();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<style>\n"+
                "table, th, td {\n"+
                "border: 1px solid black;\n"+
                "border-collapse: collapse;\n"+
                "}\n"+
                "</style>\n"+
                "<title>Exercise Entries</title>\n" +
                "</head>\n" +
                "<body>\n");
        String retStr = (String) req.getAttribute("_retStr");
        if (retStr != null) {
            out.write(retStr+"<br>");

        }
        out.write("Exercise Entries\n\n");
        out.write("<table border=\"1\" style=\"width:80%\">\n" +
                "<tr> <TH> ID </TH>" +
                "<TH> Title </TH>" +
                "<TH> Author </TH> " +
                "<TH> Genre </TH>" +
                "<TH> Rating </TH>" +
                "<TH> Comment </TH>" +
                "<TH> Status </TH>" +
                "<TH> Quote </TH> </tr>"
        );


        if (result != null) {
            for (Entry entry : result) {
                out.write("<TR> <TH> " + entry.getId() + " </TH>" +
                        "<TH> " + entry.getTitle() + "</TH> " +
                        "<TH>" + entry.getAuthor() +" </TH> " +
                        "<TH>" + entry.getGenre() + "</TH>" +
                        "<TH>" + entry.getRating() +" </TH>" +
                        "<TH> "+entry.getComment()+" </TH>" +
                        "<TH>" + entry.getStatus()+"</TH>" +
                        "<TH>" + entry.getQuote()+"</TH>" +
                        "<TH><input type=\"button\" onclick=\"location.href='/delete.do?id=" + entry.getId() + "'\" value=\"Delete\"> </TH> </tr>"
                );
            }
        }
        out.write("</table>\n");

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }
}
