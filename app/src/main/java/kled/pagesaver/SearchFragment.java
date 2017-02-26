package kled.pagesaver;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

/**
 * Created by Danielle on 2/24/17.
 */

public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener{
    private View view;
    private ListView listView;
    private SearchView searchView;
    private TextView searchErrorView;
    private DBEntryAdapter adapter;
    private ReadInEntriesAsyncTask task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Set up the adapter and the listView, but don't add database items.
        //They will be added in onResume.
        adapter = new DBEntryAdapter(getActivity());

        view = inflater.inflate(R.layout.search_frag, container, false);

        searchView = (SearchView)view.findViewById(R.id.search_bar);
        searchErrorView = (TextView)view.findViewById(R.id.search_error_textbox);

        listView = (ListView)view.findViewById(R.id.search_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Reset the adapter to reflect any changes made to the database;
        adapter.clearAdapter();

        task = new ReadInEntriesAsyncTask(ReadInEntriesAsyncTask.ALL_MODE, adapter);
        task.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
