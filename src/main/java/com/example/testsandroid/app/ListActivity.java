package com.example.testsandroid.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListActivity extends ActionBarActivity {

    private static final String LOG_TAG = ListActivity.class.getSimpleName();

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Liste");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        // setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final String content = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.list_item, parent, false);
                }
                final TextView titleView = (TextView) convertView.findViewById(R.id.title);
                titleView.setText(content);

                return  titleView;
            }

        };
        listView.setAdapter(adapter);

        /*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.highlighted_text_material_light));
                return true;
            }

        });
        */

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                // toolbar.setVisibility(View.GONE);

                Log.i(LOG_TAG, "Création de l'Action Mode");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                // toolbar.setVisibility(View.VISIBLE);
            }

        });

        adapter.addAll("A", "B", "C", "D");
    }

}
