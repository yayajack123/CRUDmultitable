package com.example.crudmultitable.Features.MataKuliahCRUD;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmultitable.Database.DatabaseQueryClass;
import com.example.crudmultitable.R;
import com.example.crudmultitable.Util.Config;

import java.util.List;

public class MatakuliahListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {


    private Context context;
    private List<Matakuliah> subjectList;

    public MatakuliahListRecyclerViewAdapter(Context context, List<Matakuliah> matakuliahList) {
        this.context = context;
        this.subjectList = matakuliahList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        final int listPosition = position;
        final Matakuliah matakuliah = subjectList.get(position);

        holder.subjectNameTextView.setText(matakuliah.getName());
        holder.subjectCodeTextView.setText(String.valueOf(matakuliah.getCode()));
        holder.subjectCreditTextView.setText(String.valueOf(matakuliah.getCredit()));

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this subject?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteSubject(matakuliah);
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
                editSubject(matakuliah.getId(), listPosition);
            }
        });
    }

    private void editSubject(long subjectId, int listPosition){
        MatakuliahUpdateDialogFragment matakuliahUpdateDialogFragment = MatakuliahUpdateDialogFragment.newInstance(subjectId, listPosition, new MatakuliahUpdateListener() {
            @Override
            public void onMatakuliahInfoUpdate(Matakuliah matakuliah, int position) {
                subjectList.set(position, matakuliah);
                notifyDataSetChanged();
            }
        });
        matakuliahUpdateDialogFragment.show(((MatakuliahListActivity) context).getSupportFragmentManager(), Config.UPDATE_MATAKULIAH);
    }

    private void deleteSubject(Matakuliah matakuliah) {
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteMatakuliahById(matakuliah.getId());

        if(isDeleted) {
            subjectList.remove(matakuliah);
            notifyDataSetChanged();
            ((MatakuliahListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
