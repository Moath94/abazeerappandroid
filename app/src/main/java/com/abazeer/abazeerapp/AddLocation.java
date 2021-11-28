package com.abazeer.abazeerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.LocationModel;
import com.abazeer.abazeerapp.model.UserModel;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLocation extends AppCompatActivity {

    @BindView(R.id.addlocation_zonename)
    TextView zonename;
    @BindView(R.id.addlocation_name)
    EditText name;
    @BindView(R.id.addlocation_save)
    Button save;
    int id;
    String storagename;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        db = new DatabaseHandler(this);
        user = db.getUser();
        id = getIntent().getIntExtra("id", 0);
        storagename = getIntent().getStringExtra("name");

        zonename.setText(storagename);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("الرجاء ادخال الاسم");
                    name.requestFocus();
                } else {
                    progress.show();

                    new RetrofitCon(AddLocation.this).getService().addlocation("Bearer " + user.getAccessToken(), name.getText().toString().trim(), id).enqueue(new Callback<DataResponse<LocationModel>>() {
                        @Override
                        public void onResponse(Call<DataResponse<LocationModel>> call, Response<DataResponse<LocationModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().isSuccess()) {
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                                    Log.e("Response", response.body().getData().getName() + "dfg");
                                    Intent intent = new Intent(AddLocation.this, ProductPage.class);
                                    intent.putExtra("id", response.body().getData().getId());
                                    intent.putExtra("location_name", response.body().getData().getName());
                                    intent.putExtra("zone_name", storagename);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {

                                {
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        Toast.makeText(AddLocation.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(AddLocation.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                            progress.dismiss();

                        }

                        @Override
                        public void onFailure(Call<DataResponse<LocationModel>> call, Throwable t) {
                            if (t.getLocalizedMessage() != null) {
                                Log.e("ErrorFailure", t.getLocalizedMessage());
                                progress.dismiss();

                            }
                        }
                    });
                }
            }
        });
    }
}