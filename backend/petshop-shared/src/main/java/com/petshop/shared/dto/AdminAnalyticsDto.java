package com.petshop.shared.dto;

import java.math.BigDecimal;

public record AdminAnalyticsDto(
        long openInquiries,
        long openBookings,
        long openAdoptions,
        long orderCount,
        BigDecimal grossRevenue,
        long wishlistCount
) {
}

