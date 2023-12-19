package com.tekup.LibraryApp.service.catalogue;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.payload.request.BookCatalogueFilter;
import org.springframework.data.domain.Page;

public interface CatalogueService {
    Page<Book> findPaginated(int pageNo, int pageSize);
    Page<Book> findBooksByFilters(BookCatalogueFilter bookCatalogueFilter, int pageNo, int pageSize);

}
