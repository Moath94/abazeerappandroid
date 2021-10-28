package com.abazeer.abazeerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abazeer.abazeerapp.ui.SearchProductFr;

import butterknife.BindView;

public class AddNewReturn extends AppCompatActivity {
    @BindView(R.id.addnewitem_total)
    TextView addnewitem_total;
    @BindView(R.id.addnewitem_exedt)
    EditText addnewitem_exedt;
    @BindView(R.id.addnewitem_scrapedt)
    EditText addnewitem_scrapedt;
    @BindView(R.id.addnewitem_validedt)
    EditText addnewitem_validedt;
    @BindView(R.id.addnewitem_exsp)
    Spinner expiry_date;
    @BindView(R.id.addnewitem_productedt)
    EditText productename;

    @BindView(R.id.addnewitem_btnrtn)
    Button addnewitem_btnrtn;
    @BindView(R.id.addnewitem_btnadd)
    Button addnewitem_btnadd;

    int return_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_return);


        return_id = getIntent().getIntExtra("return_id",0);
        productename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();

            }
        });
    }
    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SearchProductFr searchProductFr = SearchProductFr.newInstance();


        searchProductFr.show(fm, "fragment_edit_name");


    }
    void getProducts(){

    }
    void addItem(){

    }
}