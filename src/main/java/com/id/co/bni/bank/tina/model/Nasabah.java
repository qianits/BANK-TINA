package com.id.co.bni.bank.tina.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data 
@Entity 
@Table (
    name ="NASABAH_BANK_TINA"
)
public class Nasabah {

    @Id
    @Column (name = "ID")
    private String nomorKTP;
    @Column (name = "NAMA_LENGKAP")
    private String namaLengkap;
    @Column (name = "ALAMAT")
    private String alamat;
    @Column (name = "TEMPAT_LAHIR")
    private String tempatLahir;
    @Column (name = "TANGGAL_LAHIR")
    private String tanggalLahir;
    @Column (name = "NOMOR_HANPHONE")
    private String nomorHandphone; 

}
