package com.example.mobilecollection.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.DeliveredDetailsViewModel;
import com.example.mobilecollection.utilities.Utilities;

public class DeliveredDetailsActivity extends AppCompatActivity {

    ScrollView fieldContainer;
    DeliveredDetailsViewModel viewModel;
    ProgressBar loading;
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

    AlertDialog saveLoadingDialog, saveCompletedDialog;

    String[] priority = { "Pilih Prioritas", "Rendah", "Normal", "Tinggi" };
    String[] sesuai = { "Pilih Salah Satu", "Sesuai", "Tidak Sesuai" };
    String[] meet = { "Pilih Salah Satu", "Lessee", "Suami", "Istri", "Anak",
            "Orang Tua", "Anggota Keluarga Lainnya", "Karyawan", "Pembantu", "Tidak Bertemu Siapapun",
            "Pihak Ketiga"};
    String[] tipeFollowUp = { "Pilih Salah Satu", "Site Visit", "Phone Call" };
    String[] visitResultArr = { "Pilih Salah Satu", "OL Dalam Pelacakan", "PTP", "Proses Write Off",
            "OL Berhasil Ditarik", "Legal Case", "Klaim Asuransi Namun Direject",
            "Klaim Asuransi", "Deadlock (OL sudah 3 bln dalam pelacakan)", "Kontrak Tidak Difollow Up / Kurang Personil",
            "Lessee Melakukan Pembayaran", "Lainnya" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        textView.setText("Delivered Detail");

        initializeView();
        initializeSpinner();
        disableForm();

        toolbar.setNavigationIcon(R.drawable.ico_back);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        int id = getIntent().getIntExtra("detailId", 0 );

        viewModel = ViewModelProviders.of(this).get(DeliveredDetailsViewModel.class);
        viewModel.fetchDetails(id);

        observeViewModel();
    }

    void observeViewModel(){
        viewModel.getTodoDetail().observe(this, new Observer<TodoItem>() {
            @Override
            public void onChanged(TodoItem todoItem) {
                if(todoItem != null){
                    setViewData(todoItem);
                    fieldContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loading.setVisibility(View.VISIBLE);
                    fieldContainer.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.GONE);
                    fieldContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getSaveLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaving) {
                if(isSaving){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveredDetailsActivity.this);
                    saveLoadingDialog = Utilities.setProgressDialog(builder);
                } else {
                    saveLoadingDialog.dismiss();
                }
            }
        });

        viewModel.getSavedTodoItem().observe(this, new Observer<TodoItem>() {
            @Override
            public void onChanged(TodoItem todoItem) {
                if(todoItem != null){
                    buildSaveDialog(todoItem);
                    saveCompletedDialog.show();
                }
            }
        });
    }

    private void buildSaveDialog(TodoItem savedItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeliveredDetailsActivity.this);
        builder.setMessage(R.string.pending_dialog + " " + savedItem.getCustomerName())
           .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(getApplicationContext(), DeliveredActivity.class);
