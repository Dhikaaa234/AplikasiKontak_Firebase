package com.example.aplikasikontak;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

    private List<Contact> contacts;
    private Context context;
    private OnContactClickListener listener;

    public interface OnContactClickListener {
        void onEditClick(Contact contact);
        void onDeleteClick(Contact contact);
    }

    public AdapterRecyclerView(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
        this.listener = (OnContactClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        holder.textName.setText(contact.getName());
        holder.textNumber.setText(contact.getPhoneNumber());
        holder.imageIcon.setImageResource(contact.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailKontakActivity.class);
            intent.putExtra("id", contact.getId());
            intent.putExtra("name", contact.getName());
            intent.putExtra("phone", contact.getPhoneNumber());
            context.startActivity(intent);
        });

        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
            context.startActivity(intent);
        });

        holder.btnSMS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + contact.getPhoneNumber()));
            context.startActivity(intent);
        });

        holder.btnWA.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/" + contact.getPhoneNumber()));
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "WhatsApp tidak terinstall", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textNumber;
        ImageView imageIcon;
        Button btnCall, btnSMS, btnWA;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.nama);
            textNumber = itemView.findViewById(R.id.kontak);
            imageIcon = itemView.findViewById(R.id.imageList);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnSMS = itemView.findViewById(R.id.btnSMS);
            btnWA = itemView.findViewById(R.id.btnWA);
        }
    }
}