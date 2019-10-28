package com.example.crudmultitable.Features.SelectMhsdanMatul;

public class MahasiswaDetail {
    private long id;
    private String name;
    private long nim;
    private String namemtk;
    private int code;
    private double credit;


    public MahasiswaDetail(String name, long nim, String mtk, double nilai) {
        this.name= name;
        this.nim = nim;
        this.namemtk = mtk;
        this.credit = nilai;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public long getNim() {
        return nim;
    }

    public void setNim(long nim) {
        this.nim = nim;
    }

    public String getNamemtk() {
        return namemtk;
    }

    public void setNamemtk(String namemtk) {
        this.namemtk = namemtk;
    }
}
