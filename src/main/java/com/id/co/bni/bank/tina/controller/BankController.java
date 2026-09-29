package com.id.co.bni.bank.tina.controller;
import java.util.List;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.id.co.bni.bank.tina.dto.Response;
import com.id.co.bni.bank.tina.dto.UpdatedNasabah;
import com.id.co.bni.bank.tina.model.Nasabah;
import com.id.co.bni.bank.tina.service.BankService;

@RestController 
public class BankController {
    
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    
    // Endpoint to create a new Nasabah
    @PostMapping("/nasabah/create")
    public ResponseEntity<Response> createNasabah(@Valid @RequestBody Nasabah nasabah) {
        if (bankService.getNasabahById(nasabah.getNomorKTP()) != null) {
            Response response = Response.builder()
                    .code(400)
                    .message("Nasabah with this KTP number already exists")
                    .data(null)
                    .build();
            return ResponseEntity.status(response.getCode()).body(response);
        }

        Nasabah newNasabah = bankService.saveNasabah(nasabah);
        Response response = Response.builder()
                .code(200)
                .message("Nasabah created successfully")
                .data(newNasabah)
                .build();

        return ResponseEntity.ok(response);
    }

    // Endpoint to get a Nasabah by KTP number
    @GetMapping("/nasabah/{nomorKTP}")
    public ResponseEntity<Response> getNasabahById(@PathVariable String nomorKTP) {
        Nasabah nasabah = bankService.getNasabahById(nomorKTP);
        if (nasabah != null) {
            Response response = Response.builder()
                .code(200)
                .message("Nasabah retrieved successfully")
                .data(nasabah)
                .build();
            return ResponseEntity.ok(response);
        } else {
            Response response = Response.builder()
                .code(404)
                .message("Nasabah not found")
                .data(null)
                .build();
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }
              

    // Endpoint to get all Nasabah
    @GetMapping("/nasabah/all")
    public ResponseEntity<Response> getAllNasabah() {
        List<Nasabah> nasabahList = bankService.getAllNasabah();
        Response response = Response.builder()
                .code(200)
                .message("All Nasabah retrieved successfully")
                .data(nasabahList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/nasabah/update/{nomorKTP}")
    public ResponseEntity<Response> updateNasabah(@PathVariable String nomorKTP, @RequestBody UpdatedNasabah updatedNasabah) {
        if (bankService.updateNasabah(nomorKTP, updatedNasabah)){
            Response response = Response.builder()
                .code(200)
                .message("Nasabah updated successfully")
                .data(null)
                .build();
            return ResponseEntity.ok(response);
        }; 
        Response response = Response.builder()
                .code(404)
                .message("Nasabah not found")
                .data(null)
                .build();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/nasabah/delete/{nomorKTP}")
    public ResponseEntity<Response> deleteNasabah(@PathVariable String nomorKTP) {
        if (bankService.deleteNasabah(nomorKTP)) {
            Response response = Response.builder()
                    .code(200)
                    .message("Nasabah deleted successfully")
                    .data(null)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            Response response = Response.builder()
                    .code(404)
                    .message("Nasabah not found")
                    .data(null)
                    .build();
            return ResponseEntity.status(response.getCode()).body(response);
        }
    }
    
}
