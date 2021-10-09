package com.abazeer.abazeerapp.ui.ordershow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abazeer.abazeerapp.ItemShow;
import com.abazeer.abazeerapp.R;
import com.abazeer.abazeerapp.adapter.Adapter;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.databinding.FragmentGalleryBinding;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.OrderModel;
import com.abazeer.abazeerapp.model.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderShow extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    Adapter<OrderModel> OrderDataModelAdapter;
    ArrayList<OrderModel> OrderDataModelArrayList;
    RecyclerView recyclerView;

//    @BindView(R.id.myorder_search)
//    SearchView searchView;
    //    @BindView(R.id.myorder_rcvl)
//    RecyclerView rcvl;
    int id;
    String name;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        recyclerView = root.findViewById(R.id.ordershow_rcvl);
        db = new DatabaseHandler(getContext());
//        user= db.getUser();
        LinearLayoutManager llmanger = new LinearLayoutManager(getContext());
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        OrderDataModelArrayList = new ArrayList<>();

        rcvl(OrderDataModelArrayList);
//        showData();
        getData();
//        search();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    void showData(){
        OrderDataModelArrayList.clear();

//        OrderDataModelArrayList.addAll(db.getOrders());

        OrderDataModelAdapter.notifyDataSetChanged();


    }
//    private void search() {
//        ArrayList<OrderModel> filteredList = new ArrayList<>();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filteredList.clear();
//                for (OrderModel item : OrderDataModelArrayList) {
//                    String number = String.valueOf(item.getId());
//                    if (number.toLowerCase().contains(query.toLowerCase())) {
//                        filteredList.add(item);
//                    }
//                }
//                OrderDataModelAdapter.filterList(filteredList);
//
//
//
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                if (newText.isEmpty()) {
//                    rcvl(OrderDataModelArrayList);
//                } else {
//                    filteredList.clear();
//
//                    for (OrderModel item : OrderDataModelArrayList) {
//                        String number = String.valueOf(item.getId());
//                        if (number.toLowerCase().contains(newText.toLowerCase())) {
//                            filteredList.add(item);
//                        }
//                    }
//                    OrderDataModelAdapter.filterList(filteredList);
//
//                }
//                return false;
//            }
//        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//                rcvl(OrderDataModelArrayList);
//                return false;
//            }
//        });
//
//    }
    void getData(){
        progress.show();
        new RetrofitCon(getContext()).getService().getOrder("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYjYyZmM3NDFmNTM0ZmYxYzlhZDI1YWM5MTJjNmQwODM2NTljNTA2MjNjODNkYjIzYjhmMDQ4NWMyZDA5MzdlOGE5ZGQ1MWI0ZGI1NTAxMjMiLCJpYXQiOjE2MzM3MDA0NzIsIm5iZiI6MTYzMzcwMDQ3MiwiZXhwIjoxNjY1MjM2NDcyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.GZsu1Ct5f3FxFiZJuY2xSnNskgobb532MsUODwLxPZpESDQc9F8MHJJfnr8PFBvDfDBaHVogMWwS86am7gMXkaq7XGbELVioQksj4OVZIiYQk7wOwrbX7Y95ASSG7mxF_50XOtAqbOtecQnP2Q3sIz09aTr6oOlbPdFxeSUcoIJqDNW4vSos_LMdvpURvlaSaDbjMRd3fIHIrNoxMm53vzs5JJsPkGfzVJPc1Wa-w3HKThV7ChixA_yo13VWMcENlIXbQQlfXtQsRTcAJLc-k7Chkk8ZIqU7FXzZwFLJmuJ-e5XRyNIBRZcTaUQPnmZh9LGTyvS6tcxUVBorSzugsWCwfOBih19ZEigPTLhmBVLs5EgQ-nwvklAyxFEWwMs1LxlAds89jOCuBFHuF8rO0fqm6RcMpJjY2U3W1Jfww4hsWuJ2PWBJCS-Z_e2BAKUjEroZeuFzbgvi8f1oLYtkmWRKK8bYHu86RLYQAtk5kBcvoJRvqy-tgt18E7WKAxaHZ1f6jceM9fgd5peCDpEkg1gtRp04ZERTd6Dvw1VQqy7PADKP_1YJe4wBku_XBrUw6hVhhAlDmGy3pDdm3lrR9T7iFairUXUGHg-jfcfpHAXjQ_sZtRhXnIsYx-PJCeU6xWAhkLotxF24mVLuJtjoYH3tnltT47hU9B6x7PKR-Qw").enqueue(new Callback<DataResponse<OrderModel>>() {
            @Override
            public void onResponse(Call<DataResponse<OrderModel>> call, Response<DataResponse<OrderModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getDatas().toString()+"dfg");
//                        if (response.body().getDatas().size() != db.getOrdersCount()) {
                            progress.show();
//                            db.deleteOrders();
//                            for (OrderModel orderDataModel : response.body().getDatas()) {
//                                db.addoOrder(orderDataModel);

//                            }
                        OrderDataModelArrayList.clear();

                        OrderDataModelArrayList.addAll(response.body().getDatas());

                        OrderDataModelAdapter.notifyDataSetChanged();
//                            showData();
//                        }else {
//                            for (OrderModel orderDataModel : response.body().getDatas()) {
////                                db.addoOrder(orderDataModel);
//
//                            }
//                            showData();

//                        }
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DataResponse<OrderModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });
    }



    public void rcvl(ArrayList<OrderModel> arrayList) {
        OrderDataModelAdapter = new Adapter<OrderModel>(getContext(), arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.order_row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final OrderModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.ref.setText(val.getName());
                adapterHolder.repres.setText(val.getR_name());
                adapterHolder.customer.setText(val.getC_name());
                adapterHolder.wearhouse.setText(val.getL_name());
//                adapterHolder.status.setText(String.valueOf(val.getId()));

                adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ItemShow.class);

                        intent.putExtra("name",val.getName());
                        intent.putExtra("cnamet",val.getC_name());
                        intent.putExtra("lname",val.getL_name());
                        intent.putExtra("rname",val.getR_name());

                        startActivity(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(OrderDataModelAdapter);

    }
    private static class AdapterHolder extends RecyclerView.ViewHolder {
        TextView ref,customer,repres,wearhouse,status;

        Button btn;
        ImageView img;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);

            ref = itemView.findViewById(R.id.order_number);
            status = itemView.findViewById(R.id.order_status);
            wearhouse = itemView.findViewById(R.id.order_wearhouse);
            repres = itemView.findViewById(R.id.order_repres);
            customer = itemView.findViewById(R.id.order_customer);



        }
    }

    boolean contains(ArrayList<OrderModel> list, int name) {
        for (OrderModel item : list) {
            if (item.getId() == name) {
                return true;
            }
        }
        return false;
    }
    public void removeAt(int position) {

        OrderDataModelArrayList.remove(position);
        OrderDataModelAdapter.notifyItemRemoved(position);
        OrderDataModelAdapter.notifyItemRangeChanged(position, OrderDataModelArrayList.size());
    }
}