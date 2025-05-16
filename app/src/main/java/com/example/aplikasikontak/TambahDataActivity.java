package com.example.aplikasikontak;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahDataActivity extends AppCompatActivity {

    private EditText etName, etNumber;
    private Button btnSave;
    private DatabaseReference contactsRef;
    private FirebaseAuth mAuth;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data);

        mAuth = FirebaseAuth.getInstance();
        contactsRef = FirebaseDatabase.getInstance().getReference("contacts")
                .child(mAuth.getCurrentUser().getUid());

        etName = findViewById(R.id.et_name);
        etNumber = findViewById(R.id.et_number);
        btnSave = findViewById(R.id.btn_save);


        contactId = getIntent().getStringExtra("id");
        if (contactId != null) {
            etName.setText(getIntent().getStringExtra("name"));
            etNumber.setText(getIntent().getStringExtra("phone"));
            setTitle("Edit Kontak");
        } else {
            setTitle("Tambah Kontak");
        }

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact(name, number, R.drawable.a);

        if (contactId != null) {
            // Update kontak
            contactsRef.child(contactId).setValue(contact)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Kontak diperbarui", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        } else {
            // Tambah kontak baru
            contactsRef.push().setValue(contact)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Kontak ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        }
    }
}