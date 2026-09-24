package com.etec.tourtripapi.destination.mapper;

import com.etec.tourtripapi.destination.dto.request.DestinationRequest;
import com.etec.tourtripapi.destination.dto.response.DestinationResponse;
import com.etec.tourtripapi.destination.entity.Destination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DestinationMapper {

    // Map ពី Request Create ទៅ Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true) // Handle file upload separately in service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Destination toEntity(DestinationRequest.Create request);

    // Map ពី Entity ទៅ Response DTO
    DestinationResponse toResponse(Destination destination);

    // Map update data ទៅលើ Entity ដែលមានស្រាប់ (Ignore null values automatically)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true) // Keep old image if not updated
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntityFromDto(DestinationRequest.Update request, @MappingTarget Destination destination);
}