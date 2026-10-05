package com.ecommerce.dto;

import com.ecommerce.model.Category;
import com.ecommerce.model.Vendor;

public record ProductDto(long id, String name, double price,
                         int stockQuantity, int categoryId, String categoryName,
                         int vendorId, String vendorName) {
}
