package com.tekup.LibraryApp.service.catalogue;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookCatalogueFilter;
import com.tekup.LibraryApp.repository.library.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogueServiceImpli implements CatalogueService {
    private final BookRepo bookRepo;


    @Override
    public Page<Book> findBooksByFilters(BookCatalogueFilter bookCatalogueFilter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepo.findByFilters(bookCatalogueFilter.getTitle(),
                bookCatalogueFilter.getCategories(),
                bookCatalogueFilter.getAuthors(),
                pageable);
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookRepo.findAll(pageable);
    }
}
