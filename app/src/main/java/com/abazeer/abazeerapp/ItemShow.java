package com.abazeer.abazeerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.OrderItemModel;
import com.abazeer.abazeerapp.model.StanderResponse;
import com.abazeer.abazeerapp.model.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemShow extends AppCompatActivity {

    @BindView(R.id.itemshow_table)
    TableLayout tableLayout;
    @BindView(R.id.itemshow_wref)
    TextView wref;
    @BindView(R.id.itemshow_finref)
    TextView finref;
    @BindView(R.id.itemshow_saref)
    TextView saref;
    @BindView(R.id.itemshow_cname)
    TextView cname;
    @BindView(R.id.itemshow_rname)
    TextView rename;
    @BindView(R.id.itemshow_wname)
    TextView wname;
    @BindView(R.id.itemshow_productCount)
    TextView itemshow_productCount;
    @BindView(R.id.itemshow_spinner_op)
    Spinner spinner;
    @BindView(R.id.itemshow_btn_delivered)
    Button delivered_btn;

    ArrayList<OrderItemModel> OrderDataModelArrayList;
    ArrayList<OrderItemModel> returnArray;
    private ProgressDialog progress;

    String name;
    String rname;
    String lname;
    String cnamet;
    String wreft;
    String sreft;
    String finreft;

    DatabaseHandler db;
    UserModel user;
    int operator = -1;
    int sa_id;
    int wea_id;
    int l_id;
    int del_id;
    int c_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_show);
        ButterKnife.bind(this);
        db = new DatabaseHandler(this);
        user= db.getUser();
        progress = new ProgressDialog(this);
        OrderDataModelArrayList = new ArrayList<>();
        returnArray = new ArrayList<>();
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        name = getIntent().getStringExtra("name");
        rname = getIntent().getStringExtra("r_name");
        lname = getIntent().getStringExtra("l_name");
        cnamet = getIntent().getStringExtra("c_name");
        sreft = getIntent().getStringExtra("sa_name");
        sa_id = getIntent().getIntExtra("sa_id", 0);
        wea_id = getIntent().getIntExtra("wea_id",0);
        del_id = getIntent().getIntExtra("del_id",0);
        l_id = getIntent().getIntExtra("l_id",0);
        c_id = getIntent().getIntExtra("c_id",0);

        wref.setText(name);
        saref.setText(sreft);
        finref.setText(finreft);
        cname.setText(cnamet);
        rename.setText(rname);
        wname.setText(lname);

        getData();
        spinner(spinner,R.array.operator);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                operator = position;
                updateBySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        delivered_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delivered();
            }
        });
    }

    void updateBySpinner(){
        if (tableLayout.getChildCount() > -1){
            tableLayout.removeAllViews();
            showData();
        }
    }
    void showData(){

//        OrderDataModelArrayList.addAll(db.getOrders());
        itemshow_productCount.setText(String.valueOf(OrderDataModelArrayList.size() + 1));


        for (int i=0;i<OrderDataModelArrayList.size();i++){
            View tableRow = LayoutInflater.from(this).inflate(R.layout.order_table_row,null,false);

            TextView ordertr_name  = (TextView) tableRow.findViewById(R.id.ordertr_name);
            TextView ordertr_qty  = (TextView) tableRow.findViewById(R.id.ordertr_qty);
            EditText ordertr_qtyd  = (EditText) tableRow.findViewById(R.id.ordertr_qtyd);
            TextView ordertr_qtyr  = (TextView) tableRow.findViewById(R.id.ordertr_qtyr);
            TextView ordertr_lot  = (TextView) tableRow.findViewById(R.id.ordertr_lot);

            ordertr_name.setText(OrderDataModelArrayList.get(i).getP_name());
            ordertr_qty.setText(String.valueOf(OrderDataModelArrayList.get(i).getQty_done()));
            ordertr_lot.setText(OrderDataModelArrayList.get(i).getL_name());

            switch (operator){

                case 0:
                    ordertr_qtyd.setText(String.valueOf(OrderDataModelArrayList.get(i).getQty_done()));
                    ordertr_qtyd.setEnabled(false);
                    ordertr_qtyr.setText(String.valueOf(0));
                    OrderDataModelArrayList.get(i).setQty_return(0);

                    break;

                case 1:
                    ordertr_qtyd.setText(String.valueOf(OrderDataModelArrayList.get(i).getQty_done()));
                    ordertr_qtyr.setText(String.valueOf(0));
                    OrderDataModelArrayList.get(i).setQty_return(0);

                    break;

                case 2:
                    ordertr_qtyd.setText(String.valueOf(0));
                    ordertr_qtyd.setEnabled(false);
                    ordertr_qtyr.setText(String.valueOf(OrderDataModelArrayList.get(i).getQty_done()));
                    OrderDataModelArrayList.get(i).setQty_return(OrderDataModelArrayList.get(i).getQty_done());

                    break;


            }

            int finalI = i;
            ordertr_qtyd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (!charSequence.toString().isEmpty()){
                        int diff = 0;
                        if (Integer.parseInt(charSequence.toString())<= OrderDataModelArrayList.get(finalI).getQty_done() && Integer.parseInt(charSequence.toString()) >= 0){
                             diff =  OrderDataModelArrayList.get(finalI).getQty_done() - Integer.parseInt(charSequence.toString());


                        }else if (Integer.parseInt(charSequence.toString()) > OrderDataModelArrayList.get(finalI).getQty_done()){
                            diff = OrderDataModelArrayList.get(finalI).getQty_done();
                            ordertr_qtyd.setText(String.valueOf(diff));

                        }else if (Integer.parseInt(charSequence.toString()) < 0){
                            diff = 0;
                            ordertr_qtyd.setText(String.valueOf(diff));

                        }
                        ordertr_qtyr.setText(String.valueOf(diff));
                        OrderDataModelArrayList.get(finalI).setQty_return(diff);


                    }else {
                        ordertr_qtyr.setText(String.valueOf(OrderDataModelArrayList.get(finalI).getQty_done()));
                        OrderDataModelArrayList.get(finalI).setQty_return(OrderDataModelArrayList.get(finalI).getQty_done());

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tableLayout.addView(tableRow);
        }
//        OrderDataModelAdapter.notifyDataSetChanged();


    }

    void delivered(){

        JsonObject order = new JsonObject();
        order.addProperty("name",name);
        order.addProperty("s_id",sa_id);
        order.addProperty("l_id",l_id);
        order.addProperty("del_id",del_id);
        order.addProperty("c_id",c_id);
        order.addProperty("s_name",sreft);
        JsonObject products = new JsonObject();
        JsonArray productsArray = new JsonArray();
        for (int i=0;i<OrderDataModelArrayList.size();i++) {
            if (OrderDataModelArrayList.get(i).getQty_return() > 0){
                JsonObject p = new JsonObject();

                p.addProperty("qty_return",OrderDataModelArrayList.get(i).getQty_return());
                p.addProperty("unit_id",OrderDataModelArrayList.get(i).getUnit_id());
                p.addProperty("id",OrderDataModelArrayList.get(i).getP_id());
                p.addProperty("lot_id",OrderDataModelArrayList.get(i).getL_id());
                p.addProperty("mo_id",OrderDataModelArrayList.get(i).getM_id());
                p.addProperty("group_id",OrderDataModelArrayList.get(i).getGroup_id());


                productsArray.add(p);


            }

        }


        products.add("products",productsArray);
//        JSONArray products = new JSONArray();

        JsonObject orders = new JsonObject();
        orders.add("orders", order);
        orders.add("products", productsArray);

        new RetrofitCon(this).getService().delivered("Bearer "+user.getAccessToken(),orders).enqueue(new Callback<StanderResponse>() {
            @Override
            public void onResponse(Call<StanderResponse> call, Response<StanderResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isSuccess()){

                        Toast.makeText(ItemShow.this,response.body().getMessage(), Toast.LENGTH_LONG).show();

                        Log.e("E", response.body().getMessage());

                        delivered_btn.setEnabled(false);
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ItemShow.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        Log.e("E", jObjError.getString("message"));

                    } catch (Exception e) {
                        Toast.makeText(ItemShow.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("E", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<StanderResponse> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }
            }
        });
    }

    void getData(){
        progress.show();
        new RetrofitCon(this).getService().getItems("Bearer "+user.getAccessToken(), name).enqueue(new Callback<DataResponse<OrderItemModel>>() {
            @Override
            public void onResponse(Call<DataResponse<OrderItemModel>> call, Response<DataResponse<OrderItemModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        progress.show();

                        OrderDataModelArrayList.clear();

                        OrderDataModelArrayList.addAll(response.body().getDatas());

                        showData();

                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ItemShow.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ItemShow.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("E", e.getMessage());
                    }
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DataResponse<OrderItemModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });
    }

    void spinner(Spinner spinner, int arrayString) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayString, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}