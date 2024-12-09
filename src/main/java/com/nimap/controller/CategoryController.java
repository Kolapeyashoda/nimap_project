package com.nimap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimap.exception.ResourceNotFoundException;
import com.nimap.model.Category;
import com.nimap.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Page<Category> getAllCategories(@RequestParam("page") int page) {
		return categoryService.getAllCategories(page, 10); // Pagination with 10 items per page
	}

	@PostMapping
	public Category createCategory(@RequestBody Category category) {
		return categoryService.createCategory(category);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		try {
            Category getByIdCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(getByIdCategory);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
		try {
	        Category updatedCategory = categoryService.updateCategory(id, category);
	        return ResponseEntity.ok(updatedCategory);
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
		try {
	        categoryService.deleteCategory(id);
	        return ResponseEntity.ok().build();  // Send success response
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
	    }
	}
}
