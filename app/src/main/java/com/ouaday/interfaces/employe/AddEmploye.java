package com.ouaday.interfaces.employe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ouaday.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ouaday.entities.Service;

public class AddEmploye extends AppCompatActivity {

    RequestQueue requestQueue;
    private List<Service> services = new ArrayList<>();
    private Service selectedService;
    private EditText nom, prenom, dateNaissance;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe);

        getServices();

        nom = findViewById(R.id.idProfesseurNomAdd);
        prenom = findViewById(R.id.idProfesseurPrenomAdd);
        dateNaissance = findViewById(R.id.idProfesseurDateAdd);

        submit = findViewById(R.id.idAddEmploye);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEmploye();
            }
        });
    }

    private void getServices() {
        String getFUrl = "http://192.168.11.167:8080/api/service";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getFUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Log.d("Services",response.toString());
                TypeToken<List<Service>> token = new TypeToken<List<Service>>() {};
                services = gson.fromJson(response.toString(), token.getType());

                final HashMap<String, Service> serviceMap = new HashMap<>();
                for (Service service : services) {
                    serviceMap.put(service.getNom(), service);
                }


                List<String> nomServices = new ArrayList<>(serviceMap.keySet());


                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEmploye.this, android.R.layout.simple_spinner_item, nomServices);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = findViewById(R.id.spinner);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        String selectedFiliereNom = (String) parentView.getItemAtPosition(position);


                        selectedService = serviceMap.get(selectedFiliereNom);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }



    public void submitEmploye() {
        String insertUrl = "http://192.168.11.167:8080/api/employe";
        JSONObject jsonBody = new JSONObject();
        try {

            JSONArray rolesArray = new JSONArray();


            jsonBody.put("id", "");
            jsonBody.put("nom", nom.getText().toString());
            jsonBody.put("prenom", prenom.getText().toString());
            jsonBody.put("dateNaissance", dateNaissance.getText().toString());

            JSONObject filiereObject = new JSONObject();
            filiereObject.put("id", selectedService.getId());
            filiereObject.put("nom", selectedService.getNom());
            jsonBody.put("service", filiereObject);

            Log.d("Professeur", jsonBody.toString());

            Toast.makeText(AddEmploye.this, "Employe Added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddEmploye.this, EmployeList.class);
            startActivity(intent);
            AddEmploye.this.finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response+"");
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