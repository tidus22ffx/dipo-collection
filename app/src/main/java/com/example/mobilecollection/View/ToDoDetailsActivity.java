package com.example.mobilecollection.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.ToDoDetailsViewModel;
import com.example.mobilecollection.utilities.Utilities;

public class ToDoDetailsActivity extends AppCompatActivity {
    private static final int PIC_FOTO_1 = 1;
    private static final int PIC_FOTO_2 = 2;
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
    AlertDialog saveLoadingDialog, saveCompletedDialog;
    Spinner prioritas, bertemu, statAlamat, statHp, statTelp, followUp, vstResult;
    ToDoDetailsViewModel toDoViewModel;
    ProgressBar loading;
    ScrollView scroll;
    TodoItem todoDetail;
    ImageView foto1, foto2;
    TextView hob, noKontrak, namaCustomer, jatuhTempo, osNet, brand,
            expiredDate, currentStatus, bucket, alamat, mobile, telp, ptp, ptpDate,
            overdue, totalPeriod, uslPaid, unit, currentUnit, jumlahAngsuran, balance, currentBalance,
            ptpAmount, remarkAdmin, alamatPerubahan, telpPerubahan, mobilePerubahan, nopol;
    LinearLayout alamatBaru, telpBaru, hpBaru, resultOtherBaru, bertemuLayout, alamatLayout,
            hpLayout, telpLayout, followUpLayout, visitResultLayout, prioritasLayout, kronologisLayout,
            foto1Layout;
    EditText kronologi, newAlamat, newKelurahan, newKecamatan, newKodya, newKodePos, newHp, newTelp, newOther;
    Button submit, save;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_details);
        initializeFindView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        textView.setText("To Do List Details");
        toolbar.setNavigationIcon(R.drawable.ico_back);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("index");
        toDoViewModel = ViewModelProviders.of(this).get(ToDoDetailsViewModel.class);
        toDoViewModel.fetchDetail(id);

        initializeSpinner();
        initializeObserver();

        statAlamat.setOnItemSelectedListener(myListener);
        statHp.setOnItemSelectedListener(myListener);
        statTelp.setOnItemSelectedListener(myListener);
        vstResult.setOnItemSelectedListener(myListener);

        foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraLaunch = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraLaunch, PIC_FOTO_1);
            }
        });

        foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraLaunch = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraLaunch, PIC_FOTO_2);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldValidation();
