package com.tekup.LibraryApp.DTO.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryRequest {
    private String name;
}
