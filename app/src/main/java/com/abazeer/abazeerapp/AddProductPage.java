package com.abazeer.abazeerapp;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.abazeer.abazeerapp.api.RetrofitCon;
import com.abazeer.abazeerapp.db.DatabaseHandler;
import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.ProductModel;
import com.abazeer.abazeerapp.model.ProductsModel;
import com.abazeer.abazeerapp.model.UserModel;
import com.abazeer.abazeerapp.model.YearModel;
import com.abazeer.abazeerapp.ui.SearchProductFr;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductPage extends AppCompatActivity implements SearchProductFr.getProductData {


    @BindView(R.id.addpage_barcode)
    TextView barcode;
    @BindView(R.id.addpage_btnsave)
    Button save;
    @BindView(R.id.addpage_productname)
    TextView productname;
    @BindView(R.id.addpage_chdate)
    CheckBox chdate;
    @BindView(R.id.addpage_spday)
    Spinner spday;
    @BindView(R.id.addpage_spmonth)
    Spinner spmonth;
    @BindView(R.id.addpage_spyear)
    Spinner spyear;
    @BindView(R.id.addpage_spexistdate)
    Spinner spexistdate;
    @BindView(R.id.addpage_layoutdate)
    LinearLayout layoutdate;
    @BindView(R.id.addpage_layoutdatee)
    LinearLayout layoutdatee;
    @BindView(R.id.addpage_note)
    EditText note;
    @BindView(R.id.addpage_quantityin)
    EditText quantityin;
    @BindView(R.id.addpage_product)
    EditText product;
    ArrayList<YearModel> yearModelArrayList;
    ArrayList<ProductsModel.Lots> lotsArrayList;

    String expiry, location_name,lot_name,unit_name;
    int year, daysInMonth, month, lot, day, location_id, product_id, lot_postion,lot_id,unit_id,odoo_location_id = 0;
    DatabaseHandler db;
    UserModel user;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_page);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        if (!chdate.isChecked()) {
            layoutdate.setVisibility(View.GONE);
        }
        db = new DatabaseHandler(this);
        user = db.getUser();
        lotsArrayList = new ArrayList<>();

        location_id = getIntent().getIntExtra("id", 0);
        odoo_location_id = getIntent().getIntExtra("odoo_location_id", 0);
        location_name = getIntent().getStringExtra("name");
        layoutdatee.setVisibility(View.INVISIBLE);

        setTitle(location_name);
        yearModelArrayList = new ArrayList<>();
        Resources res = getResources();
        String[] months = res.getStringArray(R.array.months);
        spinner(months, spmonth);

        chdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    layoutdate.setVisibility(View.VISIBLE);
                } else {
                    layoutdate.setVisibility(View.GONE);
                }
            }
        });
        spyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    spmonth.setEnabled(true);

                    Toast.makeText(AddProductPage.this, yearModelArrayList.get(position).getName(), Toast.LENGTH_LONG).show();

                    year = Integer.parseInt(yearModelArrayList.get(position).getName());
                } else {
                    Toast.makeText(AddProductPage.this, yearModelArrayList.get(position).getName(), Toast.LENGTH_LONG).show();

                    spmonth.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0){
//                    position = 1;
//                }
                if (position > 0) {
                    spday.setEnabled(true);

                    Toast.makeText(AddProductPage.this, position + "", Toast.LENGTH_LONG).show();
                    YearMonth yearMonthObject = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        if (position == 1){
//                            yearMonthObject = YearMonth.of(year, position);
//
//                        }else {
//                            yearMonthObject = YearMonth.of(year, position + 1);
//
//                        }
                        yearMonthObject = YearMonth.of(year, position);

                        daysInMonth = yearMonthObject.lengthOfMonth();
                    }

                    ArrayList<String> arrayList = new ArrayList<>();

                    for (int i = 0; i <= daysInMonth; i++) {

                        Log.e("day", i + "");

                        arrayList.add(String.valueOf(i));

                    }
                    String[] day = new String[arrayList.size()];
//                    day[0] = "اختر اليوم المناسب";
                    for (int i = 0; i < day.length; i++) {

                        Log.e("day", i + "");

                        day[i] = arrayList.get(i);

                    }

                    spinner(day, spday);

                    month = position;
                } else {
                    Toast.makeText(AddProductPage.this, position + "", Toast.LENGTH_LONG).show();

                    spday.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {


                    Toast.makeText(AddProductPage.this, position + "", Toast.LENGTH_LONG).show();


                    day = position;

                } else {
                    Toast.makeText(AddProductPage.this, position + "", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spexistdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    spyear.setEnabled(true);
//
//                    Toast.makeText(AddProductPage.this, lotsArrayList.get(position).getName(), Toast.LENGTH_LONG).show();
//                    expiry = "";
//                    layoutdatee.setVisibility(View.VISIBLE);
//                }
//                else if (position > 1){
                Toast.makeText(AddProductPage.this, lotsArrayList.get(position).getName(), Toast.LENGTH_LONG).show();

                expiry = lotsArrayList.get(position).getName();
                lot_name = lotsArrayList.get(position).getName();
                lot_id = lotsArrayList.get(position).getOdoo_id();
                lot_postion = position;
                spyear.setEnabled(false);
                layoutdatee.setVisibility(View.INVISIBLE);

//                }else {
//                    spyear.setEnabled(false);
//                    expiry = "";
//                    layoutdatee.setVisibility(View.INVISIBLE);
//
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProductPage.this, "Clicked", Toast.LENGTH_LONG).show();
                showEditDialog();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        getYear();

    }

    private void getYear() {
        progress.show();

        new RetrofitCon(this).getService().year("Bearer " + user.getAccessToken()).enqueue(new Callback<DataResponse<YearModel>>() {
            @Override
            public void onResponse(Call<DataResponse<YearModel>> call, Response<DataResponse<YearModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        yearModelArrayList.clear();
                        yearModelArrayList.add(new YearModel("اختر السنة", 0));
                        yearModelArrayList.addAll(response.body().getDatas());
                        String[] stringArrayList = new String[yearModelArrayList.size()];
                        for (int i = 0; i < yearModelArrayList.size(); i++) {
                            Log.e("Error", yearModelArrayList.get(i).getName());
                            stringArrayList[i] = yearModelArrayList.get(i).getName();
                        }
                        Log.e("Error", yearModelArrayList.toString());
                        spinner(stringArrayList, spyear);

                    } else {
                        Log.e("ResponseCodActive", response.code() + "");

                    }
                } else {
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
            public void onFailure(Call<DataResponse<YearModel>> call, Throwable t) {
                progress.dismiss();

            }
        });
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SearchProductFr searchProductFr = SearchProductFr.newInstance();


        searchProductFr.show(fm, "fragment_edit_name");


    }

    private void save() {
        String quantity = "0";
        if (!quantityin.getText().toString().isEmpty()) {
            quantity = quantityin.getText().toString();
        }
        if (Float.parseFloat(quantity) <= 0) {
            Toast.makeText(this, "لابد ان تكون الكمية المجرودة اكبر من صفر", Toast.LENGTH_LONG).show();
            quantityin.setError("الكمية اكبر من صفر");
            quantityin.requestFocus();
        } else {
            if (product_id <= 0) {
                Toast.makeText(this, "يرجى تحديد الصنف", Toast.LENGTH_LONG).show();

            } else {
                if (lot > 0) {


                    if (lot_postion != 0) {
                        if (!quantity.isEmpty()) {
                            progress.show();

                            new RetrofitCon(this).getService().addproductsodoo("Bearer " +
                                            user.getAccessToken(),
                                    location_id,
                                    expiry,
                                    product_id,
                                    productname.getText().toString(),
                                    note.getText().toString(),
                                    quantity,
                                    0,unit_name,unit_id,lot_id,lot_name,odoo_location_id).enqueue(new Callback<DataResponse<ProductModel>>() {
                                @Override
                                public void onResponse(Call<DataResponse<ProductModel>> call, Response<DataResponse<ProductModel>> response) {

                                    if (response.isSuccessful()) {
                                        if (response.body().isSuccess()) {

                                            spyear.setEnabled(false);
                                            spday.setEnabled(false);
                                            spmonth.setEnabled(false);

                                            spexistdate.setSelection(0);
                                            spmonth.setSelection(0);
                                            spyear.setSelection(0);
                                            expiry = "";
                                            product_id = 0;
                                            note.setText("");
                                            quantityin.setText("");
                                            barcode.setText("");
                                            productname.setText("");
                                            year = 0;
                                            daysInMonth = 0;
                                            month = 0;
                                            lot = 0;
                                            day = 0;
                                            lot_id = 0;
                                            lot_name = "";
                                            unit_id = 0;
                                            unit_name = "";
                                            chdate.setChecked(false);

                                        } else {
                                            Toast.makeText(AddProductPage.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    } else {
                                        if (response.errorBody() != null) {
                                            Log.e("ResponseCodActive", response.code() + "/" + expiry + "/" + product_id);
                                            JSONObject jsonObj = null;
                                            try {
                                                jsonObj = new JSONObject(response.errorBody().string());
                                                Log.e("ErrorRes", jsonObj.toString());
                                                Toast.makeText(AddProductPage.this, jsonObj.getString("message"), Toast.LENGTH_LONG).show();

                                            } catch (JSONException | IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    progress.dismiss();

                                }

                                @Override
                                public void onFailure(Call<DataResponse<ProductModel>> call, Throwable t) {
                                    Log.e("ErrorRes", t.getLocalizedMessage());
                                    progress.dismiss();

                                }
                            });
                        } else {
                            Toast.makeText(this, "يرجى تحديد الكمية المجرودة", Toast.LENGTH_LONG).show();
                            quantityin.setError("يرجى تحديد الكمية المجرودة");
                            quantityin.requestFocus();

                        }
                    } else {
                        Toast.makeText(this, "يرجى اختيار التاريخ", Toast.LENGTH_LONG).show();

                    }


                } else {
                    if (!quantity.isEmpty()) {

                        progress.show();

                        new RetrofitCon(this).getService().addproductsodoo("Bearer " +
                                        user.getAccessToken(),
                                location_id,
                                expiry,
                                product_id,
                                productname.getText().toString(),
                                note.getText().toString(),
                                quantity,
                                0,unit_name,unit_id,lot_id,lot_name,odoo_location_id).enqueue(new Callback<DataResponse<ProductModel>>() {
                            @Override
                            public void onResponse(Call<DataResponse<ProductModel>> call, Response<DataResponse<ProductModel>> response) {

                                if (response.isSuccessful()) {
                                    if (response.body().isSuccess()) {

                                        spyear.setEnabled(false);
                                        spday.setEnabled(false);
                                        spmonth.setEnabled(false);

                                        expiry = "";
                                        product_id = 0;
                                        note.setText("");
                                        quantityin.setText("");
                                        barcode.setText("");
                                        productname.setText("");
                                        chdate.setChecked(false);

                                    } else {
                                        Toast.makeText(AddProductPage.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    if (response.errorBody() != null) {
                                        Log.e("ResponseCodActive", response.code() + "/" + expiry + "/" + product_id);
                                        JSONObject jsonObj = null;
                                        try {
                                            jsonObj = new JSONObject(response.errorBody().string());
                                            Log.e("ErrorRes", jsonObj.getString("message"));
                                            Toast.makeText(AddProductPage.this, jsonObj.getString("message"), Toast.LENGTH_LONG).show();

                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                progress.dismiss();

                            }

                            @Override
                            public void onFailure(Call<DataResponse<ProductModel>> call, Throwable t) {
                                Log.e("ErrorRes", t.getLocalizedMessage());
                                progress.dismiss();

                            }
                        });
                    } else {
                        Toast.makeText(this, "يرجى تحديد الكمية المجرودة", Toast.LENGTH_LONG).show();
                        quantityin.setError("يرجى تحديد الكمية المجرودة");
                        quantityin.requestFocus();

                    }
                }

            }
        }
    }

    @Override
    public void productdata(String barcode, int id, String name, int lot,int unit_id,String unit_name, ArrayList<ProductsModel.Lots> lotsArrayList) {

        this.barcode.setText(String.valueOf(barcode));
        this.productname.setText(name);


        this.lot = lot;
        product_id = id;

        this.unit_id = unit_id;
        this.unit_name = unit_name;
//        if (lot == 1) {
        this.lotsArrayList.clear();
        layoutdate.setVisibility(View.VISIBLE);
        chdate.setChecked(true);


        lotsArrayList.add(0, new ProductsModel.Lots("اختر التاريخ المناسب"));
//            lotsArrayList.add(1, new ProductsModel.Lots("انشاء تاريخ جديد"));
        this.lotsArrayList.addAll(lotsArrayList);
        String[] stringArrayList = new String[lotsArrayList.size()];
        for (int i = 0; i < lotsArrayList.size(); i++) {
            Log.e("Error", lotsArrayList.get(i).getName());
            stringArrayList[i] = lotsArrayList.get(i).getName();
        }
        spinner(stringArrayList, spexistdate);

//        } else {
//            layoutdate.setVisibility(View.GONE);
//            chdate.setChecked(false);
//        }

    }

    @Override
    public void productdata(String barcode, int id, String name, int lot,int unit_id,String unit_name) {
        this.barcode.setText(String.valueOf(barcode));
        this.productname.setText(name);

        this.lot = lot;
        product_id = id;
        this.unit_id = unit_id;
        this.unit_name = unit_name;
//        if (lot == 1) {
        layoutdate.setVisibility(View.VISIBLE);
        chdate.setChecked(true);


//        } else {
        layoutdate.setVisibility(View.GONE);
        chdate.setChecked(false);
//        }
    }


    void spinner(String[] arrayString, Spinner spinner) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_list,
                arrayString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}