package com.example.mohitkumar.identitiyscan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton f1,f2,f3;
    Animation fabopen, fabclose;
    boolean isopen = false;
    NavigationView navigationView;
    TextView textname,textroll;
    ImageView imageView;
    String url = "http://192.168.0.2:8000/Main_Details/";
    String barcode = null;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        f1 = (FloatingActionButton) findViewById(R.id.scan);
        f2 = (FloatingActionButton) findViewById(R.id.cam);
        f3 = (FloatingActionButton) findViewById(R.id.text1);
        fabopen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopen) {
                    f2.startAnimation(fabclose);
                    f3.startAnimation(fabclose);
                    f2.setClickable(false);
                    f3.setClickable(false);
                    isopen = false;
                } else {
                    f2.startAnimation(fabopen);
                    f3.startAnimation(fabopen);
                    f2.setClickable(true);
                    f3.setClickable(true);
                    isopen = true;
                }
            }
        });
        final Activity activity = this;
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent intent = new Intent(getApplicationContext(),PopUpActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hview = navigationView.getHeaderView(0);
        textname = (TextView)hview.findViewById(R.id.name1);
        textroll = (TextView) hview.findViewById(R.id.textView);
        imageView = (ImageView)hview.findViewById(R.id.imageView);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
            String s = b.getString("texted");
            final String url1 = url + s + ".json";
            Toast.makeText(getApplicationContext(),"Connecting....",Toast.LENGTH_SHORT).show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, (String) null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("TAG",url1);
                                id = response.getString("id");
                                barcode = response.getString("Barcode");
                                textname.setText(response.getString("Name"));
                                textroll.setText(response.getString("Roll_No"));

                                if(response.getString("Barcode").equals("221720"))
                                {
                                    imageView.setImageResource(R.drawable.mohit);
                                }
                                else if(response.getString("Barcode").equals("1115838"))
                                {
                                    imageView.setImageResource(R.drawable.vasu1);
                                }

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
            MySingleton.getInstance(MainActivity.this).addtorequestqueue(jsonObjectRequest);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
            else {
                url = url + result.getContents() + ".json";
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(),"Connecting....",Toast.LENGTH_SHORT).show();
                                Log.d("TAG",url);
                                id = response.getString("id");
                                barcode = response.getString("Barcode");
                                textname.setText(response.getString("Name"));
                                textroll.setText(response.getString("Roll_No"));

                                if(response.getString("Barcode").equals("221720"))
                                {
                                    imageView.setImageResource(R.drawable.mohit);
                                }
                                else if(response.getString("Barcode").equals("1115838"))
                                {
                                    imageView.setImageResource(R.drawable.vasu1);
                                }

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
            MySingleton.getInstance(MainActivity.this).addtorequestqueue(jsonObjectRequest);

        }
        else
            super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if(!(barcode == null)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_academic) {
            if(!(barcode == null)) {
                Intent intent = new Intent(this, AcademicDetails.class);
                intent.putExtra("Academic Details",barcode);
                startActivity(intent);
            }
        } else if (id == R.id.nav_library) {
            if(!(barcode == null)) {
                Intent intent = new Intent(this, LibraryDetails.class);
                intent.putExtra("Library Details",barcode);
                startActivity(intent);
            }
        } else if (id == R.id.nav_personal) {
            if(!(barcode == null)) {
                Intent intent = new Intent(this, PersonalDetails.class);
                intent.putExtra("Personal Details",barcode);
                startActivity(intent);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Myatask extends AsyncTask<Void,Void,Void>
    {

        private String Content;
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}
