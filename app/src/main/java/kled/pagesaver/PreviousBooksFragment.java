package kled.pagesaver;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Danielle on 2/24/17.
 */

public class PreviousBooksFragment extends Fragment, implements AdapterView.OnItemClickListener {
    View view;
    ListView listView;
    DBEntryAdapter adapter;
    ReadInEntriesAsyncTask task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Set up the adapter and the listView, but don't add database items.
        //They will be added in onResume.
        adapter = new DBEntryAdapter(getActivity());
        view = inflater.inflate(R.layout.book_list_view, container, false);
        listView = (ListView)view.findViewById(R.id.book_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Reset the adapter to reflect any changes made to the database;
        adapter.clearAdapter();

        task = new ReadInEntriesAsyncTask();
        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
