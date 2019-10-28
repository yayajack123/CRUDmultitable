package com.example.crudmultitable.Features.MahasiswaCRUD;

public class Mahasiswa {

    private long id;
    private String name;
    private long nim;
    private String phoneNumber;
    private String email;

    public Mahasiswa(int id, String name, long nim, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public long getNim() {
        return nim;
    }

    public void setNim(long nim) {
        this.nim = nim;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
