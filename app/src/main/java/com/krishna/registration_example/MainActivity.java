package com.krishna.registration_example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button submit;
    EditText fname,lname,email,mobile,address,password;
    private static final String url = "http://10.0.2.2/android_php/register_user.php";
//    private static final String url = "https://glossological-skies.000webhostapp.com/Registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname=findViewById(R.id.First);
        lname=findViewById(R.id.Last);
        address=findViewById(R.id.Address);
        email=findViewById(R.id.Email);
        mobile=findViewById(R.id.Mobile);
        password=findViewById(R.id.Password);



        submit=findViewById(R.id.Register);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegistration();

            }
        });

    }

    public void userRegistration()
    {


        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    JSONObject jsonObject=new JSONObject(response);

                    int sucess=jsonObject.getInt("sucess");

                    if(sucess==1)
                    {
                        fname.setText("");
                        lname.setText("");
                        address.setText("");
                        email.setText("");
                        mobile.setText("");
                        password.setText("");

                        Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(MainActivity.this,Login.class);
//                        startActivity(intent);
                    }
                    else if(sucess==2)
                    {
                        Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else if(sucess==3)
                    {
                        Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();

                    Toast.makeText(MainActivity.this,"Exception....",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param=new HashMap<String,String>();

                param.put("firstname",fname.getText().toString().trim());
                param.put("lastname",lname.getText().toString().trim());
                param.put("address",address.getText().toString().trim());
                param.put("email",email.getText().toString().trim());
                param.put("mobile",mobile.getText().toString().trim());
                param.put("password",password.getText().toString().trim());
                return param;

            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}