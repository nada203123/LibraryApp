package com.tekup.LibraryApp.service.book;

import com.tekup.LibraryApp.exception.ResourceNotFoundException;
import com.tekup.LibraryApp.model.library.Book;
import com.tekup.LibraryApp.model.library.BookCopy;
import com.tekup.LibraryApp.model.library.StatusCopy;
import com.tekup.LibraryApp.DTO.request.BookAddRequest;
import com.tekup.LibraryApp.repository.library.BookCopyRepo;
import com.tekup.LibraryApp.repository.library.BookRepo;
import com.tekup.LibraryApp.service.notification.NotificationService;
import com.tekup.LibraryApp.service.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BookServiceImpli implements BookService {

    private final BookRepo bookRepo;
    private final BookCopyRepo bookCopyRepo;
    private final FileStorageService fileStorageService;
    private final NotificationService notificationService;

    private Set<BookCopy> createBookCopies(int numberOfCopies) {
        return IntStream.range(0, numberOfCopies)
                .mapToObj(i -> new BookCopy(StatusCopy.AVAILABLE))
                .collect(Collectors.toSet());
    }

    @Override
    public String addBook(BookAddRequest bookAddRequest) {

        Set<BookCopy> copies = createBookCopies(bookAddRequest.getNumberOfCopies());

        var newBook = Book.builder()
                .title(bookAddRequest.getTitle())
                .categories(bookAddRequest.getCategories())
                .bookCopies(copies)
                .publicationDate(bookAddRequest.getPublicationDate())
                .language(bookAddRequest.getLanguage())
                .numberPages(bookAddRequest.getNumberPages())
                .build();
        if (bookAddRequest.getImageUrl() != null && !bookAddRequest.getImageUrl().isEmpty()) {
            try {
                String fileName = fileStorageService.storeImg(bookAddRequest.getImageUrl());
                newBook.setImageUrl("/uploads/img/user/" + fileName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        copies.forEach(bookCopy -> bookCopy.setBook(newBook));
        newBook.setArchived(false);
        bookRepo.save(newBook);
        String title = "New Book";
        String msg = bookAddRequest.getTitle();
        notificationService.sendNotificationToMembers(title, msg);
        return "redirect:/manager/book/add";
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow();
    }

    @Override
    public void updateBook(Long bookId, BookAddRequest bookUpdateRequest) {
        var book = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        book.setLanguage(bookUpdateRequest.getLanguage());
        book.setTitle(bookUpdateRequest.getLanguage());
        book.setNumberPages(bookUpdateRequest.getNumberPages());
        book.setPublicationDate(bookUpdateRequest.getPublicationDate());

        //Update Copies
        int currentNumberOfCopies = book.getBookCopies().size();
        int newNumberOfCopies = bookUpdateRequest.getNumberOfCopies();

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
        if (bookUpdateRequest.getImageUrl() != null && !bookUpdateRequest.getImageUrl().isEmpty()) {
            try {
                String fileName = fileStorageService.storeImg(bookUpdateRequest.getImageUrl());
                book.setImageUrl("/uploads/img/user/" + fileName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        book.setCategories(bookUpdateRequest.getCategories());

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
