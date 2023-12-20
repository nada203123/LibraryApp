package com.tekup.LibraryApp.service.Book;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.library.BookCopy;
import com.tekup.LibraryApp.model.library.Category;
import com.tekup.LibraryApp.model.library.StatusCopy;
import com.tekup.LibraryApp.payload.request.BookAddRequest;
import com.tekup.LibraryApp.repository.library.BookCopyRepo;
import com.tekup.LibraryApp.repository.library.BookRepo;
import com.tekup.LibraryApp.repository.library.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BookServiceImpli implements BookService {

    private final BookRepo bookRepo;
    private final CategoryRepository categoryRepo;

    private final BookCopyRepo bookCopyRepo;

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
                .imageUrl(bookAddRequest.getImageUrl())
                .language(bookAddRequest.getLanguage())
                .numberPages(bookAddRequest.getNumberPages())
                .build();

        copies.forEach(bookCopy -> bookCopy.setBook(newBook));
        newBook.setArchived(false);
        bookRepo.save(newBook);
        return "redirect:/manager/book/add";
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow();
    }

    @Override
    public void updateBook(Long bookId, BookAddRequest bookAddRequest) {
        var book = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        book.setLanguage(bookAddRequest.getLanguage());
        book.setTitle(bookAddRequest.getLanguage());
        book.setNumberPages(bookAddRequest.getNumberPages());
        book.setPublicationDate(bookAddRequest.getPublicationDate());

        //Update Copies
        int currentNumberOfCopies = book.getBookCopies().size();
        int newNumberOfCopies = bookAddRequest.getNumberOfCopies();

        if (newNumberOfCopies > currentNumberOfCopies) {
            // Add new copies
            int copiesToAdd = newNumberOfCopies - currentNumberOfCopies;
            for (int i = 0; i < copiesToAdd; i++) {
                BookCopy newCopy = new BookCopy(StatusCopy.AVAILABLE);
                newCopy.setBook(book);
                book.getBookCopies().add(newCopy);
                bookCopyRepo.save(newCopy);
            }
        } else if (newNumberOfCopies < currentNumberOfCopies) {
            // Update existing copies if number of copies in the request is less than in the db
            int copiesToDeactivate = currentNumberOfCopies - newNumberOfCopies;
            for (BookCopy bookCopy : book.getBookCopies()) {
                if (bookCopy.getStatusCopy() == StatusCopy.AVAILABLE && copiesToDeactivate > 0) {
                    bookCopy.setStatusCopy(StatusCopy.UNAVAILABLE);
                    bookCopyRepo.save(bookCopy);
                    copiesToDeactivate--;
                }
            }
        }
        // Update the book entity
        bookRepo.save(book);
    }

    @Override
    public void archiveBook(Long id) {
        var book = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setArchived(true);
        bookRepo.save(book);
    }

    @Override
    public void revealBook(Long id) {
        var book = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setArchived(false);
        bookRepo.save(book);
    }
}
