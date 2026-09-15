package com.etec.tourtripapi.tour.dto.request;

import com.etec.tourtripapi.common.enums.EntityStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageRequestDTO {

    @NotNull(message = "Tour ID is required")
    private Long tourId;

    private MultipartFile file;       // សម្រាប់ករណី Upload តែ ១
    private List<MultipartFile> files; // សម្រាប់ករណី Upload ច្រើន

    private Boolean isPrimary;
    private EntityStatus status;

    public String getImageUrl() {
        return file != null ? file.getOriginalFilename() : null;
    }
}