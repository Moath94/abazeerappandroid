package com.abazeer.abazeerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.OrderItemModel;

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
    @BindView(R.id.itemshow_spinner_op)
    Spinner spinner;

    ArrayList<OrderItemModel> OrderDataModelArrayList;
    private ProgressDialog progress;

    String name;
    String rname;
    String lname;
    String cnamet;
    String wreft;
    String sreft;
    String finreft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_show);
        ButterKnife.bind(this);

        progress = new ProgressDialog(this);
        OrderDataModelArrayList = new ArrayList<>();
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        name = getIntent().getStringExtra("name");
        rname = getIntent().getStringExtra("rname");
        lname = getIntent().getStringExtra("lname");
        cnamet = getIntent().getStringExtra("cnamet");
        sreft = getIntent().getStringExtra("sreft");
        finreft = getIntent().getStringExtra("finreft");

        wref.setText(name);
        saref.setText(sreft);
        finref.setText(finreft);
        cname.setText(cnamet);
        rename.setText(rname);
        wname.setText(lname);

        getData();
    }

    void showData(){

//        OrderDataModelArrayList.addAll(db.getOrders());
        for (int i=0;i<OrderDataModelArrayList.size();i++){
            View tableRow = LayoutInflater.from(this).inflate(R.layout.order_table_row,null,false);
            TextView ordertr_name  = (TextView) tableRow.findViewById(R.id.ordertr_name);
            TextView ordertr_qty  = (TextView) tableRow.findViewById(R.id.ordertr_qty);
            EditText ordertr_qtyd  = (EditText) tableRow.findViewById(R.id.ordertr_qtyd);
            TextView ordertr_qtyr  = (TextView) tableRow.findViewById(R.id.ordertr_qtyr);

            ordertr_name.setText(OrderDataModelArrayList.get(i).getP_name());

            ordertr_qty.setText(String.valueOf(OrderDataModelArrayList.get(i).getQty_done()));
            ordertr_qtyd.setText(String.valueOf(OrderDataModelArrayList.get(i).getQty_done()));
            ordertr_qtyr.setText(String.valueOf(0));
            int finalI = i;
            ordertr_qtyd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (!charSequence.toString().isEmpty()){
                       int diff =  OrderDataModelArrayList.get(finalI).getQty_done() - Integer.parseInt(charSequence.toString());
                        ordertr_qtyr.setText(String.valueOf(diff));


                    }else {
                        ordertr_qtyr.setText(String.valueOf(OrderDataModelArrayList.get(finalI).getQty_done()));
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


    void getData(){
        progress.show();
        new RetrofitCon(this).getService().getItems("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYjYyZmM3NDFmNTM0ZmYxYzlhZDI1YWM5MTJjNmQwODM2NTljNTA2MjNjODNkYjIzYjhmMDQ4NWMyZDA5MzdlOGE5ZGQ1MWI0ZGI1NTAxMjMiLCJpYXQiOjE2MzM3MDA0NzIsIm5iZiI6MTYzMzcwMDQ3MiwiZXhwIjoxNjY1MjM2NDcyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.GZsu1Ct5f3FxFiZJuY2xSnNskgobb532MsUODwLxPZpESDQc9F8MHJJfnr8PFBvDfDBaHVogMWwS86am7gMXkaq7XGbELVioQksj4OVZIiYQk7wOwrbX7Y95ASSG7mxF_50XOtAqbOtecQnP2Q3sIz09aTr6oOlbPdFxeSUcoIJqDNW4vSos_LMdvpURvlaSaDbjMRd3fIHIrNoxMm53vzs5JJsPkGfzVJPc1Wa-w3HKThV7ChixA_yo13VWMcENlIXbQQlfXtQsRTcAJLc-k7Chkk8ZIqU7FXzZwFLJmuJ-e5XRyNIBRZcTaUQPnmZh9LGTyvS6tcxUVBorSzugsWCwfOBih19ZEigPTLhmBVLs5EgQ-nwvklAyxFEWwMs1LxlAds89jOCuBFHuF8rO0fqm6RcMpJjY2U3W1Jfww4hsWuJ2PWBJCS-Z_e2BAKUjEroZeuFzbgvi8f1oLYtkmWRKK8bYHu86RLYQAtk5kBcvoJRvqy-tgt18E7WKAxaHZ1f6jceM9fgd5peCDpEkg1gtRp04ZERTd6Dvw1VQqy7PADKP_1YJe4wBku_XBrUw6hVhhAlDmGy3pDdm3lrR9T7iFairUXUGHg-jfcfpHAXjQ_sZtRhXnIsYx-PJCeU6xWAhkLotxF24mVLuJtjoYH3tnltT47hU9B6x7PKR-Qw", name).enqueue(new Callback<DataResponse<OrderItemModel>>() {
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


}