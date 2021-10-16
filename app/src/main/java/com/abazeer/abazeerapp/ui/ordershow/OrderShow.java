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
        user= db.getUser();
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
        new RetrofitCon(getContext()).getService().getOrder("Bearer "+user.getAccessToken()).enqueue(new Callback<DataResponse<OrderModel>>() {
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
                        intent.putExtra("wea_id",val.getId());
                        intent.putExtra("sa_id",val.getS_id());
                        intent.putExtra("sa_name",val.getS_name());
                        intent.putExtra("c_id",val.getC_id());
                        intent.putExtra("c_name",val.getC_name());
                        intent.putExtra("l_id",val.getL_id());
                        intent.putExtra("l_name",val.getL_name());
                        intent.putExtra("r_id",val.getR_id());
                        intent.putExtra("r_name",val.getR_name());
                        intent.putExtra("del_id",val.getDel_id());
                        intent.putExtra("del_name",val.getDel_name());
                        intent.putExtra("pt_id",val.getPt_id());
                        intent.putExtra("pt_name",val.getPt_name());
                        intent.putExtra("isdelivered",val.getIsdelivered());

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