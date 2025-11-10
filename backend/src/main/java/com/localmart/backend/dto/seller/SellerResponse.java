package com.localmart.backend.dto.seller;

public record SellerResponse(
    Long sellerId,
    String storeName,
    String ownerName,
    String email,
    String address
) {}



