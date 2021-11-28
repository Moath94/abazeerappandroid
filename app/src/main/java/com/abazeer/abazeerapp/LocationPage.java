package com.abazeer.abazeerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.abazeer.abazeerapp.adapter.Adapter;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.LocationModel;
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

public class LocationPage extends AppCompatActivity {

    Adapter<LocationModel> locationModelAdapter;
    ArrayList<LocationModel> locationModelArrayList;
    RecyclerView recyclerView;

    @BindView(R.id.location_add)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.location_search)
    SearchView searchView;
    int id;
    String name;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        recyclerView = findViewById(R.id.rcvl);
        db = new DatabaseHandler(this);
        user= db.getUser();
        LinearLayoutManager llmanger = new LinearLayoutManager(this);
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        locationModelArrayList = new ArrayList<>();

        rcvl(locationModelArrayList);

        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        setTitle(name);

        search();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationPage.this, AddLocation.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

    }
    void getData(){
        progress.show();
        locationModelArrayList.clear();
        new RetrofitCon(this).getService().getOdooLocationZone("Bearer "+user.getAccessToken(),id).enqueue(new Callback<DataResponse<LocationModel>>() {
            @Override
            public void onResponse(Call<DataResponse<LocationModel>> call, Response<DataResponse<LocationModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getDatas().toString()+"dfg");

                        locationModelArrayList.addAll(response.body().getDatas());
                        locationModelAdapter.notifyDataSetChanged();

                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LocationPage.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LocationPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void search() {
        ArrayList<LocationModel> filteredList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filteredList.clear();
                for (LocationModel item : locationModelArrayList) {
                    String number = String.valueOf(item.getName());
                    if (number.toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                locationModelAdapter.filterList(filteredList);




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    rcvl(locationModelArrayList);
                } else {
                    filteredList.clear();

                    for (LocationModel item : locationModelArrayList) {
                        String number = String.valueOf(item.getName());
                        if (number.toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    locationModelAdapter.filterList(filteredList);

                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                rcvl(locationModelArrayList);
                return false;
            }
        });

    }



    public void rcvl(ArrayList<LocationModel> arrayList) {
        locationModelAdapter = new Adapter<LocationModel>(this, arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(LocationPage.this).inflate(R.layout.row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final LocationModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.itemName.setText(val.getName());
                adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LocationPage.this, ProductPage.class);
                        intent.putExtra("id",val.getId());
                        intent.putExtra("odoo_location_id",id);
                        intent.putExtra("location_name",val.getName());
                        intent.putExtra("zone_name",name);
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
        recyclerView.setAdapter(locationModelAdapter);

    }

    private static class AdapterHolder extends RecyclerView.ViewHolder {
        TextView itemNumber,itemPrice,itemQuantity,itemName,itemDes,itemDis;

        ImageView img;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);


            itemName = itemView.findViewById(R.id.stgrow_name);
//            itemNumber = itemView.findViewById(R.id.itemRow_itemNumber);
//            itemPrice = itemView.findViewById(R.id.itemRow_price);
//            itemQuantity = itemView.findViewById(R.id.itemRow_quantity);
//            itemDes = itemView.findViewById(R.id.itemRow_description);
//            itemDis = itemView.findViewById(R.id.itemRow_discount);
//            img = itemView.findViewById(R.id.itemRow_img);


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

        locationModelArrayList.remove(position);
        locationModelAdapter.notifyItemRemoved(position);
        locationModelAdapter.notifyItemRangeChanged(position, locationModelArrayList.size());
    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}