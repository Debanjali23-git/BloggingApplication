package com.Aegon.BloggingApplication.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Email(message = "Email should not be null or empty")
    private String email;
    @NotEmpty
    @Size(min = 10)
    private String body;
}
