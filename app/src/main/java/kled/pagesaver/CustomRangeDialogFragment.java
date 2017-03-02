package kled.pagesaver;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Yu Liu on 3/1/2017.
 */

public class CustomRangeDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customRangeView = inflater.inflate(R.layout.dialog_custom_range, null);

        final TextView customRangeTv = (EditText) customRangeView.findViewById(R.id.custom_range);

        // Allow the user to input a custom page range
        builder.setView(customRangeView)
                // Save custom range
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Save user input as page increment
                        // Integer.parseInt(customRangeTv.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CustomRangeDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
