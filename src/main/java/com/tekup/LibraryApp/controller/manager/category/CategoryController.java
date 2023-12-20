package com.tekup.LibraryApp.controller.manager.category;

import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String listCategories(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Category> categoryPage = this.categoryService.getAllCategoriesPaged(pageable);

        model.addAttribute("categoryPage", categoryPage);

        return "manager/category/list";
    }

    @GetMapping("/category/add")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "manager/category/add";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        this.categoryService.saveCategory(category);
        return "redirect:/manager/category";
    }

    @GetMapping("/category/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Category category = this.categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "manager/category/edit";
    }

    @PostMapping("/category/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category category) {
        category.setId(id);
        this.categoryService.updateCategory(category);
        return "redirect:/manager/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        this.categoryService.deleteCategory(id);
        return "redirect:/manager/category";
    }
}