package com.example.eloisedietz.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by eloisedietz on 2/24/17.
 */

public class EntryDatastore {
    private Logger mLogger;
    private DatastoreService mDatastore;

    public EntryDatastore(){
        mLogger = Logger.getLogger(Entry.class.getName());
        mDatastore = DatastoreServiceFactory
                .getDatastoreService();
    }

    private static Key getParentKey() {
        return KeyFactory.createKey(Entry.PARENT_KIND,
                Entry.PARENT_IDENTIFIER);
    }

    public Entry getEntryByIdentifier(String id){
        Entity entity = null;
        //Key key = KeyFactory.createKey(getParentKey(), Entry.ID, id);
        Key key = KeyFactory.createKey(getParentKey(), Entry.ENTITY_NAME, id);
        try {
            entity = mDatastore.get(key);
        }catch (Exception e){}
        if(entity != null)
            return convertEntity2Entry(entity);
        else
            return null;
    }

    private Entry convertEntity2Entry(Entity entity) {
        Entry entry = new Entry();

        entry.setId(entity.getProperty(Entry.ID).toString());
        entry.setTitle(entity.getProperty(Entry.TITLE).toString());
        entry.setAuthor(entity.getProperty(Entry.AUTHOR).toString());
        entry.setGenre(entity.getProperty(Entry.GENRE).toString());
        entry.setRating(entity.getProperty(Entry.RATING).toString());
        entry.setComment(entity.getProperty(Entry.COMMENT).toString());
        entry.setStatus(entity.getProperty(Entry.STATUS).toString());
//        entry.setQuote(entity.getProperty(Entry.QUOTE).toString());

        return entry;
    }

    /*
    Adds a given entry to the datastore
     */
    public boolean addEntry2Datastore(Entry entry){
        if(getEntryByIdentifier(entry.getId()) != null)
            return false;
        else{
            //This makes the datastore autogenerate ids
            //Entity entity = new Entity(Entry.ID);
            Entity entity = new Entity(Entry.ENTITY_NAME, entry.getId(), getParentKey());
            entity.setProperty(Entry.ID, entry.getId());
            entity.setProperty(Entry.TITLE, entry.getTitle());
            entity.setProperty(Entry.AUTHOR, entry.getAuthor());
            entity.setProperty(Entry.GENRE, entry.getGenre());
            entity.setProperty(Entry.RATING, entry.getRating());
            entity.setProperty(Entry.COMMENT, entry.getComment());
            entity.setProperty(Entry.STATUS, entry.getStatus());
            //entity.setProperty(Entry.QUOTE, entry.getQuote());

            mDatastore.put(entity);
            return true;
        }
    }


    public boolean delete(String id) {
        Query.Filter filter = new Query.FilterPredicate(Entry.ID, Query.FilterOperator.EQUAL, id);
        //Query query = new Query(Entry.ID);
        Query query = new Query(Entry.ENTITY_NAME);
        query.setFilter(filter);
        query.setAncestor(getParentKey());
        PreparedQuery preparedQuery = mDatastore.prepare(query);
        Entity entity = preparedQuery.asSingleEntity();
        if (entity == null)
            return false;
        else {
            mDatastore.delete(entity.getKey());
            return true;
        }
    }

    /*
    This method queries and returns an arraylist of all current entries
     */
    public ArrayList<Entry> query() {
        ArrayList<Entry> result = new ArrayList<Entry>();
        //Query query = new Query(Entry.ID);
        Query query = new Query(Entry.ENTITY_NAME);
        query.setFilter(null);
        query.setAncestor(getParentKey());
        PreparedQuery pq = mDatastore.prepare(query);
        Iterator<Entity> entities = pq.asIterator();
        if(entities!=null) {
            while (entities.hasNext()) {
                Entity entity = entities.next();
                if (entity != null) {
                    result.add(convertEntity2Entry(entity));
                }
            }
        }

        return result;
    }


}
