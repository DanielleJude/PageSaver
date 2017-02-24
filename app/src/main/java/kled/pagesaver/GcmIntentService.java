package kled.pagesaver;

import android.app.Activity;
import android.app.IntentService;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by eloisedietz on 2/19/17.
 * This intent service allows the backend to communicate with the app
 */

public class GcmIntentService extends IntentService {
    private BookEntryDbHelper dataSource;
    int id;
    public GcmIntentService() {


        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dataSource = new BookEntryDbHelper(this);
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                /*
                The database entry can be deleted using the id
                 */
                id = Integer.valueOf(extras.getString("message"));
                dataSource.removeEntry(id);
            }
        }
        GcmReceiver.completeWakefulIntent(intent);
    }


}


