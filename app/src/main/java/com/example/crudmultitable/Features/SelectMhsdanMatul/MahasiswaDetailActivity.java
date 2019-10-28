package com.example.crudmultitable.Features.SelectMhsdanMatul;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.crudmultitable.Database.DatabaseQueryClass;
import com.example.crudmultitable.Features.MahasiswaCRUD.MahasiswaListRecyclerViewAdapter;
import com.example.crudmultitable.Features.MataKuliahCRUD.Matakuliah;
import com.example.crudmultitable.Features.MataKuliahCRUD.MatakuliahListRecyclerViewAdapter;
import com.example.crudmultitable.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MahasiswaDetailActivity extends AppCompatActivity {
    private long studentRegNo;

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<MahasiswaDetail> details = new ArrayList<>();
    private RecyclerView recyclerView;
    private DetailRecyclerView detailRecyclerView;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        TextView subjectListEmptyTextView = findViewById(R.id.emptyListTextView);
        subjectListEmptyTextView.setVisibility(View.GONE);

        details.addAll(databaseQueryClass.getDetail());

        detailRecyclerView = new DetailRecyclerView(this, details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(detailRecyclerView);

    }

}
