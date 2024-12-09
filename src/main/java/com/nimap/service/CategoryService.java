package com.nimap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nimap.exception.ResourceNotFoundException;
import com.nimap.model.Category;
import com.nimap.respository.CategoryRepository;
 
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public Page<Category> getAllCategories(int page, int size) {
		return categoryRepository.findAll(PageRequest.of(page, size));
	}

	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(null);
	}

	public Category updateCategory(Long id, Category categoryDetails) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(null);
		category.setName(categoryDetails.getName());
		return categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(null);
		categoryRepository.delete(category);
	}
}
