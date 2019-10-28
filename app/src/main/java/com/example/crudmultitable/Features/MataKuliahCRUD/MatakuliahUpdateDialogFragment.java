package com.example.crudmultitable.Features.MataKuliahCRUD;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.crudmultitable.Database.DatabaseQueryClass;
import com.example.crudmultitable.R;
import com.example.crudmultitable.Util.Config;

public class MatakuliahUpdateDialogFragment extends DialogFragment {

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button updateButton;
    private Button cancelButton;

    private static MatakuliahUpdateListener matakuliahUpdateListener;
    private static long subjectId;
    private static int position;

    private DatabaseQueryClass databaseQueryClass;

    public MatakuliahUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static MatakuliahUpdateDialogFragment newInstance(long subId, int pos, MatakuliahUpdateListener listener){
        subjectId = subId;
        position = pos;
        matakuliahUpdateListener= listener;

        MatakuliahUpdateDialogFragment matakuliahUpdateDialogFragment = new MatakuliahUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update subject information");
        matakuliahUpdateDialogFragment.setArguments(args);

        matakuliahUpdateDialogFragment.setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return matakuliahUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_update_dialog, container, false);

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        Matakuliah subject = databaseQueryClass.getMatakuliahById(subjectId);

        subjectNameEditText.setText(subject.getName());
        subjectCodeEditText.setText(String.valueOf(subject.getCode()));
        subjectCreditEditText.setText(String.valueOf(subject.getCredit()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = subjectNameEditText.getText().toString();
                int subjectCode = Integer.parseInt(subjectCodeEditText.getText().toString());
                double subjectCredit = Double.parseDouble(subjectCreditEditText.getText().toString());

                Matakuliah subject = new Matakuliah(subjectId, subjectName, subjectCode, subjectCredit);

                long rowCount = databaseQueryClass.updateMatakuliahInfo(subject);

                if(rowCount>0){
                    matakuliahUpdateListener.onMatakuliahInfoUpdate(subject, position);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }
}
