package com.tekup.LibraryApp.service.category;

import com.tekup.LibraryApp.model.library.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<String> getAllCategoriesNames();

    List<Category> getAllCategories();

    Page<Category> getAllCategoriesPaged(Pageable pageable);

    Category getCategoryById(Long id);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Long id);
}
