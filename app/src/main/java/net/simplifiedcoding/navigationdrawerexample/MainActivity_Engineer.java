package net.simplifiedcoding.navigationdrawerexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_Engineer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    SessionManager sm;
    TextView tv_usernames,tv_emails;
    RelativeLayout layout_p;
    ImageView iv_user_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_engineer);
        sm = new SessionManager(MainActivity_Engineer.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView ;
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        layout_p=(RelativeLayout) headerView.findViewById(R.id.layout_p);
        tv_usernames=(TextView)headerView.findViewById(R.id.tv_usernames);
        tv_emails=(TextView)headerView.findViewById(R.id.tv_emails);
        iv_user_pic=(ImageView) headerView.findViewById(R.id.iv_user_pic);
        tv_emails.setText(sm.getmobileno());
        tv_usernames.setText(sm.getname());
        layout_p.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),Activity_profile.class);
                startActivity(i);
            }
        });
        Picasso.with(getApplicationContext()).load("http://sighteat.com//imitra/"+sm.getTokenID()).placeholder(getResources().getDrawable(R.drawable.no_img)).error(getResources().getDrawable(R.drawable.no_img)).into(iv_user_pic);
       // Picasso.with(getApplicationContext()).load(sm.getTokenID()).into(iv_user_pic);
       // iv_user_pic.
        userlist();
        promotertype();
        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_menu1);
    }

    @Override
    public void onBackPressed()
    {
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
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();
        if(id == R.id.action_settings)
        {
            Intent i=new Intent(getApplicationContext(),Activity_profile.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_settings1)
        {

            android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setMessage("Are you sure you want to Logout App?");
            dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();



                }
            });
            dialog.setNegativeButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    sm.logoutUser();
                    Intent intent = new Intent(MainActivity.this, Activity_Login.class);
                    startActivity(intent);
                    finish();
                }
            });
            dialog.show();
            return true;

        }*/
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
        //noinspection SimplifiableIfStatement

    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new Menu1();
                break;
            case R.id.nav_menu21:
                Intent i=new Intent(getApplicationContext(),Activity_Authorized.class);
                startActivity(i);
                //fragment = new Menu2();
                break;
            case R.id.nav_menu22:
                Intent i2=new Intent(getApplicationContext(),Activity_ProjectHead.class);
                startActivity(i2);
                break;
            case R.id.nav_menu23:
                Intent i3=new Intent(getApplicationContext(),Activity_Agent.class);
                startActivity(i3);
                break;
            case R.id.nav_menu24:
                Intent i4=new Intent(getApplicationContext(),Activity_Contractor.class);
                startActivity(i4);
                break;
            case R.id.nav_menu3:
                Intent i5=new Intent(getApplicationContext(),Activity_Current_Project.class);
                startActivity(i5);
                break;
            case R.id.nav_menu4:
                fragment = new Menu4();
                break;
            case R.id.nav_menu5:
                android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(MainActivity_Engineer.this);
                dialog.setCancelable(false);
                dialog.setMessage("Are you sure you want to Logout App?");
                dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();



                    }
                });
                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        sm.logoutUser();
                        Intent intent = new Intent(MainActivity_Engineer.this, Activity_Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
    private void userlist()
    {

        // progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getUserList/{\"RoleID\":\"12\",\"IsActive\":\"Y\",\"mode\":\"list\",\"Type\":\"promoter_group\"}",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        // progressBar_main.setVisibility(View.GONE);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                // JSONObject object = jsonObject.getJSONObject("StateData");
                               // Toast.makeText(getApplicationContext(),jsonObject+"",Toast.LENGTH_LONG).show();
                                Constants.userlist=jsonObject+"";
                                //object.getString("CurrentProjects");



                            }
                            else
                            {

                            }
                        }
                        catch (Exception e)
                        {

                        }

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // progressBar_main.setVisibility(View.GONE);
                        //mess(error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Host", "sighteat.com");
                headers.put("Language", "" + "en");
                headers.put("Ipaddress", "" + "185.255.554.20");
                headers.put("Authorization", "" + "NWNiNTZiZGZmMWZiZTU1MGE1ODUxY2RlODE1MzIxNzRiZjIyMTVkMmI2Mzli");
                headers.put("Devicetype", "" + "Web");
                headers.put("Timestamp", "" + "156724011232");
                headers.put("Deviceid", "" + "Android");
                headers.put("Clientcode", "" + "5cb56bfb61a6f");
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());
                //   headers.put("Relationid", "" + sm.getRelationID());

                Log.e("sssss",headers+"");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void promotertype()
    {

        // progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getPromoterType/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        // progressBar_main.setVisibility(View.GONE);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                // JSONObject object = jsonObject.getJSONObject("StateData");
                                //Toast.makeText(getApplicationContext(),jsonObject+"",Toast.LENGTH_LONG).show();
                                Constants.promotertype=jsonObject+"";
                                //object.getString("CurrentProjects");



                            }
                            else
                            {

                            }
                        }
                        catch (Exception e)
                        {

                        }

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // progressBar_main.setVisibility(View.GONE);
                        //mess(error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Host", "sighteat.com");
                headers.put("Language", "" + "en");
                headers.put("Ipaddress", "" + "185.255.554.20");
                headers.put("Authorization", "" + "NWNiNTZiZGZmMWZiZTU1MGE1ODUxY2RlODE1MzIxNzRiZjIyMTVkMmI2Mzli");
                headers.put("Devicetype", "" + "Web");
                headers.put("Timestamp", "" + "156724011232");
                headers.put("Deviceid", "" + "Android");
                headers.put("Clientcode", "" + "5cb56bfb61a6f");
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());
                //   headers.put("Relationid", "" + sm.getRelationID());

                Log.e("sssss",headers+"");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
