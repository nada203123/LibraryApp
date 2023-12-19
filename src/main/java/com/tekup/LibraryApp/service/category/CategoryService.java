package com.tekup.LibraryApp.service.category;

import com.tekup.LibraryApp.model.library.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllGategories();

    List<String> getAllGategoriesNames();
}
