package com.ecommerce.repository;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.mapper.ProductDtoMapper;
import com.ecommerce.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductDtoMapper productDtoMapper;

    public ProductRepository(JdbcTemplate jdbcTemplate, ProductDtoMapper productDtoMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.productDtoMapper = productDtoMapper;
    }


    public void insertProduct(Product product) {
        String sql="insert into product values(?,?,?,?,?,?)";
        Object[] values=new Object[]{product.getId(),product.getName(),
                product.getPrice(),product.getStockQuantity(),product.getCategory().getId(),
                product.getVendor().getId()};
        jdbcTemplate.update(sql,values);
    }

    public List<ProductDto> getProductById(long pid) {
        String sql= """
                Select p.id, p.name, p.price, p.stock_quantity, p.category_id, c.name as category_name, 
                p.vendor_id, v.name as vendor_name
                from product p join category c on p.category_id = c.id
                join vendor v on p.vendor_id = v.id
                where p.id = ?
                """;
        return jdbcTemplate.query(sql, productDtoMapper,pid);
    }

    public void setNewQuantityById(long pid, int newQuantity) {
        String sql="Update product set stock_quantity = ? Where id = ?";
        Object[] values=new Object[]{newQuantity,pid};
        jdbcTemplate.update(sql,values);
    }

    public Map<String, Integer> countProductsByVendor() {
        String sql = """
                    select v.name as vendor_name, count(p.id) as product_count
                    from product p
                    JOIN vendor v ON p.vendor_id = v.id
                    group by v.name
                    """;
        Map<String, Integer> vendorProductCount = new HashMap<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            vendorProductCount.put(rs.getString(1), rs.getInt(2));
            return null;
        });
        return vendorProductCount;
    }
}
