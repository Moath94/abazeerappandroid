package com.abazeer.abazeerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.LoginResponse;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.login_btn)
    Button login;

    @BindView(R.id.login_email)
    EditText email;
    @BindView(R.id.login_pass)
    EditText pass;

    DatabaseHandler db;

    String emailS;
    String passS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        db = new DatabaseHandler(this);

        if (db.getContactsCount() > 0) {

            Intent intent = new Intent(MainActivity.this, SideMenu.class);

            startActivity(intent);

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailS = email.getText().toString().trim();
                passS = pass.getText().toString().trim();

                if (emailS.isEmpty()) {
                    email.requestFocus();
                    email.setError("اكتب البريد الالكتروني بصياغة التالية exampl@gmail.com");
                }
                if (passS.isEmpty()) {
                    pass.requestFocus();
                    pass.setError("اكتب كلمة المرور");
                }


                if (!emailS.isEmpty() && !passS.isEmpty()) {
                    progress.show();

                    new RetrofitCon(MainActivity.this).getService().login(emailS, passS,2).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().isSuccess()) {

                                    response.body().getUser().setAccessToken(response.body().getAccessToken());
                                    response.body().getUser().setIsdriver(response.body().getIsdriver());
                                    Log.e("Users", response.body().getUser().toString() + "");
                                    db.addUser(response.body().getUser());
                                    Intent intent = new Intent(MainActivity.this, SideMenu.class);

                                    startActivity(intent);
                                } else {

                                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                    Log.e("ApiError",jObjError.getString("message"));
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                            progress.dismiss();

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("Error", t.getLocalizedMessage());
                            progress.dismiss();
                        }
                    });
                }

            }
        });

    }
}