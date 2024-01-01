package com.tekup.LibraryApp.DTO.request;
import com.tekup.LibraryApp.model.library.Category;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookCatalogueFilter {
    private String title;
    private List<Long> categories;
    private String language;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    private String author;
    private List<String> categories;
}
