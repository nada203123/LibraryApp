package com.tekup.LibraryApp.service.Book;

import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.library.BookCopy;
import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.model.library.StatusCopy;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import com.tekup.LibraryApp.repository.library.BookRepo;
import com.tekup.LibraryApp.repository.library.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BookServiceImpli implements BookService {

    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;

    private Set<BookCopy> createBookCopies(int numberOfCopies) {
        return IntStream.range(0, numberOfCopies)
                .mapToObj(i -> new BookCopy(StatusCopy.AVAILABLE))
                .collect(Collectors.toSet());
    }

    @Override
    public String addBook(BookAddRequest bookAddRequest) {
        System.out.println(bookAddRequest.getPublicationDate());
        Set<Category> categories = bookAddRequest.getCategories().stream()
                .map(category -> categoryRepo.findByName(category)
                        .orElseGet(() -> categoryRepo.save(new Category(category)))
                )
                .collect(Collectors.toSet());

        Set<BookCopy> copies = createBookCopies(bookAddRequest.getNumberOfCopies());

        var newBook = Book.builder()
                .title(bookAddRequest.getTitle())
                .categories(categories)
                .bookCopies(copies)
                .publicationDate(bookAddRequest.getPublicationDate())
                .build();

        copies.forEach(bookCopy -> bookCopy.setBook(newBook));
        bookRepo.save(newBook);
        return "redirect:/admin/add_book";
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        return bookRepo.findAll(pageable);
    }
}
