package com.tekup.LibraryApp.service.category;

import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.repository.library.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCategoriesPaged(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public List<String> getAllCategoriesNames() {
        return categoryRepository.findAll().stream().map(Category::getName).toList();
    }
}
