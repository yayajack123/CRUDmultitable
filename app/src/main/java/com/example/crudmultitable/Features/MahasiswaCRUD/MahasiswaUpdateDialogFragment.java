package com.example.crudmultitable.Features.MahasiswaCRUD;

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

public class MahasiswaUpdateDialogFragment extends DialogFragment {
    private static long studentRegNo;
    private static int studentItemPosition;
    private static MahasiswaUpdateListener mahasiswaUpdateListener;

    private Mahasiswa mMahasiswa;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private long registrationNumber = -1;
    private String phoneString = "";
    private String emailString = "";

    private DatabaseQueryClass databaseQueryClass;

    public MahasiswaUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static MahasiswaUpdateDialogFragment newInstance(long registrationNumber, int position, MahasiswaUpdateListener listener){
        studentRegNo = registrationNumber;
        studentItemPosition = position;
        mahasiswaUpdateListener = listener;
        MahasiswaUpdateDialogFragment studentUpdateDialogFragment = new MahasiswaUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Informasi Mahasiswa");
        studentUpdateDialogFragment.setArguments(args);

        studentUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.studentNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        updateButton = view.findViewById(R.id.updateStudentInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mMahasiswa = databaseQueryClass.getMahasiswaByRegNum(studentRegNo);

        if(mMahasiswa!=null){
            nameEditText.setText(mMahasiswa.getName());
            registrationEditText.setText(String.valueOf(mMahasiswa.getNim()));
            phoneEditText.setText(mMahasiswa.getPhoneNumber());
            emailEditText.setText(mMahasiswa.getEmail());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = nameEditText.getText().toString();
                    registrationNumber = Integer.parseInt(registrationEditText.getText().toString());
                    phoneString = phoneEditText.getText().toString();
                    emailString = emailEditText.getText().toString();

                    mMahasiswa.setName(nameString);
                    mMahasiswa.setNim(registrationNumber);
                    mMahasiswa.setPhoneNumber(phoneString);
                    mMahasiswa.setEmail(emailString);

                    long id = databaseQueryClass.updateMahasiswaInfo(mMahasiswa);

                    if(id>0){
                        mahasiswaUpdateListener.onMahasiswaInfoUpdated(mMahasiswa, studentItemPosition);
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

        }

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
