package com.bee.beehive.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bee.beehive.Activities.ApiaryActivity;
import com.bee.beehive.Activities.ListItem;
import com.bee.beehive.R;

public class AddApiaryOverlay extends DialogFragment {

	String name;
	int id;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.activity_add_apiary_overlay, null);

		builder.setView(view);

		EditText editText = (EditText)view.findViewById(R.id.ApiaryNameInput);
		editText.setText(name);
		editText.setSelection(editText.getText().length());

		builder.setPositiveButton((name == null) ? getString(R.string.add) : getString(R.string.change), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText input = (EditText) view.findViewById(R.id.ApiaryNameInput);

				if (name == null) {
					((ListItem) getActivity()).add(input.getText().toString());
				} else {
					((ApiaryActivity) getActivity()).changeName(input.getText().toString(), id);
				}

			}
		}).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		Dialog dialog=builder.create();
		return dialog;
	}

	public void setName(String name, int id)
	{
		this.name = name;
		this.id = id;
	}

}
