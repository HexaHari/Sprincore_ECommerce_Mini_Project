package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.exception.NoSuchEntityFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Vendor;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<Category> getCategoryByID(int id) {
        return categoryRepository.getCategoryByID(id);
    }

    public List<Vendor> getVendorByID(int id) {
        return vendorRepository.getVendorByID(id);
    }

    public void insertProduct(Product product) {
        productRepository.insertProduct(product);
    }

    public ProductDto getProductById(long pid) {
        List<ProductDto> list=productRepository.getProductById(pid);
        if(list == null || list.isEmpty())
            throw new NoSuchEntityFoundException("No Product is available for this ID..");
        else
            return list.getFirst();

    }

    public void setNewQuantityById(long pid, int newQuantity) {
        productRepository.setNewQuantityById(pid,newQuantity);
    }

    public Map<String,Integer> countProductsByVendor(){
        return productRepository.countProductsByVendor();
    }
}
