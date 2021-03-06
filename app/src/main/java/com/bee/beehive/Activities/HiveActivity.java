package com.bee.beehive.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bee.beehive.DatabaseHandler;
import com.bee.beehive.Fragments.AddHiveOverlay;
import com.bee.beehive.Hive;
import com.bee.beehive.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class HiveActivity extends ListItem {

    public DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive);

        //setTitle(getIntent().getStringExtra("apiary_name"));
		setTitle(mSharedPreferences.getString("apiary_name", ""));

        startAdMob();

    }

    public String getKeyName()
    {
        return db.getKeyHiveName();
    }

	@Override
	public String getSubText(int id) {
		String[] action = db.getNextAction(id);
        if (action[1] == null) {
            return getString(R.string.no_action_scheduled);
        } else {
            return action[1] + " " + getString(R.string.scheduled_on) + " " + action[0];
        }
	}

	public String getKeyId()
	{
		return db.getKeyHiveId();
	}

    public Cursor getCursor(int i, int a)
    {
		mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		return db.getHivesCursor(mSharedPreferences.getInt("apiary_id", 0));
       // return db.getHivesCursor(getIntent().getIntExtra("apiary_id", -1));
    }

    public Class getGoToClass()
    {
        return ActionActivity.class;
    }

	public void deleteButton(View view)
	{
        final View view_arg = view;

        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_hive_overlay_title)
				.setMessage(getText(R.string.delete_overlay_message) + " " + "\""+view_arg.getTag(R.string.name).toString()+"\"?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
                        db.deleteHive(Integer.valueOf(view_arg.getTag(R.string.id).toString()), mSharedPreferences.getInt("apiary_id", 0));
                        cursorAdapter.changeCursor(getCursor(1, 1));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();


	//	mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
	//	db.deleteHive(Integer.valueOf(view.getTag().toString()), mSharedPreferences.getInt("apiary_id", 0));
	//	cursorAdapter.changeCursor(getCursor(1, 1));
	}

    public void add(String hive_name, String honeycomb_count, String breedingcomb_count)
    {
		mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

		db.addHive(new Hive(hive_name, honeycomb_count, breedingcomb_count, mSharedPreferences.getInt("apiary_id", 0)));
        cursorAdapter.changeCursor(getCursor(clicked_id, 1));
    }

    public void changeHiveProperties(int id, String name, String honeycomb_count, String breedingcomb_count)
    {
        db.changeHiveProperties(id, name, honeycomb_count, breedingcomb_count);
        cursorAdapter.changeCursor(getCursor(1, 1));
    }

    public void popDialog()
    {
		AddHiveOverlay addOverlay = new AddHiveOverlay();
		addOverlay.show(getFragmentManager(), "AddOverlay");
    }

    public void popDialog(int id)
    {
        AddHiveOverlay addOverlay = new AddHiveOverlay();
		addOverlay.onAttach(this);
        addOverlay.show(getFragmentManager(), "AddOverlay");
		addOverlay.setHiveData(id);
    }

}
