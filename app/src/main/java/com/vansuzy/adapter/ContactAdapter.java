package com.vansuzy.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vansuzy.baigiang34_projectthucte_projectcontactmanager.NhanTinSMSActivity;
import com.vansuzy.baigiang34_projectthucte_projectcontactmanager.R;
import com.vansuzy.model.Contact;

import java.util.List;

/**
 * Created by keeps on 5/29/2017.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    Activity context;
    int resource;
    List<Contact> objects;

    public ContactAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtPhone = (TextView) row.findViewById(R.id.txtPhone);
        ImageButton btnCall = (ImageButton) row.findViewById(R.id.btnCall);
        ImageButton btnSMS = (ImageButton) row.findViewById(R.id.btnSMS);
        ImageButton btnDelete = (ImageButton) row.findViewById(R.id.btnDelete);

        final Contact contact = this.objects.get(position); // Biến local mà được truy xuất trong Anomous listener thì nó phải khai báo là final
        txtTen.setText(contact.getName());
        txtPhone.setText(contact.getPhone());

        // 1
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCall(contact);
            }
        });

        // 2
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySMS(contact);
            }
        });

        // 3
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXoa(contact);
            }
        });

        return row;
    }

    // 9
    private void xuLyXoa(Contact contact) {
        this.remove(contact);
    }

    // 7
    private void xuLySMS(Contact contact) {
        Intent intent = new Intent(this.context, NhanTinSMSActivity.class);
        intent.putExtra("CONTACT", contact);
        this.context.startActivity(intent);
    }

    // 5
    private void xuLyCall(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + contact.getPhone());
        intent.setData(uri);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.context.startActivity(intent);
    }
}
