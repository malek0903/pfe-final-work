package org.tn.esprit.commons.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Long idReview;
    private Long raiting;
    private String feedback;
    private Long idWorkflow;
    private String username;
}
