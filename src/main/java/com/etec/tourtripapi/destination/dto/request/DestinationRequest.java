package com.etec.tourtripapi.destination.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class DestinationRequest {

    @Getter
    @Setter
    public static class Create {
        @NotBlank(message = "Destination name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        private String name;

        @Size(max = 100, message = "Slug must not exceed 100 characters")
        private String slug;

        private String description;

        private Boolean status;

        private Object file;

        // Custom getter សម្រាប់ទប់តម្លៃ null ពេលកូដយកទៅប្រើប្រាស់
        public Object getFile() {
            return this.file; // បើ this.file is null វានឹង return null ស្រាប់ដោយស្វ័យប្រវត្តិ
        }
    }

    @Getter
    @Setter
    public static class Update {
        @Size(max = 100, message = "Name must not exceed 100 characters")
        private String name;

        @Size(max = 100, message = "Slug must not exceed 100 characters")
        private String slug;

        private String description;

        private Boolean status;

        private Object file; // អាចទទួលទាំង MultipartFile ឬ String URL

        public Object getFile() {
            return this.file;
        }
    }
}