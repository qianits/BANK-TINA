package com.id.co.bni.bank.tina.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.id.co.bni.bank.tina.model.Nasabah;

public interface BankRepository extends JpaRepository<Nasabah, String> {
    @Query("SELECT n FROM Nasabah n WHERE n.nomorKTP = :nomorKTP")
    Nasabah findByNomorKTP(@Param("nomorKTP") String nomorKTP);
}
