package com.example.mohitkumar.identitiyscan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AcademicDetails extends AppCompatActivity {

    TextView textView1,textView2,textView3,textView4,math,physics,electrical,pf,ed,en,sem;
    String url = "http://192.168.0.2:8000/Academic_Details/";
    String url1 = "http://192.168.0.2:8000/Semester_1/";
    String url2 = "http://192.168.0.2:8000/Semester_2/";
    Button button1,button2;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_details);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("Academic Details");
        getSupportActionBar().setTitle("#"+s);
        url = url + s + ".json";
        url1 = url1 + s + ".json";
        url2 = url2 + s + ".json";
        textView1 = (TextView)findViewById(R.id.branch);
        textView2 = (TextView)findViewById(R.id.year);
        textView3 = (TextView)findViewById(R.id.sem);
        textView4 = (TextView)findViewById(R.id.average_cgpa);
        linearLayout = (LinearLayout)findViewById(R.id.semes);
        linearLayout.setVisibility(View.INVISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAG",url);
                            //id = response.getString("id");
                            textView3.setText("Semester      "+response.getString("Sem"));
                            textView1.setText(response.getString("Branch"));
                            textView2.setText("Year          "+response.getString("Year"));
                            textView4.setText("Average GPA   "+response.getString("Average_CGPA"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(AcademicDetails.this).addtorequestqueue(jsonObjectRequest);

        math = (TextView) findViewById(R.id.maths1);
        physics = (TextView) findViewById(R.id.physcis1);
        electrical = (TextView) findViewById(R.id.Electrical);
        pf = (TextView) findViewById(R.id.prog_fund);
        ed = (TextView) findViewById(R.id.ED);
        en = (TextView) findViewById(R.id.EN);
        button1 = (Button) findViewById(R.id.sem_1);
        button2 = (Button) findViewById(R.id.sem_2);
        sem = (TextView)findViewById(R.id.semester);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sem.setText("Semester 1");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("TAG",url1);
                                    //id = response.getString("id");
                                    math.setText(response.getString("Maths_1"));
                                    physics.setText(response.getString("Physics_1"));
                                    electrical.setText(response.getString("Basic_Electrical"));
                                    pf.setText(response.getString("Prog_Fundamentals"));
                                    ed.setText(response.getString("Engg_Drawing"));
                                    en.setText(response.getString("Environment"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error",error.getMessage());
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                MySingleton.getInstance(AcademicDetails.this).addtorequestqueue(jsonObjectRequest);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sem.setText("Semester 2");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2, (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(),"Connecting....",Toast.LENGTH_SHORT).show();
                                    Log.d("TAG",url2);
                                    //id = response.getString("id");
                                    math.setText(response.getString("Maths_2"));
                                    physics.setText(response.getString("Physics_2"));
                                    electrical.setText(response.getString("Chemistry"));
                                    pf.setText(response.getString("Basic_Mechanical"));
                                    ed.setText(response.getString("Workshop_Practice"));
                                    en.setText(response.getString("English"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error",error.getMessage());
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                MySingleton.getInstance(AcademicDetails.this).addtorequestqueue(jsonObjectRequest);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}