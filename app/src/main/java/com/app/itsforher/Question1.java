package com.app.itsforher;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Question1 extends AppCompatActivity {

    Button next, prev;
    int step = 1;
    TextView question_tv, count;
    RadioGroup radioGroup;
    String selectedAnswer = "";
    ArrayList<QuestionAnswers> questions_and_asnswers;
    URLS urls;
    Prefs prefs;
    int[] questions =
            {
                    R.string.question1, R.string.question2, R.string.question3,
                    R.string.question4, R.string.question5, R.string.question6,
                    R.string.question7, R.string.question8, R.string.question9,
                    R.string.question10

            };

    int[] questionsCount =
            {
                    R.string.questioncount, R.string.questioncount2, R.string.questioncount3,
                    R.string.questioncount4, R.string.questioncount5, R.string.questioncount6,
                    R.string.questioncount7, R.string.questioncount8, R.string.questioncount9,
                    R.string.questioncount10

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        prefs = new Prefs(this);
        urls = new URLS();

        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        prev.setVisibility(View.GONE);
        question_tv = findViewById(R.id.question1);
        count = findViewById(R.id.question_count);
        radioGroup = findViewById(R.id.radio_group);

        questions_and_asnswers = new ArrayList<>();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                Toast.makeText(getBaseContext(), "You have selected " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                selectedAnswer = radioButton.getText() + "";
            }
        });


        SwitchStep(1);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step != 1) {
                    step--;
                    SwitchStep(step);
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prev.setVisibility(View.VISIBLE);

                if (step == 10) {
                    QuestionAnswers qq = new QuestionAnswers();
                    qq.setAnswer(selectedAnswer);
                    qq.setQuestion(question_tv.getText().toString());
                    questions_and_asnswers.add(qq);
                    selectedAnswer = "";
                    submitResults();
                } else if (step != 10 && selectedAnswer.length() > 1) {
                    step++;
                    SwitchStep(step);
                    QuestionAnswers qq = new QuestionAnswers();
                    qq.setAnswer(selectedAnswer);
                    qq.setQuestion(question_tv.getText().toString());
                    questions_and_asnswers.add(qq);
                    selectedAnswer = "";
                } else {
                    Toast.makeText(getBaseContext(), "Please select one option", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void submitResults() {


        String message = "";
        for (int i = 0; i < questions_and_asnswers.size(); i++) {

            message = message + questions_and_asnswers.get(i).getQuestion() + "\n" + questions_and_asnswers.get(i).getAnswer() + "\n";
            Log.e("Results", questions_and_asnswers.get(i).getQuestion() + "\n" + questions_and_asnswers.get(i).getAnswer());
        }

        showAlertDialogButtonClicked(message);

    }


    public void showAlertDialogButtonClicked(String message) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("These are your answers");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                send();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void SwitchStep(int step) {

        question_tv.setText(questions[step - 1]);
        count.setText(questionsCount[step - 1]);
        String pass;

        switch (step) {
            case 1:
                pass = "Male\n" + "Female\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 2:
                pass = "16–20\n" +
                        "21–25\n" +
                        "26–30\n" +
                        "31–39\n" +
                        "40+</\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 3:
                pass = "School Leaver\n" +
                        "Apprentice\n" +
                        "Undergraduate\n" +
                        "Postgraduate\n" +
                        "Other\n";
                addRadioButtons(pass.split("\n"));

                break;
            case 4:
                pass = "Yes\n" +
                        "No\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 5:
                pass = "Yes\n" +
                        "No\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 6:
                pass = "Yes\n" +
                        "No\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 7:
                pass = "Yes\n" +
                        "No\n";
                ;
                addRadioButtons(pass.split("\n"));
                break;
            case 8:
                pass = "Yes\n" +
                        "No\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 9:
                pass = "Secondary School\n" +
                        "Sixth-form\n" +
                        "University\n" +
                        "First 5 years in industry\n" +
                        "Between 6 and 10 years in industry\n" +
                        "Permanent job in industry\n";
                addRadioButtons(pass.split("\n"));
                break;
            case 10:
                pass = "Male domination in these fields.\n" +
                        "Women are pushed towards non-practical subjects throughout life.\n" +
                        "The attitude of men towards women in these fields.\n" +
                        "STEM subjects are not taught thoroughly at school so no interest in shown later in life.\n" +
                        "Advertisement for technology (past and present) is mainly focused at men.\n" +
                        "Other\n";
                addRadioButtons(pass.split("\n"));
                break;
        }

    }

    public void addRadioButtons(String[] asnwers) {
        radioGroup.removeAllViews();
        int number = asnwers.length;
        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < number; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(asnwers[i]);
                radioGroup.addView(rdbtn);
            }
        }
    }

    public void sendData(){

        class CreateUser extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                StringRequest request = new StringRequest(Request.Method.POST, URLS.URL_send, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    Log.i("SSS: ",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("SSS",error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("user", prefs.getUser());
                        for (int i = 0; i < questions_and_asnswers.size(); i++) {
                            params.put("q" + (i + 1), questions_and_asnswers.get(i).getAnswer());
                        }
                        return params;
                    }
                };

                Volley.newRequestQueue(getApplicationContext()).add(request);
                return null;
            }

        }

        new CreateUser().execute();
    }


    public void send() {
        class RegisterUser extends AsyncTask<Void, Void, String> {


            private ProgressDialog dialog;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                for (int i = 0; i < questions_and_asnswers.size(); i++) {
                    params.put("user", prefs.getUser());
                    params.put("q" + (i + 1), questions_and_asnswers.get(i).getAnswer());
                }

                Log.e("Response", params.toString());

                //returing the response
                return requestHandler.sendPostRequest(urls.URL_send, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                dialog = new ProgressDialog(Question1.this);
                dialog.setMessage("Uploading answer");
                dialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                Log.i("Response", s);
                if (s.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Could not connect, Check IP or Your wifi connection", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Question1.this, EndPage.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Question1.this, EndPage.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();

    }
}
