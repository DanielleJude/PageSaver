package kled.pagesaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by kelley
 */
public class GoalsFragment extends Fragment implements View.OnClickListener {

    public ArrayList<GoalEntry> entries;
    private GoalsAdapter goalsAdapter;

    public GoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment GoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalsFragment newInstance() {
        GoalsFragment fragment = new GoalsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        Button addButton = (Button)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        entries = new ArrayList<>();

        // Dummy entries
        entries.add(new GoalEntry("To Kill a Mockingbird", "Read everything"));
        entries.add(new GoalEntry("Yes", "Read nothing"));

        // Set up list view
        ListView entryView = (ListView)view.findViewById(R.id.goals_list);
        goalsAdapter = new GoalsAdapter(getActivity(), entries);
        entryView.setAdapter(goalsAdapter);

        entryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), ViewGoalActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Add goal
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                Intent intent = new Intent(getActivity(), AddGoalActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
