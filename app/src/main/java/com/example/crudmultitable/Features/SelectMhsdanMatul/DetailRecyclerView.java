package com.example.crudmultitable.Features.SelectMhsdanMatul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmultitable.Features.MataKuliahCRUD.Matakuliah;
import com.example.crudmultitable.R;

import java.util.List;

public class DetailRecyclerView extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<MahasiswaDetail> mahasiswaDetailList;

    public DetailRecyclerView(Context context, List<MahasiswaDetail> mahasiswaDetails) {
        this.context = context;
        this.mahasiswaDetailList = mahasiswaDetails;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final int listPosition = position;
        final MahasiswaDetail mahasiswaDetail = mahasiswaDetailList.get(position);
        holder.namaMhs.setText(mahasiswaDetail.getName());
        holder.nimMhs.setText(String.valueOf(mahasiswaDetail.getNim()));
        holder.mtkMhs.setText(mahasiswaDetail.getNamemtk());
        holder.nilaiMhs.setText(String.valueOf(mahasiswaDetail.getCredit()));
    }

    @Override
    public int getItemCount() {
        return mahasiswaDetailList.size();
    }
}
