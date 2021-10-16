package com.abazeer.abazeerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.abazeer.abazeerapp.model.ReturnItemModel;
import com.abazeer.abazeerapp.model.StanderResponse;
import com.abazeer.abazeerapp.model.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnItemShow extends AppCompatActivity {

    @BindView(R.id.returnitem_table)
    TableLayout tableLayout;
    @BindView(R.id.returnitem_cname)
    TextView cname;
    @BindView(R.id.returnitem_repname)
    TextView repname;
    @BindView(R.id.returnitem_name)
    TextView namet;
    @BindView(R.id.returnitem_warename)
    TextView warename;
    @BindView(R.id.returnitem_pcount)
    TextView returnitem_pcount;

    ArrayList<ReturnItemModel> OrderDataModelArrayList;
    ArrayList<ReturnItemModel> returnArray;
    private ProgressDialog progress;

    String name;
    String w_name;
    String p_name;
    String r_name;

    DatabaseHandler db;
    UserModel user;

    int return_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retun_item_show);
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
        w_name = getIntent().getStringExtra("w_name");
        p_name = getIntent().getStringExtra("p_name");
        r_name = getIntent().getStringExtra("r_name");
        return_id = getIntent().getIntExtra("return_id",0);

        namet.setText(name);
        warename.setText(w_name);
        cname.setText(p_name);
        repname.setText(r_name);
        getData();

    }


    void showData(){

//        OrderDataModelArrayList.addAll(db.getOrders());
        returnitem_pcount.setText(String.valueOf(OrderDataModelArrayList.size()));


        for (int i=0;i<OrderDataModelArrayList.size();i++){
            View tableRow = LayoutInflater.from(this).inflate(R.layout.return_row,null,false);

            TextView p_name  = (TextView) tableRow.findViewById(R.id.returnrow_product);
            TextView ex_name  = (TextView) tableRow.findViewById(R.id.returnrow_exdate);
            TextView qtyv  = (TextView) tableRow.findViewById(R.id.returnrow_qtyv);
            TextView qtyd  = (TextView) tableRow.findViewById(R.id.returnrow_qtyd);
            TextView qtyex  = (TextView) tableRow.findViewById(R.id.returnrow_qtyex);
            TextView total  = (TextView) tableRow.findViewById(R.id.returnrow_total);

            p_name.setText(OrderDataModelArrayList.get(i).getProduct_name());
            ex_name.setText(OrderDataModelArrayList.get(i).getLot_name());
            qtyv.setText(String.valueOf(OrderDataModelArrayList.get(i).getX_studio_abz_product_valid_driver()));
            qtyd.setText(String.valueOf(OrderDataModelArrayList.get(i).getX_studio_abz_product_damaged_driver()));
            qtyex.setText(String.valueOf(OrderDataModelArrayList.get(i).getX_studio_abz_product_exp_driver()));
            total.setText(String.valueOf(OrderDataModelArrayList.get(i).getX_studio_abz_total_qty_driver()));



            tableLayout.addView(tableRow);
        }
//        OrderDataModelAdapter.notifyDataSetChanged();


    }



    void getData(){
        progress.show();
        new RetrofitCon(this).getService().getReturnsItems("Bearer "+user.getAccessToken(), return_id).enqueue(new Callback<DataResponse<ReturnItemModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ReturnItemModel>> call, Response<DataResponse<ReturnItemModel>> response) {
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
                        Toast.makeText(ReturnItemShow.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ReturnItemShow.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("E", e.getMessage());
                    }
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DataResponse<ReturnItemModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });
    }


}