package com.id.co.bni.bank.tina.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.id.co.bni.bank.tina.dto.UpdatedNasabah;
import com.id.co.bni.bank.tina.model.Nasabah;
import com.id.co.bni.bank.tina.repository.BankRepository;

import lombok.extern.slf4j.Slf4j;


@Service 
@Slf4j 
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Nasabah saveNasabah(Nasabah nasabah) {
        return bankRepository.save(nasabah);
    }

    public Nasabah getNasabahById(String nomorKTP) {
        return bankRepository.findByNomorKTP(nomorKTP);
    }

    public List<Nasabah> getAllNasabah() {
        return bankRepository.findAll();
    }

    public boolean updateNasabah(String nomorKTP, UpdatedNasabah updatedNasabah) {
        Nasabah nasabah = getNasabahById(nomorKTP);
        if (nasabah != null) {
            if (updatedNasabah.getNamaLengkapBaru() != null){
                nasabah.setNamaLengkap(updatedNasabah.getNamaLengkapBaru());
            }        
            if (updatedNasabah.getAlamatBaru() != null){
                nasabah.setAlamat(updatedNasabah.getAlamatBaru());
            }
            if (updatedNasabah.getNomorHandphoneBaru() != null){
                nasabah.setNomorHandphone(updatedNasabah.getNomorHandphoneBaru());
            }
            saveNasabah(nasabah);
            log.info("Nasabah updated successfully: {}", nasabah.getNomorKTP());
            return true;
        } else {
            log.warn("Nasabah not found with KTP: {}", nomorKTP);
            return false;
        }
    }

    public boolean deleteNasabah(String nomorKTP) {
        Nasabah nasabah = getNasabahById(nomorKTP);
        if (nasabah != null) {
            bankRepository.delete(nasabah);
            log.info("Nasabah deleted successfully: {}", nasabah.getNomorKTP());
            return true;
        } else {
            log.warn("Nasabah not found with KTP: {}", nomorKTP);
            return false;
        }
    }
}
