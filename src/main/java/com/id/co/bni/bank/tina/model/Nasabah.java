package com.id.co.bni.bank.tina.model;

import jakarta.validation.constraints.Pattern;

import com.id.co.bni.bank.tina.utils.NoRekeningGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
    @Pattern(regexp = "\\d{16}", message = "Nomor KTP harus terdiri dari 16 digit angka")
    private String nomorKTP;

    @Column (name = "NAMA_LENGKAP", nullable = false)
    private String namaLengkap;

    @Column (name = "ALAMAT", nullable = false)
    private String alamat;
    
    @Column (name = "TEMPAT_LAHIR", nullable = false)
    private String tempatLahir;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Tanggal lahir harus dalam format yyyy-MM-dd")
    @Column (name = "TANGGAL_LAHIR", nullable = false)
    private String tanggalLahir;

    @Pattern(regexp = "\\d{10,13}", message = "Nomor handphone harus terdiri dari 10-13 digit angka")
    @Column (name =  "NOMOR_HANPHONE", nullable = false)
    private String nomorHandphone; 

    @Column (name = "NO_REKENING")
    private String noRekening;

    @PrePersist
    public void generateNoRekening() {
        if (noRekening == null || noRekening.isBlank()) {
            noRekening = NoRekeningGenerator.generate(
                nomorKTP,
                nomorHandphone
            );
        }
    }

    @Column (name = "AMOUNT", nullable = false)
    private double amount = 0.0;

}
