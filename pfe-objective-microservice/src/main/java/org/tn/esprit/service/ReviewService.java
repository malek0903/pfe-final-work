package org.tn.esprit.service;


import org.tn.esprit.commons.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    void createReview(ReviewDto reviewDto);

    Long getReviewsByWorkflowId(Long workflowId, String username);

    List<ReviewDto> getAllReviews();
    Long  countAll() ;
    boolean existByWorkflowId(Long workflowId);
}
