package com.app.itsforher;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Results extends AppCompatActivity {


    URLS urls;
    ListView listView;
    String VOTES = "";
    Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        prefs = new Prefs(this);
        urls = new URLS();

        // Get ListView object from xml
        listView = findViewById(R.id.list);


        listView.setVisibility(View.GONE);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });

        geta();
    }


    public void geta() {
        class GetVotes extends AsyncTask<Void, Void, String> {


            private ProgressDialog dialog;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user", prefs.getUser());

                //returing the response
                return requestHandler.sendPostRequest(urls.URL_get, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                dialog = new ProgressDialog(Results.this);
                dialog.setMessage("connecting");
                dialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                Log.e("Response", " Response" + s);
                if (s.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Could not connect, Check IP or your wifi connection", Toast.LENGTH_SHORT).show();
                }
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the votes from the response
                        JSONArray userJson = obj.getJSONArray("votes");
                        //[{"id":3,"username":"Brian","vote":"Coke","location":"Nairobi"}]
                        //{"error":false,"message":"Here are the answers","votes":[{"id":1,"user":"Brian","q1":"a","q2":"b","q3":"c","q4":"d","q5":"e","q6":"f","q7":"g","q8":"h","q9":"i","q10":"j"},{"id":2,"user":"ken","q1":"a","q2":"b","q3":"c","q4":"d","q5":"e","q6":"f","q7":"g","q8":"h","q9":"i","q10":"j"}]}
                        for (int i = 0; i < userJson.length(); i++) {
                            JSONObject actor = userJson.getJSONObject(i);
                            VOTES = VOTES + ":" + actor.getString("user")
                                    + "\nq1 =" + actor.getString("q1")
                                    + "\nq2 =" + actor.getString("q2")
                                    + "\nq3 =" + actor.getString("q3")
                                    + "\nq4 =" + actor.getString("q4")
                                    + "\nq5 =" + actor.getString("q5")
                                    + "\nq6 =" + actor.getString("q6")
                                    + "\nq7 =" + actor.getString("q7")
                                    + "\nq8 =" + actor.getString("q8")
                                    + "\nq9 =" + actor.getString("q9")
                                    + "\nq10=" + actor.getString("q10");
                        }


                        String[] values = VOTES.split(":");

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Results.this,
                                R.layout.items, R.id.text1, values);


                        // Assign adapter to ListView
                        listView.setAdapter(adapter);
                        listView.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        GetVotes ru = new GetVotes();
        ru.execute();

    }
}
