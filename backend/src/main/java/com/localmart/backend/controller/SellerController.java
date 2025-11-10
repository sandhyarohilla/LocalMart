package com.localmart.backend.controller;

import com.localmart.backend.dto.seller.SellerResponse;
import com.localmart.backend.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerResponse>> getAllSellers() {
        List<SellerResponse> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getSellerById(@PathVariable("id") Long sellerId) {
        try {
            SellerResponse seller = sellerService.getSellerById(sellerId);
            return ResponseEntity.ok(seller);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



