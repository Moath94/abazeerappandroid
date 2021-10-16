package com.abazeer.abazeerapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.ItemModel;
import com.abazeer.abazeerapp.model.ProductModel;
import com.abazeer.abazeerapp.model.UserModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfoPage extends AppCompatActivity {

    @BindView(R.id.productinfo_name) TextView name;
    @BindView(R.id.productinfo_barcode) TextView barcode;
    @BindView(R.id.productinfo_expiry) TextView expiry;
    @BindView(R.id.productinfo_createby) TextView createdby;
    @BindView(R.id.productinfo_createdat) TextView createdat;
    @BindView(R.id.productinfo_updatedat) TextView updatedat;
    @BindView(R.id.productinfo_checkby) TextView updatedby;
    @BindView(R.id.productinfo_quantity) EditText quantity;
    @BindView(R.id.productinfo_quantitycheck) EditText quantityCheck;
    @BindView(R.id.productinfo_matching) TextView matching;
    @BindView(R.id.productinfo_note) EditText note;
    @BindView(R.id.productinfo_btn_delete) Button delete;
    @BindView(R.id.productinfo_btn_update) Button update;


    int id;
    DatabaseHandler db;
    UserModel user;
    ItemModel itemModel;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info_page);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        db = new DatabaseHandler(this);
        user= db.getUser();

        LinearLayoutManager llmanger = new LinearLayoutManager(this);
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);


        id = getIntent().getIntExtra("id",0);
        progress.show();

        new RetrofitCon(this).getService().product("Bearer "+user.getAccessToken(),id).enqueue(new Callback<DataResponse<ItemModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ItemModel>> call, Response<DataResponse<ItemModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getData().toString()+"dfg");

                        itemModel = response.body().getData();
                        name.setText(response.body().getData().getProduct_name());
                        barcode.setText(String.valueOf(response.body().getData().getProduct_barcode()));
                        createdat.setText(response.body().getData().getCreated_at());
                        createdby.setText(response.body().getData().getCreateby());
                        expiry.setText(response.body().getData().getExpirydate());
                        quantity.setText(String.valueOf(response.body().getData().getQuantity()));
                        quantityCheck.setText(String.valueOf(response.body().getData().getQuantitycheck()));
                        note.setText(response.body().getData().getDescription());

                        updatedat.setText(response.body().getData().getUpdated_at());
                        updatedby.setText(response.body().getData().getCheckby());

                        double q = response.body().getData().getQuantity() - response.body().getData().getQuantitycheck();
                        if (q == 0){

                            matching.setText(String.valueOf(q));
                            matching.setTextColor(Color.parseColor("#0bdb54"));
                        }else {
                            matching.setText(String.valueOf(q));
                            matching.setTextColor(Color.parseColor("#db3c0b"));
                        }
                        if (response.body().getData().getCreateby_id() == user.getId()){
                            quantity.setEnabled(true);
                            quantityCheck.setEnabled(false);
                            note.setEnabled(true);
                        }else {
                            quantity.setEnabled(false);
                            quantityCheck.setEnabled(true);
                            note.setEnabled(false);

                        }
//                        updatedby.setText(response.body().getData().);
//                        name.setText(response.body().getData().getProduct_name());



                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ProductInfoPage.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ProductInfoPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<DataResponse<ItemModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject = new JsonObject();
                if (itemModel.getCreateby().equals(user.getNameA())){
                    if (!quantity.getText().toString().equals(String.valueOf(itemModel.getQuantity()))){
                        if (quantity.getText().toString().isEmpty() || quantity.getText().toString().equals("0")){
                            quantity.requestFocus();
                            quantity.setError("يرجى ادخال الكمية اكبر من 0");
                        }else {
                            if (Float.parseFloat(quantity.getText().toString()) <=0){
                                quantity.requestFocus();
                                quantity.setError("يرجى ادخال الكمية اكبر من 0");
                            }else {
                                jsonObject.addProperty("quantity",Float.parseFloat(quantity.getText().toString()));
                            }
                        }
                    }
                    if (!note.getText().toString().equals(String.valueOf(itemModel.getNote()))){

                        if (note.getText().toString().isEmpty()){
                            note.requestFocus();
                            note.setError("يرجى كتابة ملاحظة");
                        }else {

                            jsonObject.addProperty("description",note.getText().toString());

                        }
                    }
                }else {
                    if (!quantityCheck.getText().toString().equals(String.valueOf(itemModel.getQuantitycheck()))){
                        if (quantityCheck.getText().toString().isEmpty() || quantityCheck.getText().toString().equals("0")){
                            quantityCheck.requestFocus();
                            quantityCheck.setError("يرجى ادخال الكمية اكبر من 0");
                        }else {
                            if (Float.parseFloat(quantityCheck.getText().toString()) <=0){
                                quantityCheck.requestFocus();
                                quantityCheck.setError("يرجى ادخال الكمية اكبر من 0");
                            }else {
                                jsonObject.addProperty("quantitycheck",Float.parseFloat(quantityCheck.getText().toString()));
                            }
                        }
                    }
                }

//                Toast.makeText(ProductInfoPage.this,jsonObject.toString()+"",Toast.LENGTH_LONG).show();


                updateData(jsonObject);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    void updateData(JsonObject jsonObject){
        progress.show();

        new RetrofitCon(this).getService().updateproducts("Bearer "+user.getAccessToken(),id,jsonObject).enqueue(new Callback<DataResponse<ProductModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ProductModel>> call, Response<DataResponse<ProductModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        Toast.makeText(ProductInfoPage.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ProductInfoPage.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ProductInfoPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<DataResponse<ProductModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }
            }
        });
    }

    void deleteData(){
        progress.show();

        new RetrofitCon(this).getService().deleteproducts("Bearer "+user.getAccessToken(),id).enqueue(new Callback<DataResponse<ProductModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ProductModel>> call, Response<DataResponse<ProductModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        Toast.makeText(ProductInfoPage.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ProductInfoPage.this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ProductInfoPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<DataResponse<ProductModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }
            }
        });
    }
}