package com.tekup.LibraryApp.DTO.request;

import com.tekup.LibraryApp.model.library.Category;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAddRequest {

    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    private Set<Category> categories;
    public int numberOfCopies;
    String imageUrl;
    int numberPages;
    String language;
}
