package com.example.crudmultitable.Features.SelectMhsdanMatul;

import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmultitable.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView namaMhs;
    TextView nimMhs;
    TextView mtkMhs;
    TextView nilaiMhs;


    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        namaMhs = itemView.findViewById(R.id.namaMhs);
        nimMhs = itemView.findViewById(R.id.nimMhs);
        mtkMhs = itemView.findViewById(R.id.mtkMhs);
        nilaiMhs = itemView.findViewById(R.id.nilaiMhs);
    }
}
