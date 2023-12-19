package com.tekup.LibraryApp.service.category;

import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.repository.library.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public List<Category> getAllGategories() {
        return categoryRepo.findAll();
    }

    @Override
    public List<String> getAllGategoriesNames() {
        return categoryRepo.findAll().stream().map(Category::getName).toList();

    }
}
