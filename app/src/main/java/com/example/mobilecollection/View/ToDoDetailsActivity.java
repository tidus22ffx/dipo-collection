package com.example.mobilecollection.View;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.ToDoViewModel;

public class ToDoDetailsActivity extends AppCompatActivity {
    String[] priority = { "Pilih Prioritas", "Rendah", "Tinggi" };
    String[] sesuai = { "Pilih Salah Satu", "Sesuai", "Tidak Sesuai" };
    String[] meet = { "Pilih Salah Satu", "Lessee", "Suami", "Istri", "Anak",
                        "Orang Tua", "Anggota Keluarga Lainnya", "Karyawan", "Pembantu", "Tidak Bertemu Siapapun",
                        "Pihak Ketiga"};
    String[] tipeFollowUp = { "Pilih Salah Satu", "Site Visit", "Phone Call" };
    String[] visitResult = { "Pilih Salah Satu", "OL Dalam Pelacakan", "PTP", "Proses Write Off",
                            "OL Berhasil Ditarik", "Legal Case", "Klaim Asuransi Namun Direject",
                            "Klaim Asuransi", "Deadlock (OL sudah 3 bln dalam pelacakan)", "Kontrak Tidak Difollow Up / Kurang Personil",
                            "Lessee Melakukan Pembayaran", "Lainnya" };

    Spinner prioritas, bertemu, statAlamat, statHp, statTelp, followUp, vstResult;
    ToDoViewModel toDoViewModel;
    ProgressBar loading;
    ScrollView scroll;
    TodoItem todoDetail;
    TextView hob, noKontrak, namaCustomer, jatuhTempo, osNet, brand,
            expiredDate, currentStatus, bucket, alamat, mobile, telp, ptp, ptpDate,
            overdue, totalPeriod, uslPaid, unit, currentUnit, jumlahAngsuran, balance, currentBalance,
            ptpAmount, remarkAdmin, alamatPerubahan, telpPerubahan, mobilePerubahan, nopol;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_details);
        initializeFindView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        textView.setText("To Do List Details");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("index");
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.fetchDetail(id);

        initializeSpinner();
        initializeObserver();
    }

    private void initializeFindView() {
        prioritas = findViewById(R.id.prioritas_spinner);
        bertemu = findViewById(R.id.bertemu_dengan);
        statAlamat = findViewById(R.id.status_alamat);
        statHp = findViewById(R.id.status_hp);
        statTelp = findViewById(R.id.status_telp);
        followUp = findViewById(R.id.followup_type);
        vstResult = findViewById(R.id.visit_result);
        loading = findViewById(R.id.details_loader);
        scroll = findViewById(R.id.detail_scroll);
        hob = findViewById(R.id.delivered_hob_action);
        noKontrak = findViewById(R.id.delivered_no_kontrak);
        namaCustomer = findViewById(R.id.delivered_nama_customer);
        jatuhTempo = findViewById(R.id.delivered_jatuh_tempo);
        overdue = findViewById(R.id.delivered_overdue);
        osNet = findViewById(R.id.delivered_receivable);
        totalPeriod = findViewById(R.id.delivered_total_period);
        uslPaid = findViewById(R.id.delivered_paid);
        unit = findViewById(R.id.delivered_unit);
        brand = findViewById(R.id.delivered_brand);
        currentUnit = findViewById(R.id.delivered_current_unit);
        expiredDate = findViewById(R.id.delivered_expired_date);
        currentStatus = findViewById(R.id.delivered_status);
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
        remarkAdmin = findViewById(R.id.delivered_remark);
        alamatPerubahan = findViewById(R.id.delivered_alamat_perubahan);
        telpPerubahan = findViewById(R.id.delivered_telp_perubahan);
        mobilePerubahan = findViewById(R.id.delivered_mobile_perubahan);
        nopol = findViewById(R.id.delivered_plat);
    }


    private void initializeObserver() {
        toDoViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loading.setVisibility(View.VISIBLE);
                    scroll.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.GONE);
                }
            }
        });

        toDoViewModel.getToDoDetail().observe(this, new Observer<TodoItem>() {
            @Override
            public void onChanged(TodoItem todoItem) {
                if(todoItem != null) {
                    todoDetail = todoItem;
                    scroll.setVisibility(View.VISIBLE);
                    initializeDetail(todoDetail);
                }
            }
        });
    }

    private void initializeDetail(TodoItem todoItem) {
        hob.setText(todoItem.getHobAction());
        noKontrak.setText(todoItem.getContractNo());
        namaCustomer.setText(todoItem.getCustomerName());
        jatuhTempo.setText(todoItem.getExpiredDate());
        overdue.setText(Integer.toString(todoItem.getOverdue()));
        osNet.setText(todoItem.getReceivable());
        totalPeriod.setText(todoItem.getTotalPeriod());
        uslPaid.setText(todoItem.getPaid());
        unit.setText(todoItem.getUnit());
        brand.setText(todoItem.getBrand());
        currentUnit.setText(todoItem.getCurrentUnit());
        expiredDate.setText(todoItem.getExpiredDate());
        currentStatus.setText(todoItem.getStatus());;
        jumlahAngsuran.setText(todoItem.getAngsuran());
        balance.setText(todoItem.getBalance());
        bucket.setText(todoItem.getBucket());
        alamat.setText(todoItem.getAlamat());
        telp.setText(todoItem.getTelp());
        mobile.setText(todoItem.getMobile());
        currentBalance.setText(todoItem.getBalance());
        ptp.setText(todoItem.getPaid());
        ptpDate.setText(todoItem.getOrderDate());
        ptpAmount.setText(todoItem.getAngsuran());
        remarkAdmin.setText(todoItem.getStatus());
        alamatPerubahan.setText(todoItem.getAlamat());
        telpPerubahan.setText(todoItem.getTelp());
        mobilePerubahan.setText(todoItem.getMobile());
        nopol.setText(todoItem.getPlat());
    }

    private void initializeSpinner() {
        ArrayAdapter<String> prioritasSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priority);
        prioritasSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        prioritas.setAdapter(prioritasSpinner);

        ArrayAdapter<String> bertemuSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meet);
        bertemuSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        bertemu.setAdapter(bertemuSpinner);

        ArrayAdapter<String> statAlamatSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesuai);
        statAlamatSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statAlamat.setAdapter(statAlamatSpinner);

        ArrayAdapter<String> statHpSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesuai);
        statHpSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statHp.setAdapter(statHpSpinner);

        ArrayAdapter<String> statTelpSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesuai);
        statTelpSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statTelp.setAdapter(statTelpSpinner);

        ArrayAdapter<String> followUpSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipeFollowUp);
        followUpSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        followUp.setAdapter(followUpSpinner);

        ArrayAdapter<String> visitResultSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visitResult);
        visitResultSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        vstResult.setAdapter(visitResultSpinner);
    }
}