//                String text = bertemu.getSelectedItem().toString();
//                String id = Long.toString(bertemu.getSelectedItemId());
//                scroll.smoothScrollTo(0, bertemuLayout.getTop());
//                Toast.makeText(ToDoDetailsActivity.this, "Duarr "+text+" "+id, Toast.LENGTH_SHORT).show();
//                toDoViewModel.saveInputToDB();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoViewModel.saveToPendingDatabase();
            }
        });
    }

    private void fieldValidation() {
        if (prioritas.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, prioritasLayout.getTop());
        } else if (bertemu.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, bertemuLayout.getTop());
        } else if (statAlamat.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, alamatLayout.getTop());
        } else if (statHp.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, hpLayout.getTop());
        } else if (statTelp.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, telpLayout.getTop());
        } else if (followUp.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, followUpLayout.getTop());
        } else if (vstResult.getSelectedItemId() == 0) {
            scroll.smoothScrollTo(0, visitResultLayout.getTop());
        } else if (kronologi.getText().toString().isEmpty()) {
            Toast.makeText(ToDoDetailsActivity.this,
                    "Silahkan Isi Kronolgi Terlebih Dahulu.", Toast.LENGTH_SHORT).show();
        } else if (foto1.getDrawable().getConstantState() ==
                getResources().getDrawable(R.drawable.icon_camera).getConstantState()) {
            Toast.makeText(ToDoDetailsActivity.this,
                    "Silahkan Ambil Foto Terlebih Dahulu.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PIC_FOTO_1) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            foto1.setImageBitmap(image);
        } else if (requestCode == PIC_FOTO_2) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            foto2.setImageBitmap(image);
        }
    }

    private AdapterView.OnItemSelectedListener myListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.status_alamat) {
                if (position == 2) {
                    alamatBaru.setVisibility(View.VISIBLE);
                } else {
                    alamatBaru.setVisibility(View.GONE);
                }
            } else if (parent.getId() == R.id.status_hp) {
                if (position == 2) {
                    hpBaru.setVisibility(View.VISIBLE);
                } else {
                    hpBaru.setVisibility(View.GONE);
                }
            } else if (parent.getId() == R.id.status_telp) {
                if (position == 2) {
                    telpBaru.setVisibility(View.VISIBLE);
                } else {
                    telpBaru.setVisibility(View.GONE);
                }
            } else if (parent.getId() == R.id.visit_result) {
                if (position == 11) {
                    resultOtherBaru.setVisibility(View.VISIBLE);
                } else {
                    resultOtherBaru.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(ToDoDetailsActivity.this,
                    "show nothing", Toast.LENGTH_LONG).show();
        }
    };

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
        kronologi = findViewById(R.id.kronologis);
        alamatBaru = findViewById(R.id.delivered_alamat_baru);
        hpBaru = findViewById(R.id.delivered_hp_baru);
        telpBaru = findViewById(R.id.delivered_telp_baru);
        resultOtherBaru = findViewById(R.id.delivered_result_other_baru);
        newAlamat = findViewById(R.id.delivered_alamat_baru_edittext);
        newKelurahan = findViewById(R.id.delivered_kelurahan_baru_edittext);
        newKecamatan = findViewById(R.id.delivered_kecamatan_baru_edittext);
        newKodya = findViewById(R.id.delivered_kodya_baru_edittext);
        newKodePos = findViewById(R.id.delivered_kodepos_baru_edittext);
        newHp = findViewById(R.id.delivered_hp_baru_edittext);
        newTelp = findViewById(R.id.delivered_telp_baru_edittext);
        newOther = findViewById(R.id.delivered_result_other_edittext);
        foto1 = findViewById(R.id.foto1);
        foto2 = findViewById(R.id.foto2);
        submit = findViewById(R.id.detail_submit_button);
        save = findViewById(R.id.detail_save_button);
        bertemuLayout = findViewById(R.id.bertemu_spinner_layout);
        alamatLayout = findViewById(R.id.statusAlamat_spinner_layout);
        hpLayout = findViewById(R.id.statusHP_spinner_layout);
        telpLayout = findViewById(R.id.statusTelp_spinner_layout);
        followUpLayout = findViewById(R.id.followUp_spinner_layout);
        visitResultLayout = findViewById(R.id.visitResult_spinner_layout);
        prioritasLayout = findViewById(R.id.prioritas_layout);
        kronologisLayout = findViewById(R.id.kronologis_layout);
        foto1Layout = findViewById(R.id.foto1_layout);
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

        toDoViewModel.getSaveLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaving) {
                if(isSaving){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ToDoDetailsActivity.this);
                    saveLoadingDialog = Utilities.setProgressDialog(builder);
                } else {
                    saveLoadingDialog.dismiss();
                }
            }
        });

        toDoViewModel.getSavedPendingTodoItem().observe(this, new Observer<TodoItem>() {
            @Override
            public void onChanged(TodoItem todoItem) {
                if(todoItem != null){
                    buildSaveDialog(todoItem);
                    saveCompletedDialog.show();
                }
            }
        });

        toDoViewModel.getSavedInputTodoItem().observe(this, new Observer<TodoItem>() {
            @Override
            public void onChanged(TodoItem todoItem) {
                if(todoItem != null){
                    buildSaveDialog(todoItem);
                    saveCompletedDialog.show();
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

    private void buildSaveDialog(TodoItem savedItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoDetailsActivity.this);
        builder.setMessage(R.string.pending_dialog + " " + savedItem.getCustomerName())
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        saveCompletedDialog = builder.create();
    }
}
