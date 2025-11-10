package com.localmart.backend.service;

import com.localmart.backend.dto.seller.SellerResponse;
import com.localmart.backend.model.Seller;
import com.localmart.backend.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<SellerResponse> getAllSellers() {
        return sellerRepository.findAll()
                .stream()
                .map(this::mapToSellerResponse)
                .collect(Collectors.toList());
    }

    public SellerResponse getSellerById(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        return mapToSellerResponse(seller);
    }

    private SellerResponse mapToSellerResponse(Seller seller) {
        return new SellerResponse(
                seller.getSellerId(),
                seller.getStoreName(),
                seller.getOwnerName(),
                seller.getEmail(),
                seller.getAddress()
        );
    }
}



