package com.ecommerce.main;

import com.ecommerce.config.AppConfig;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.exception.NoSuchEntityFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Vendor;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.VendorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductService productService=context.getBean(ProductService.class);
        CategoryService categoryService = context.getBean(CategoryService.class);
        VendorService vendorService= context.getBean(VendorService.class);
        Scanner sc=new Scanner(System.in);
        //System.out.println("Config loaded..");
        while(true){
            System.out.println("----------ECOMMERCE APP------------");
            System.out.println("1. Add Product");
            System.out.println("2. Find Product by ID");
            System.out.println("3. Update stock quantity");
            System.out.println("4. Find Product count by Vendor");
            System.out.println("0. to Exit");
            int choice=sc.nextInt();
            if(choice==0){
                System.out.println("Exiting...");
                break;
            }
            switch(choice){
                case 1 ->{
                    Product product=new Product();
                    sc.nextLine();
                    product.setId((long)(Math.random()*100000));
                    System.out.println("Enter Product Name: ");
                    product.setName(sc.nextLine());
                    System.out.println("Enter Product Price: ");
                    product.setPrice(sc.nextDouble());
                    System.out.println("Enter Product Stock Quantity: ");
                    product.setStockQuantity(sc.nextInt());
                    System.out.println("Enter Product Category ID: ");
                    int cid=sc.nextInt();
                    try {
                        List<Category> list = productService.getCategoryByID(cid);
                        Category category;
                        if (list == null || list.isEmpty()) {
                            category = new Category();
                            category.setId(cid);
                            sc.nextLine();
                            System.out.println("Enter Category Name: ");
                            category.setName(sc.nextLine());
                            System.out.println("Enter Category Description: ");
                            category.setDescription(sc.nextLine());
                            categoryService.insertCategory(category);
                            System.out.println("New Category inserted successfully");
                        } else
                            category = list.getFirst();
                        product.setCategory(category);
                        System.out.println("Enter Product Vendor ID: ");
                        int vid = sc.nextInt();
                        List<Vendor> listVen = productService.getVendorByID(vid);
                        Vendor vendor;
                        if (listVen == null || listVen.isEmpty()) {
                            vendor = new Vendor();
                            vendor.setId(vid);
                            sc.nextLine();
                            System.out.println("Enter Vendor Name: ");
                            vendor.setName(sc.nextLine());
                            System.out.println("Enter Vendor Email: ");
                            vendor.setEmail(sc.next());
                            vendorService.insertVendor(vendor);
                            System.out.println("New Vendor inserted successfully");
                        } else
                            vendor = listVen.getFirst();
                        product.setVendor(vendor);
                        productService.insertProduct(product);
                        System.out.println("New Product Added Successfully: ");
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2 ->{
                    try {
                        System.out.println("Enter Product ID: ");
                        long pid = sc.nextLong();
                        ProductDto product = productService.getProductById(pid);
                        System.out.println("Result: ");
                        System.out.println(product);
                    } catch (NoSuchEntityFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 3 ->{
                    try {
                        System.out.println("Enter Product ID: ");
                        long pid = sc.nextLong();
                        System.out.println("Enter New Product Quantity: ");
                        int newQuantity = sc.nextInt();
                        productService.setNewQuantityById(pid, newQuantity);
                        System.out.println("Product Stock Quantity has changed successfully");
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4 ->{
                    System.out.println("Product Count for Each Vendor");
                    System.out.println(productService.countProductsByVendor());
                    break;
                }
                default ->{
                    System.out.println("Invalid option..");
                    return;
                }
            }
        }
        sc.close();
    }
}
