package com.trnd.trndapi.dto;

import com.trnd.trndapi.entity.BusinessServiceCategoryRef;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link BusinessServiceCategoryRef}
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessServiceCategoryRefDto implements Serializable {
    private Long busSvcCatId;
    private String busSvcCatNm;
    private String descr;
}