package org.swisscom.serviceapp.infrastructure.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Object used only as D-T-O
 */
public record OwnerDto(String id, @NotEmpty String name, @NotEmpty String accountNumber
        , @Min(value = 0, message = "Level cannot be less than 0") Integer level, Integer version)
        implements BaseDto {

}
