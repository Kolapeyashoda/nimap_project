package com.nimap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nimap.exception.ResourceNotFoundException;
import com.nimap.model.Category;
import com.nimap.model.Product;
import com.nimap.respository.CategoryRepository;
import com.nimap.respository.ProductRepository;

import jakarta.validation.ValidationException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public Page<Product> getAllProducts(int page, int size) {
		return productRepository.findAll(PageRequest.of(page, size));
	}

	public Product createProduct(Product product) throws Exception {

		if (product.getCategory() == null || product.getCategory().getId() == null) {
			throw new ValidationException("Product must have a valid category.");
		}

		Optional<Category> categoryOpt = categoryRepository.findById(product.getCategory().getId());
		if (categoryOpt.isEmpty()) {
			throw new ValidationException("Category not found.");
		}

		product.setCategory(categoryOpt.get());
		return productRepository.save(product);
	}

	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	public Product updateProduct(Long id, Product productDetails) throws ResourceNotFoundException {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setPrice(productDetails.getPrice());

		if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
			Category category = categoryRepository.findById(productDetails.getCategory().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
			product.setCategory(category);
		}

		return productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		productRepository.delete(product);
	}
}
