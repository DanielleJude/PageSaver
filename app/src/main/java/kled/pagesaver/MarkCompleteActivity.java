package kled.pagesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MarkCompleteActivity extends AppCompatActivity {
    public final static String ID_BUNDLE_KEY = "_idbundle key";
    private long mEntryId;
    private BookEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_complete);

        Bundle bundle = getIntent().getExtras();
        mEntryId = bundle.getLong(ID_BUNDLE_KEY);

        entry = new BookEntryDbHelper(this).fetchEntryByIndex(mEntryId);
    }

    //Region button call backs

    public void onAddLocationClick(View view) {
        //TODO SEND TO MAP VIEW
    }

    public void onSaveButtonClicked(View view) {
        //TODO Save changes
    }

    public void onCancelButtonClicked(View view) {
        finish();
    }
}
