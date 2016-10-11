package com.example.jack.ayd2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UserDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Intent intent = getIntent();
        String message = intent.getStringExtra(UsersActivity.ID_USUARIO).split("-")[0].trim();

        TextView textView_id = (TextView) findViewById(R.id.textView_id);
        textView_id.setText(message);

        new Consult().execute("http://ec2-52-34-76-37.us-west-2.compute.amazonaws.com/users/"+message);


    }



    private class Consult extends AsyncTask<String, Void, Void> {

        // Required initialization

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        String data = "";
        String email, password;
        int sizeData = 0;
        private boolean success = false;
        LoginActivity loginActivity;






        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);

                URLConnection conn = url.openConnection();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                Content = sb.toString();
            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }
            return null;
        }

        protected void onPostExecute(Void unused) {




                String OutputData = "";
                JSONObject jsonResponse;

                try {

                    jsonResponse = new JSONObject(Content);
                    JSONObject jsonChildNode = jsonResponse.getJSONObject("data");

                        //JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                       // String jemail = jsonChildNode.optString("email").toString();

                    ((TextView) findViewById(R.id.textView_email)).
                            setText(jsonChildNode.optString("email").toString());

                    String name = jsonChildNode.optString("first_name").toString();
                        ((TextView) findViewById(R.id.textView_name)).
                                setText(name);

                        ((TextView) findViewById(R.id.textView_last_name)).
                                setText(jsonChildNode.optString("last_name").toString());
                    ((TextView) findViewById(R.id.textView_created_at)).
                            setText(jsonChildNode.optString("created_at").toString());

                    ((TextView) findViewById(R.id.textView_updated_at)).
                            setText(jsonChildNode.optString("updated_at").toString());

                    ((TextView) findViewById(R.id.textView_deleted_at)).
                            setText(jsonChildNode.optString("deleted_at").toString());

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }



    }

}
