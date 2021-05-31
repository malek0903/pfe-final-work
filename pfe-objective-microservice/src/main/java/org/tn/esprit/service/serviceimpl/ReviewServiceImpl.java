package org.tn.esprit.service.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.tn.esprit.commons.dto.ReviewDto;
import org.tn.esprit.dao.ReviewRepository;
import org.tn.esprit.entities.Review;
import org.tn.esprit.service.ReviewService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    @Inject
    ReviewRepository reviewRepository;

    @Override
    public void createReview(ReviewDto reviewDto) {
        reviewRepository.save(new Review(
                reviewDto.getRaiting(),
                reviewDto.getFeedback(),
                reviewDto.getIdWorkflow(),
                reviewDto.getUsername()

        ));
    }

    @Override
    public Long getReviewsByWorkflowId(Long workflowId, String username) {
        try {
            return reviewRepository.findByIdWorkflowAndUsername(workflowId, username).getRaiting();
        } catch (NullPointerException nullPointerException) {
            return 0L;
        }
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream().map(ReviewServiceImpl::mapToDo).collect(Collectors.toList());
    }

    @Override
    public Long countAll() {
        return reviewRepository.count();
    }

    @Override
    public boolean existByWorkflowId(Long workflowId) {
        return reviewRepository.existsByIdWorkflow(workflowId);
    }

    public static ReviewDto mapToDo(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getRaiting(),
                review.getFeedback(),
                review.getIdWorkflow(),
                review.getUsername()
        );
    }
}
