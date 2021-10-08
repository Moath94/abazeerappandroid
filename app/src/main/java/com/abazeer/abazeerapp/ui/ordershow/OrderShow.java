package com.abazeer.abazeerapp.ui.ordershow;

import android.app.ProgressDialog;
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

import com.abazeer.abazeerapp.R;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.databinding.FragmentGalleryBinding;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderShow extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    Adapter<OrderDataModel> OrderDataModelAdapter;
    ArrayList<OrderDataModel> OrderDataModelArrayList;
    RecyclerView recyclerView;

    @BindView(R.id.myorder_search)
    SearchView searchView;
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
        showData();
        getData();
        search();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    void showData(){
        OrderDataModelArrayList.clear();

        OrderDataModelArrayList.addAll(db.getOrders());

        OrderDataModelAdapter.notifyDataSetChanged();


    }
    private void search() {
        ArrayList<OrderDataModel> filteredList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filteredList.clear();
                for (OrderDataModel item : OrderDataModelArrayList) {
                    String number = String.valueOf(item.getId());
                    if (number.toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                OrderDataModelAdapter.filterList(filteredList);




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    rcvl(OrderDataModelArrayList);
                } else {
                    filteredList.clear();

                    for (OrderDataModel item : OrderDataModelArrayList) {
                        String number = String.valueOf(item.getId());
                        if (number.toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    OrderDataModelAdapter.filterList(filteredList);

                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                rcvl(OrderDataModelArrayList);
                return false;
            }
        });

    }
    void getData(){
//        progress.show();
        new RetrofitCon(getContext()).getService().getOrder("Bearer "+user.getAccessToken()).enqueue(new Callback<DataResponse<OrderDataModel>>() {
            @Override
            public void onResponse(Call<DataResponse<OrderDataModel>> call, Response<DataResponse<OrderDataModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getDatas().toString()+"dfg");
                        if (response.body().getDatas().size() != db.getOrdersCount()) {
                            progress.show();
                            db.deleteOrders();
                            for (OrderDataModel orderDataModel : response.body().getDatas()) {
                                db.addoOrder(orderDataModel);

                            }
                            showData();
                        }else {
                            for (OrderDataModel orderDataModel : response.body().getDatas()) {
                                db.addoOrder(orderDataModel);

                            }
                            showData();

                        }
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DataResponse<OrderDataModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });
    }



    public void rcvl(ArrayList<OrderDataModel> arrayList) {
        OrderDataModelAdapter = new Adapter<OrderDataModel>(getContext(), arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.myorder_row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final OrderDataModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.id.setText(String.valueOf(val.getId()));
//                adapterHolder.storename.setText(val.());
                adapterHolder.distance.setText(val.getDistance());



            }
        };
        recyclerView.setAdapter(OrderDataModelAdapter);

    }
    private static class AdapterHolder extends RecyclerView.ViewHolder {
        TextView id,from,to,status,price,distance,storename,number,recivedtime,time;

        Button btn;
        ImageView img;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);




        }
    }

    boolean contains(ArrayList<OrderDataModel> list, int name) {
        for (OrderDataModel item : list) {
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