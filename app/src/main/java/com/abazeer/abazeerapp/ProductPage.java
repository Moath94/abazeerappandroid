package com.abazeer.abazeerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abazeer.abazeerapp.adapter.Adapter;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.ProductModel;
import com.abazeer.abazeerapp.model.UserModel;
import com.abazeer.abazeerapp.model.ZoneModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPage extends AppCompatActivity {
    Adapter<ProductModel> productModelAdapter;
    ArrayList<ProductModel> productModelArrayList;
    RecyclerView recyclerView;
    String name;
    int id;

    @BindView(R.id.productpage_search)
    SearchView searchView;
    @BindView(R.id.productpage_sp)
    Spinner spinner;
    @BindView(R.id.productpage_add)
    FloatingActionButton add;
    private int searchByy;
    DatabaseHandler db;
    UserModel user;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        ButterKnife.bind(this);
         progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        db = new DatabaseHandler(this);
        user= db.getUser();
        recyclerView = findViewById(R.id.rcvl);
        LinearLayoutManager llmanger = new LinearLayoutManager(this);
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        productModelArrayList = new ArrayList<>();
        rcvl(productModelArrayList);

        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("location_name");
        setTitle(name);
        search();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductPage.this, AddProductPage.class);

                intent.putExtra("id",id);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        Resources res = getResources();
        String[] months = res.getStringArray(R.array.search);
        spinner(months, spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchByy = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    void getData(){
        progress.show();
        productModelArrayList.clear();
        new RetrofitCon(this).getService().products("Bearer "+user.getAccessToken(),id).enqueue(new Callback<DataResponse<ProductModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ProductModel>> call, Response<DataResponse<ProductModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getDatas().toString()+"dfg");

                        productModelArrayList.addAll(response.body().getDatas());
                        productModelAdapter.notifyDataSetChanged();

                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ProductPage.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ProductPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    void spinner(String[] arrayString, Spinner spinner) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_list,
                arrayString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    private void search() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                filter(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    rcvl(productModelArrayList);
                } else {
                    filter(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                rcvl(productModelArrayList);
                return false;
            }
        });

    }

    private void filter(String text) {
        ArrayList<ProductModel> filteredList = new ArrayList<>();

        switch (searchByy) {
            case 0:
                for (ProductModel item : productModelArrayList) {
                    String number = String.valueOf(item.getProduct_name());
                    if (number.toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                break;
            case 1:
                for (ProductModel item : productModelArrayList) {
                    if (item.getProduct_quantity() == Double.parseDouble(text)) {
                        filteredList.add(item);
                    }
                }
                break;
            case 2:
                for (ProductModel item : productModelArrayList) {
                    String number = String.valueOf(item.getProduct_barcode());

                    if (number.toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                break;
        }


        productModelAdapter.filterList(filteredList);
    }

    public void rcvl(ArrayList<ProductModel> arrayList) {
        productModelAdapter = new Adapter<ProductModel>(this, arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(ProductPage.this).inflate(R.layout.row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final ProductModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.itemName.setText(val.getProduct_name());
                adapterHolder.itemId.setText(String.valueOf(val.getId()));
                adapterHolder.size.setText(val.getSize());
                adapterHolder.itemExpiry.setText(val.getProduct_expiry());
                adapterHolder.itemQuantity.setText(String.valueOf(val.getProduct_quantity()));
                adapterHolder.itemBarcode.setText(String.valueOf(val.getProduct_barcode()));
                adapterHolder.itemCreateby.setText(val.getCreated_by());
                if (val.getDelete() >0){
                    adapterHolder.cardView.setCardBackgroundColor(Color.parseColor("#db3c0b"));
                }
                adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductPage.this, ProductInfoPage.class);
                        intent.putExtra("id", val.getId());

                        startActivity(intent);
                    }
                });
//                adapterHolder.itemName.setText(val.getName());
//                adapterHolder.itemDes.setText(val.getDescription());
//                adapterHolder.itemQuantity.setText(String.valueOf(val.getQuantity()));
//                adapterHolder.itemDis.setText(val.getDiscount()+"%");
//                adapterHolder.itemPrice.setText(String.valueOf(val.getPrice()));



            }
        };
        recyclerView.setAdapter(productModelAdapter);
    }

    private static class AdapterHolder extends RecyclerView.ViewHolder {
        TextView itemId,itemExpiry,size,itemQuantity,itemName,itemBarcode,itemCreateby;
        CardView cardView;

        ImageView img;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);


            itemName = itemView.findViewById(R.id.stgrow_name);
            itemId = itemView.findViewById(R.id.row_id);
            itemExpiry = itemView.findViewById(R.id.row_expiry);
            itemQuantity = itemView.findViewById(R.id.row_quantity);
            size = itemView.findViewById(R.id.row_size);
            itemBarcode = itemView.findViewById(R.id.row_barcode);
            itemCreateby = itemView.findViewById(R.id.row_createby);
            cardView = itemView.findViewById(R.id.row_card);



        }
    }

    boolean contains(ArrayList<ZoneModel> list, int name) {
        for (ZoneModel item : list) {
            if (item.getId() == name) {
                return true;
            }
        }
        return false;
    }
    public void removeAt(int position) {

        productModelArrayList.remove(position);
        productModelAdapter.notifyItemRemoved(position);
        productModelAdapter.notifyItemRangeChanged(position, productModelArrayList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}