package com.example.mobilecollection.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.DeliveredViewModel;

public class DeliveredDetailsActivity extends AppCompatActivity {

    DeliveredViewModel viewModel;
    TodoItem todoDetail;
    Spinner prioritas;
    TextView hobAction;
    TextView noContract;
    TextView namaCustomer;
    TextView tglJatuhTempo;
    TextView overdue;
    TextView receivable;
    TextView totalPeriod;
    TextView paid;
    TextView unit;
    TextView brand;
    TextView currentUnit;
    TextView expiredDate;
    TextView status;
    TextView jumlahAngsuran;
    TextView balance;
    TextView bucket;
    TextView alamat;
    TextView telp;
    TextView mobile;
    TextView currentBalance;
    TextView ptp;
    TextView ptpDate;
    TextView ptpAmount;
    TextView remark;
    TextView alamatPerubahan;
    TextView mobilePerubahan;
    TextView telpPerubahan;
    TextView noPlat;
    Spinner bertemuDengan;
    Spinner statusAlamat;
    Spinner statusHp;
    Spinner statusTelp;
    Spinner followupType;
    Spinner visitResult;
    EditText kronologis;
    ImageView foto1;
    ImageView foto2;
    Button savePriority, save, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        textView.setText("Delivered Detail");

        prioritas = findViewById(R.id.prioritas_spinner);
        hobAction = findViewById(R.id.delivered_hob_action);
        noContract = findViewById(R.id.delivered_contract_no);
        namaCustomer = findViewById(R.id.delivered_nama_customer);
        tglJatuhTempo = findViewById(R.id.delivered_jatuh_tempo);
        overdue = findViewById(R.id.delivered_overdue);
        receivable = findViewById(R.id.delivered_receivable);
        totalPeriod = findViewById(R.id.delivered_total_period);
        paid = findViewById(R.id.delivered_paid);
        unit = findViewById(R.id.delivered_unit);
        brand = findViewById(R.id.delivered_brand);
        currentUnit = findViewById(R.id.delivered_current_unit);
        expiredDate = findViewById(R.id.delivered_expired_date);
        status = findViewById(R.id.delivered_status);
        jumlahAngsuran = findViewById(R.id.delivered_jumlah_angsuran);
        balance = findViewById(R.id.delivered_balance);
        bucket = findViewById(R.id.delivered_bucket);
        alamat = findViewById(R.id.delivered_alamat);
        telp = findViewById(R.id.delivered_telp);
        mobile = findViewById(R.id.delivered_mobile);
        currentBalance = findViewById(R.id.delivered_current_balance);
        ptp = findViewById(R.id.delivered_ptp);
        ptpDate = findViewById(R.id.delivered_ptp_date);
        ptpAmount = findViewById(R.id.delivered_ptp_amount);
        remark = findViewById(R.id.delivered_remark);
        alamatPerubahan =findViewById(R.id.delivered_remark);
        telpPerubahan = findViewById(R.id.delivered_telp_perubahan);
        mobilePerubahan = findViewById(R.id.delivered_mobile_perubahan);
        noPlat = findViewById(R.id.delivered_plat);
        bertemuDengan = findViewById(R.id.bertemu_dengan);
        statusAlamat = findViewById(R.id.status_alamat);
        statusHp = findViewById(R.id.status_hp);
        statusTelp = findViewById(R.id.status_telp);
        followupType = findViewById(R.id.followup_type);
        visitResult = findViewById(R.id.visit_result);
        kronologis = findViewById(R.id.kronologis);
        foto1 = findViewById(R.id.foto1);
        foto2 = findViewById(R.id.foto2);
        savePriority = findViewById(R.id.save_prioritas_button);
        save = findViewById(R.id.detail_save_button);
        submit = findViewById(R.id.detail_submit_button);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(DeliveredViewModel.class);

        observeViewModel();
    }

    void observeViewModel(){
        viewModel.getTodoDetail().observe(this, new Observer<TodoItem>() {
            @Override
            public void onChanged(TodoItem todoItem) {
                if(todoItem != null){
                    namaCustomer.setText(todoItem.getCustomerName());
                    noContract.setText(todoItem.getContractNo());
                }
            }
        });
    }
}
