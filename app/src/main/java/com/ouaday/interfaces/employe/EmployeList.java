package com.ouaday.interfaces.employe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ouaday.R;
import com.ouaday.adapters.EmployeAdapter;
import com.ouaday.entities.Employe;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class EmployeList extends AppCompatActivity {

    private List<Employe> employes = new ArrayList<>();
    private ListView EmployeList;
    RequestQueue requestQueue;
    EmployeAdapter employeAdapter ;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_list);

        employeAdapter = new EmployeAdapter(employes, this);
        getProfesseur();

        addButton = findViewById(R.id.idAddEmployeLV);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeList.this, AddEmploye.class);
                startActivity(intent);
                EmployeList.this.finish();
            }
        });
    }
    public void getProfesseur(){
        String getSUrl = "http://192.168.43.91:8080/api/employe";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getSUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
//                Log.d("Professeur",response.toString());
                TypeToken<List<Employe>> token = new TypeToken<List<Employe>>() {};
                employes = gson.fromJson(response.toString(), token.getType());
                EmployeList = findViewById(R.id.idEmployeLV);
                Log.d("Employes",employes.toString());
                employeAdapter.updateStudentsList(employes);
                EmployeList.setAdapter(employeAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }

}