package com.abazeer.abazeerapp.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abazeer.abazeerapp.R;
import com.abazeer.abazeerapp.adapter.Adapter;
import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.ProductsModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchProductFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchProductFr extends AppCompatDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Adapter<ProductsModel> adapter;
    ArrayList<ProductsModel> arrayList;
    RecyclerView recyclerView;
    SearchView searchView;
   private getProductData lisener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progress;

    public SearchProductFr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchProductFr.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchProductFr newInstance() {
        SearchProductFr fragment = new SearchProductFr();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        progress = new ProgressDialog(getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search_product, null,false);
        arrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.searchpr_rclr);
        searchView = view.findViewById(R.id.searchpr_search);
        LinearLayoutManager llmanger = new LinearLayoutManager(getContext());
        llmanger.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanger);
        rcvl(arrayList);

        builder.setView(view);
        builder.setTitle("البحث عن منتج");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                getData(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    rcvl(arrayList);
                } else {
//                    filter(newText);
                }
                return false;
            }
        });
        return builder.create() ;
    }

    void getData(String q){
        progress.show();

        new RetrofitCon(getContext()).getService().searchproduct("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiMjI3N2M4N2U1ZDQ1NDIwOTViYjY1ZGIzODRkN2Q5ZTU3YTUyNWNlOTQxMTc5NDhlMDE0OTIxZjI5N2JlYmM5ZWYwMTkyNzFkN2ZhNmI2MzkiLCJpYXQiOjE2MTI5MDEwMjcsIm5iZiI6MTYxMjkwMTAyNywiZXhwIjoxNjQ0NDM3MDI3LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.Go0IpdKp9coWiYOIKO8emPkV9WsIVRkJlMw3SkGzAiY0e2ftwIhHaJmDALSkvQIdG1FJFLHykh4sIS7XAGXtgwRe9p_8n-TIPnO4v7nGQCMFM_r7eiC6wp6A8JufzO8f8wdES-8Ufef17ddvZsX41782M8rk25Oz1TOQSevYwsi1mEQhInps4IJ5L_TkiXIxrHSxjVfG2npBU7e3Hf1HS0H1TMcXcsJFKA3_FXK0rXW5ijKyxNUTu7j-O6yHpBhlR9qm9czyqVx6wsyfPm90DLItU1AYncNCTP1n23JqkP1Oh4otRlYvI6zVsizO8qSSGjKa9KggHmKKFknUdn5fP8Lvi-_LjVtvt879FnBgPC4Hh_Zv02yXz8qvs9ujc-zrULlLFnrjfmE0zyM9qoRKmaKYj-5APrYlqYUud6IaDKtWOrkhc2uZcOLy8S8LRvhtDp5rmvwnbQNPi4GNsp1mcWEiPG0TLx1xD7rGnOGQED1NYHE7zIc" +
                "KSnglyujNQDDL4ificIFH6mZJEMVUKK0DaMWm0cW0DrCKlin1vjpHWecHpj3sjrblSjDsqOTOlzqKo2ZOxEbq22DgQzFvWDp-XJ5zFH26IOOoBIWX-9fcP47t0TB_e5JWy9h7v2MMUJN3BrQns3haG3zf0Zp--_ssGwU4NHucvV782KXQtYQAVww",q).enqueue(new Callback<DataResponse<ProductsModel>>() {
            @Override
            public void onResponse(Call<DataResponse<ProductsModel>> call, Response<DataResponse<ProductsModel>> response) {

                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        arrayList.clear();
                        arrayList.addAll(response.body().getDatas());
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(arrayList.size() - 1);
                        recyclerView.scrollToPosition(arrayList.size() - 1);
                    }else {
                        Log.e("ErrorRes", response.message());

                    }
                }else{
                    if (response.errorBody() != null) {
                        Log.e("ResponseCodActive", response.code() + "");
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(response.errorBody().string());
                            Log.e("ErrorRes", jsonObj.toString());

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                progress.dismiss();


            }

            @Override
            public void onFailure(Call<DataResponse<ProductsModel>> call, Throwable t) {
                Log.e("ErrorRet", t.getMessage() + "");
                progress.dismiss();

            }
        });
    }

//    private void filter(String text) {
//        ArrayList<Invoice> filteredList = new ArrayList<>();
//
//        switch (searchByy) {
//            case 0:
//                for (Invoice item : arrayList) {
//                    String number = String.valueOf(item.getId());
//                    if (number.toLowerCase().contains(text.toLowerCase())) {
//                        filteredList.add(item);
//                    }
//                }
//                break;
//            case 1:
//                for (Invoice item : arrayList) {
//                    if (item.getCustomerphone() == Integer.parseInt(text)) {
//                        filteredList.add(item);
//                    }
//                }
//                break;
//            case 2:
//                for (Invoice item : arrayList) {
//                    String date = item.getDate();
//                    if (date.toLowerCase().contains(text.toLowerCase())) {
//                        filteredList.add(item);
//                    }
//                }
//                break;
//        }
//
//
//        adapter.filterList(filteredList);
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        lisener = (getProductData) context;
    }
    public interface getProductData{
        void productdata(String barcode, int id, String name, int lot, ArrayList<ProductsModel.Lots> lots);
        void productdata(String barcode, int id, String name, int lot);

    }
    public void rcvl(ArrayList<ProductsModel> arrayList) {
        adapter = new Adapter<ProductsModel>(getContext(), arrayList) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.product_row, parent, false);
                AdapterHolder holder = new AdapterHolder(view);

                return holder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, final ProductsModel val , int i) {

                AdapterHolder adapterHolder = (AdapterHolder) holder;

                adapterHolder.id.setText(String.valueOf(val.getId()));
                adapterHolder.name.setText(val.getName());
                adapterHolder.size.setText(val.getSize());
                adapterHolder.barcode.setText(String.valueOf(val.getBarcode()));

                adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getContext(), AddProductPage.class);
//                        intent.putExtra("name",val.getName());
//                        intent.putExtra("barcode",val.getBarcode());
//                        intent.putExtra("id",val.getId());
//                        Log.e("Lots",val.getLots().get(1).getName());
                        if (val.getLot() == 0){
                            lisener.productdata(String.valueOf(val.getBarcode()),val.getId(),val.getName(),val.getLot());

                        }else {
                            lisener.productdata(String.valueOf(val.getBarcode()),val.getId(),val.getName(),val.getLot(), (ArrayList<ProductsModel.Lots>) val.getLots());

                        }
                        dismiss();
//                        getTargetFragment().onActivityResult(getTargetRequestCode(),100,intent);
                    }
                });



            }
        };
        recyclerView.setAdapter(adapter);
    }

    private static class AdapterHolder extends RecyclerView.ViewHolder {
        TextView name,barcode,id,size;

        public AdapterHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.productrow_name);
            barcode = itemView.findViewById(R.id.productrow_barcode);
            size = itemView.findViewById(R.id.productrow_size);
            id = itemView.findViewById(R.id.productrow_id);



        }
    }
}