package com.example.aplikasikontak;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailKontakActivity extends AppCompatActivity {

    private TextView tvName, tvNumber;
    private ImageView ivIcon;
    private Button btnCall, btnSMS, btnWA, btnEdit, btnDelete;
    private String contactId, contactName, contactPhone;
    private DatabaseReference contactRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kontak);

        mAuth = FirebaseAuth.getInstance();
        contactId = getIntent().getStringExtra("id");
        contactName = getIntent().getStringExtra("name");
        contactPhone = getIntent().getStringExtra("phone");

        contactRef = FirebaseDatabase.getInstance().getReference("contacts")
                .child(mAuth.getCurrentUser().getUid())
                .child(contactId);

        initViews();
        setupButtons();
    }

    private void initViews() {
        tvName = findViewById(R.id.tv_detail_name);
        tvNumber = findViewById(R.id.tv_detail_number);
        ivIcon = findViewById(R.id.iv_detail_icon);
        btnCall = findViewById(R.id.btn_detail_call);
        btnSMS = findViewById(R.id.btn_detail_sms);
        btnWA = findViewById(R.id.btn_detail_wa);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        tvName.setText(contactName);
        tvNumber.setText(contactPhone);
        ivIcon.setImageResource(R.drawable.a);
    }

    private void setupButtons() {
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactPhone));
            startActivity(intent);
        });

        btnSMS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + contactPhone));
            startActivity(intent);
        });

        btnWA.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/" + contactPhone));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "WhatsApp tidak terinstall", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, TambahDataActivity.class);
            intent.putExtra("id", contactId);
            intent.putExtra("name", contactName);
            intent.putExtra("phone", contactPhone);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Hapus Kontak")
                    .setMessage("Yakin ingin menghapus kontak ini?")
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        contactRef.removeValue()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Kontak dihapus", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }
}