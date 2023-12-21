package com.tekup.LibraryApp.service.catalogue;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.DTO.request.BookCatalogueFilter;
import com.tekup.LibraryApp.repository.library.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogueServiceImpli implements CatalogueService {
    private final BookRepo bookRepo;


    @Override
    public Page<Book> findBooksByFilters(BookCatalogueFilter bookCatalogueFilter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        System.out.println(bookCatalogueFilter);
        return bookRepo.findByFilters(bookCatalogueFilter.getTitle(),
                bookCatalogueFilter.getCategories(),
                bookCatalogueFilter.getAuthor(),
                bookCatalogueFilter.getLanguage(),
                pageable);
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookRepo.findAll(pageable);
    }

    @Override
    public Page<Book> findPaginatedBySelectedCategories(List<Long> categories, int pageNo, int pageSize) {
        return bookRepo.findPaginatedByCategories(categories, PageRequest.of(pageNo, pageSize));
    }
}
