package ru.nsu.sberlab.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureCreationDto implements Comparable<FeatureCreationDto>{
    private String description;
    private Long propertyId;

    @Override
    public int compareTo(FeatureCreationDto other) {
        return Long.compare(this.getPropertyId(), other.getPropertyId());
    }
}