//                    startActivity(intent);
                    finish();
               }
           });

        saveCompletedDialog = builder.create();
    }

    private void setViewData(TodoItem todoItem){
        hobAction.setText(todoItem.getHobAction());
        namaCustomer.setText(todoItem.getCustomerName());
        noContract.setText(todoItem.getContractNo());
        prioritas.setSelection(todoItem.getPrioritas());
        tglJatuhTempo.setText(todoItem.getTglJatuhTempo());
        overdue.setText(Integer.toString(todoItem.getOverdue()));
        receivable.setText(todoItem.getReceivable());
        totalPeriod.setText(todoItem.getTotalPeriod());
        paid.setText(todoItem.getPaid());
        unit.setText(todoItem.getUnit());
        brand.setText(todoItem.getBrand());
        currentUnit.setText(todoItem.getCurrentUnit());
        expiredDate.setText((todoItem.getExpiredDate()));
        status.setText(todoItem.getStatus());
        jumlahAngsuran.setText(todoItem.getAngsuran());
        balance.setText(todoItem.getBalance());
        bucket.setText(todoItem.getBucket());
        alamat.setText(todoItem.getAlamat());
        telp.setText(todoItem.getTelp());
        mobile.setText(todoItem.getMobile());
        currentBalance.setText(todoItem.getCurrentBalance());
        ptp.setText(todoItem.getPtp());
        ptpDate.setText(todoItem.getPtpDate());
        ptpAmount.setText(Utilities.format(todoItem.getPtpAmount()));
        remark.setText(todoItem.getRemark());
        alamatPerubahan.setText(todoItem.getAlamatPerubahan());
        telpPerubahan.setText(todoItem.getTelpPerubahan());
        mobilePerubahan.setText(todoItem.getMobilePerubahan());
        noPlat.setText(todoItem.getPlat());
        bertemuDengan.setSelection(todoItem.getBertemuDengan());
        prioritas.setSelection(todoItem.getPrioritas());
        statusAlamat.setSelection(todoItem.getStatusAlamat());
        statusTelp.setSelection(todoItem.getStatusTelp());
        statusHp.setSelection(todoItem.getStatusHp());
        followupType.setSelection(todoItem.getFollowupType());
        visitResult.setSelection(todoItem.getVisitResult());
        kronologis.setText(todoItem.getKronologis());

        Glide.with(this)
            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.icon_camera))
            .load(todoItem.getFoto1()).into(foto1);
        Glide.with(this)
            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.icon_camera))
            .load(todoItem.getFoto2()).into(foto2);
    }

    private void initializeSpinner() {
        ArrayAdapter<String> prioritasSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priority);
        prioritasSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        prioritas.setAdapter(prioritasSpinner);

        ArrayAdapter<String> bertemuSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meet);
        bertemuSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        bertemuDengan.setAdapter(bertemuSpinner);

        ArrayAdapter<String> statAlamatSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesuai);
        statAlamatSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statusAlamat.setAdapter(statAlamatSpinner);

        ArrayAdapter<String> statHpSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesuai);
        statHpSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statusHp.setAdapter(statHpSpinner);

        ArrayAdapter<String> statTelpSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesuai);
        statTelpSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        statusTelp.setAdapter(statTelpSpinner);

        ArrayAdapter<String> followUpSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipeFollowUp);
        followUpSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        followupType.setAdapter(followUpSpinner);

        ArrayAdapter<String> visitResultSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visitResultArr);
        visitResultSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        visitResult.setAdapter(visitResultSpinner);
    }

    private void initializeView(){
        prioritas = findViewById(R.id.prioritas_spinner);
        hobAction = findViewById(R.id.delivered_hob_action);
        noContract = findViewById(R.id.delivered_no_kontrak);
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
        alamatPerubahan =findViewById(R.id.delivered_alamat_perubahan);
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
        fieldContainer = findViewById(R.id.detail_scroll);
        loading = findViewById(R.id.details_loader);
    }

    private void disableForm(){
        hobAction.setEnabled(false);
        namaCustomer.setEnabled(false);
        noContract.setEnabled(false);
        prioritas.setEnabled(false);
        tglJatuhTempo.setEnabled(false);
        overdue.setEnabled(false);
        receivable.setEnabled(false);
        totalPeriod.setEnabled(false);
        paid.setEnabled(false);
        unit.setEnabled(false);
        brand.setEnabled(false);
        currentUnit.setEnabled(false);
        expiredDate.setEnabled(false);
        status.setEnabled(false);
        jumlahAngsuran.setEnabled(false);
        balance.setEnabled(false);
        bucket.setEnabled(false);
        alamat.setEnabled(false);
        telp.setEnabled(false);
        mobile.setEnabled(false);
        currentBalance.setEnabled(false);
        ptp.setEnabled(false);
        ptpDate.setEnabled(false);
        ptpAmount.setEnabled(false);
        remark.setEnabled(false);
        alamatPerubahan.setEnabled(false);
        telpPerubahan.setEnabled(false);
        mobilePerubahan.setEnabled(false);
        noPlat.setEnabled(false);
        bertemuDengan.setEnabled(false);
        prioritas.setEnabled(false);
        statusAlamat.setEnabled(false);
        statusTelp.setEnabled(false);
        statusHp.setEnabled(false);
        followupType.setEnabled(false);
        visitResult.setEnabled(false);
        kronologis.setEnabled(false);
        submit.setEnabled(false);
        save.setEnabled(false);
        savePriority.setEnabled(false);
    }
}
