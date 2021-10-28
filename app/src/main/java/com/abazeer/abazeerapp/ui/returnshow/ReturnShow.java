package com.abazeer.abazeerapp.ui.returnshow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abazeer.abazeerapp.AddNewReturn;
import com.abazeer.abazeerapp.ItemShow;
import com.abazeer.abazeerapp.R;
import com.abazeer.abazeerapp.ReturnItemShow;
import com.abazeer.abazeerapp.adapter.Adapter;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.databinding.FragmentHomeBinding;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.OrderModel;
import com.abazeer.abazeerapp.model.ReturnOrderModel;
import com.abazeer.abazeerapp.model.UserModel;
import com.abazeer.abazeerapp.ui.ordershow.OrderShow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnShow extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    Adapter<ReturnOrderModel> OrderDataModelAdapter;
    ArrayList<ReturnOrderModel> OrderDataModelArrayList;
    RecyclerView recyclerView;

    int id;
    String name;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;
    FloatingActionButton returnshow_addbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ButterKnife.bind(root);
        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        recyclerView = root.findViewById(R.id.returnorder_rcvl);
        returnshow_addbtn = root.findViewById(R.id.returnshow_addbtn);
        db = new DatabaseHandler(getContext());
        user= db.getUser();
        LinearLayoutManager llmanger = new LinearLayoutManager(getContext());
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        OrderDataModelArrayList = new ArrayList<>();

        rcvl(OrderDataModelArrayList);
        getData();
        binding.returnshowAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AddNewReturn.class);

                startActivity(intent);
            }
        });
        returnshow_addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReturnItemShow.class);
                intent.putExtra("state","new");
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void getData(){
        progress.show();
        new RetrofitCon(getContext()).getService().getReturns("Bearer "+user.getAccessToken()).enqueue(new Callback<DataResponse<ReturnOrderModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ReturnOrderModel>> call, Response<DataResponse<ReturnOrderModel>> response) {
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
            public void onFailure(Call<DataResponse<ReturnOrderModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });
    }



    public void rcvl(ArrayList<ReturnOrderModel> arrayList) {
        OrderDataModelAdapter = new Adapter<ReturnOrderModel>(getContext(), arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.order_row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final ReturnOrderModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.ref.setText(val.getName());
                adapterHolder.repres.setText(val.getX_studio_abz_rep_name());
                adapterHolder.customer.setText(val.getPartner_name());
                adapterHolder.wearhouse.setText(val.getWarehouse_name());
                adapterHolder.status.setText(val.getStatusOdoo());

                adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ReturnItemShow.class);

                        intent.putExtra("name",val.getName());
                        intent.putExtra("w_name",val.getWarehouse_name());
                        intent.putExtra("p_name",val.getPartner_name());
                        intent.putExtra("r_name",val.getX_studio_abz_rep_name());
                        intent.putExtra("return_id",val.getId());
                        intent.putExtra("state",val.getStatusOdoo());


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