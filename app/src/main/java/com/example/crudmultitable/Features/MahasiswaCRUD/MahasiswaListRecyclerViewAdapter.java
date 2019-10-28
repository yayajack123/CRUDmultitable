package com.example.crudmultitable.Features.MahasiswaCRUD;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmultitable.Database.DatabaseQueryClass;
import com.example.crudmultitable.Features.MataKuliahCRUD.Matakuliah;
import com.example.crudmultitable.Features.MataKuliahCRUD.MatakuliahListActivity;
import com.example.crudmultitable.R;
import com.example.crudmultitable.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class MahasiswaListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Mahasiswa> mahasiswaList;
    private DatabaseQueryClass databaseQueryClass;

    public MahasiswaListRecyclerViewAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        final int itemPosition = position;
        final Mahasiswa mahasiswa = mahasiswaList.get(position);

        holder.nameTextView.setText(mahasiswa.getName());
        holder.registrationNumTextView.setText(String.valueOf(mahasiswa.getNim()));
        holder.emailTextView.setText(mahasiswa.getEmail());
        holder.phoneTextView.setText(mahasiswa.getPhoneNumber());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this mahasiswa?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteMahasiswa(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MahasiswaUpdateDialogFragment mahasiswaUpdateDialogFragment = MahasiswaUpdateDialogFragment.newInstance(mahasiswa.getNim(),
                        itemPosition, new MahasiswaUpdateListener() {
                    @Override
                    public void onMahasiswaInfoUpdated(Mahasiswa mahasiswa, int position) {
                        mahasiswaList.set(position, mahasiswa);
                        notifyDataSetChanged();
                    }
                });
                mahasiswaUpdateDialogFragment.show(((MahasiswaListActivity) context).getSupportFragmentManager(), Config.UPDATE_MAHASISWA);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MatakuliahListActivity.class);
                intent.putExtra(Config.MAHASISWA_REGISTRATION, mahasiswa.getNim());
                context.startActivity(intent);
            }
        });
    }

    private void deleteMahasiswa(int position) {
        Mahasiswa mahasiswa = mahasiswaList.get(position);
        long count = databaseQueryClass.deleteMahasiswaByRegNum(mahasiswa.getNim());

        if(count>0){
            mahasiswaList.remove(position);
            notifyDataSetChanged();
            ((MahasiswaListActivity) context).viewVisibility();
            Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Student not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }
}
