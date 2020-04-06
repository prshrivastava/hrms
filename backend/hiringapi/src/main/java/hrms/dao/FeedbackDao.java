package hrms.dao;

import java.util.List;

import hrms.domain.InterviewFeedback;

public interface FeedbackDao {
	public int submitFeedback(int interviewId, String comments);
	public List<InterviewFeedback> getFeedback(int candidateId);
}
