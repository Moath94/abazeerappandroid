package com.abazeer.abazeerapp.ui.inventory;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abazeer.abazeerapp.LocationPage;
import com.abazeer.abazeerapp.R;
import com.abazeer.abazeerapp.adapter.Adapter;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.databinding.FragmentGalleryBinding;
import com.abazeer.abazeerapp.databinding.FragmentInventoryBinding;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.UserModel;
import com.abazeer.abazeerapp.model.ZoneModel;
import com.abazeer.abazeerapp.ui.ordershow.GalleryViewModel;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentInventoryBinding binding;
    Adapter<ZoneModel> zoneModelAdapter;
    ArrayList<ZoneModel> zoneModelArrayList;
    RecyclerView recyclerView;
    SearchView searchView;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        db = new DatabaseHandler(getContext());
        user= db.getUser();
//        final TextView textView = root.findViewById(R.id.text_gallery);
        recyclerView = root.findViewById(R.id.rcvl);
        LinearLayoutManager llmanger = new LinearLayoutManager(getContext());
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        zoneModelArrayList = new ArrayList<>();
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        rcvl(zoneModelArrayList);


        return root;
    }


    public void rcvl(ArrayList<ZoneModel> arrayList) {
        zoneModelAdapter = new Adapter<ZoneModel>(getContext(), arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.storge_row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final ZoneModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.itemName.setText(val.getName_zone());
                adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LocationPage.class);
                        intent.putExtra("id", val.getId());
                        intent.putExtra("name", val.getName_zone());

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
        recyclerView.setAdapter(zoneModelAdapter);

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

        zoneModelArrayList.remove(position);
        zoneModelAdapter.notifyItemRemoved(position);
        zoneModelAdapter.notifyItemRangeChanged(position,zoneModelArrayList.size());
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();

    }

    private void getData() {
        progress.show();
        zoneModelArrayList.clear();
        new RetrofitCon(getContext()).getService().zone("Bearer "+user.getAccessToken()).enqueue(new Callback<DataResponse<ZoneModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ZoneModel>> call, Response<DataResponse<ZoneModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
//                        textView.setText(response.body().getData().get(0).getName_zone()+"dfg");
                        Log.e("Response",response.body().getDatas().toString()+"dfg");

                        zoneModelArrayList.addAll(response.body().getDatas());
                        zoneModelAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<DataResponse<ZoneModel>> call, Throwable t) {
                if (t.getLocalizedMessage() != null) {
                    Log.e("ErrorFailure", t.getLocalizedMessage());
                    progress.dismiss();

                }

            }
        });
    }
}