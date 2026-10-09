package com.id.co.bni.bank.tina.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.id.co.bni.bank.tina.dto.UpdatedNasabah;
import com.id.co.bni.bank.tina.model.Nasabah;
import com.id.co.bni.bank.tina.model.Transaction;
import com.id.co.bni.bank.tina.repository.BankRepository;
import com.id.co.bni.bank.tina.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service 
@Slf4j 
public class BankService {

    private final BankRepository bankRepository;
    private final TransactionRepository transactionRepository;

    public BankService(BankRepository bankRepository, TransactionRepository transactionRepository) {
        this.bankRepository = bankRepository;
        this.transactionRepository = transactionRepository;
    }

    public Nasabah saveNasabah(Nasabah nasabah) {
        return bankRepository.save(nasabah);
    }

    public Nasabah getNasabahById(String nomorKTP) {
        return bankRepository.findByNomorKTP(nomorKTP);
    }

    public Page<Nasabah> getAllNasabah(int page, int size, String sortBy) {
        Pageable pageable;
        if (sortBy.equals("asc")){
            pageable = PageRequest.of(page, size,  Sort.by("nomorKTP").ascending());
        } else {
            pageable = PageRequest.of(page, size,  Sort.by("nomorKTP").descending());
        }
        return bankRepository.findAll(pageable);
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

    @Transactional
    public boolean transferAmount(
            String noRekening1,
            String noRekening2,
            double amount) {

        // Validate transfer amount
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Transfer amount must be greater than zero"
            );
        }

        // Prevent transferring to the same account
        if (noRekening1.equals(noRekening2)) {
            throw new IllegalArgumentException(
                "Source and destination accounts must be different"
            );
        }

        // Find both accounts
        Nasabah account1 = bankRepository.findByNoRekening(noRekening1)
            .orElseThrow(() ->
                new RuntimeException("Source account not found")
            );

        Nasabah account2 = bankRepository
            .findByNoRekening(noRekening2)
            .orElseThrow(() ->
                new RuntimeException("Destination account not found")
            );

        // Check sufficient balance
        if (account1.getAmount() < amount) {
            throw new IllegalArgumentException(
                "Insufficient balance"
            );
        }

        // Update balances
        account1.setAmount(account1.getAmount() - amount);
        account2.setAmount(account2.getAmount() + amount);

        // Save both accounts
        bankRepository.save(account1);
        bankRepository.save(account2);

        // Add transaction data
        Transaction transaction = new Transaction();

        transaction.setTransactionId(
            java.util.UUID.randomUUID().toString()
        );
        transaction.setNoRekening1(noRekening1);
        transaction.setNoRekening2(noRekening2);
        transaction.setAmount(amount);
        transaction.setStatus(Transaction.TransactionStatus.SUCCESS);

        transactionRepository.save(transaction);
        return true;
    }
}
