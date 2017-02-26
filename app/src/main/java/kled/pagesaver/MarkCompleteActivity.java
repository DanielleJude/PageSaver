package kled.pagesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MarkCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_complete);
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
