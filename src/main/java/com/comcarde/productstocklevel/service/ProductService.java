package com.comcarde.productstocklevel.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.comcarde.productstocklevel.exception.ProductNotFoundException;
import com.comcarde.productstocklevel.model.Product;
import com.comcarde.productstocklevel.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductByName(String productName) throws ProductNotFoundException {

        Optional<Product> product = productRepository.findById(productName);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException(String.format("No product found with product name: %s", productName));
        }
    }

    public Product createOrUpdateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductName());

        if (optionalProduct.isPresent()) {
            Product newProduct = optionalProduct.get();
            newProduct.setProductName(product.getProductName());
            newProduct.setCurrentStock(product.getCurrentStock());
            return productRepository.save(newProduct);
        } else {

            return productRepository.save(product);
        }
    }

    public void deleteProduct(String productName) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productName);
        if (product.isPresent()) {
            productRepository.deleteById(productName);
        } else {
            throw new ProductNotFoundException(String.format("No product exists with product name: %s", productName));
        }
    }
}
