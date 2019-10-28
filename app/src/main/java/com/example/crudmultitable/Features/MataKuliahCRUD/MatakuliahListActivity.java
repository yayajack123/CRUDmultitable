package com.example.crudmultitable.Features.MataKuliahCRUD;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmultitable.Database.DatabaseQueryClass;
import com.example.crudmultitable.R;
import com.example.crudmultitable.Util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatakuliahListActivity extends AppCompatActivity implements MatakuliahCreateListener {

    private long studentRegNo;

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Matakuliah> subjectList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView subjectListEmptyTextView;
    private RecyclerView recyclerView;
    private MatakuliahListRecyclerViewAdapter matakuliahListRecyclerViewAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        subjectListEmptyTextView = findViewById(R.id.emptyListTextView);
        summaryTextView = findViewById(R.id.summaryTextView);
        studentRegNo = getIntent().getLongExtra(Config.MAHASISWA_REGISTRATION, -1);

        subjectList.addAll(databaseQueryClass.getAllMatakuliahByRegNo(studentRegNo));

        matakuliahListRecyclerViewAdapter = new MatakuliahListRecyclerViewAdapter(this, subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(matakuliahListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSubjectCreateDialog();
            }
        });
    }

    private void printSummary() {
        long studentNum = databaseQueryClass.getNumberOfStudent();
        long subjectNum = databaseQueryClass.getNumberOfMatakuliah();

        summaryTextView.setText(getResources().getString(R.string.database_summary, subjectNum));
    }

    private void openSubjectCreateDialog() {
        MatakuliahCreateDialogFragment matakuliahCreateDialogFragment = MatakuliahCreateDialogFragment.newInstance(studentRegNo, this);
        matakuliahCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_MATAKULIAH);
    }


    public void viewVisibility() {
        if(subjectList.isEmpty())
            subjectListEmptyTextView.setVisibility(View.VISIBLE);
        else
            subjectListEmptyTextView.setVisibility(View.GONE);
        printSummary();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete all subjects?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                boolean isAllDeleted = databaseQueryClass.deleteAllMatakuliahByRegNum(studentRegNo);
                                if(isAllDeleted){
                                    subjectList.clear();
                                    matakuliahListRecyclerViewAdapter.notifyDataSetChanged();
                                    viewVisibility();
                                }
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
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMatakuliahCreated(Matakuliah matakuliah) {
        subjectList.add(matakuliah);
        matakuliahListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
    }
}
