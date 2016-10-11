package com.example.jack.ayd2;
import android.app.ListActivity;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.app.Activity;
import android.widget.Toast;

public class UsersActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);


        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra(LoginActivity.USUARIO);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, message);
        setListAdapter(adapter);

    }
    final static String   ID_USUARIO = "ID_USUARIO";
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        Intent intent = new Intent(this, UserDetails.class);
        intent.putExtra(UsersActivity.ID_USUARIO, item);
        startActivity(intent);
    }


}
