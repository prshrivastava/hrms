package hrms.domain;

import java.util.List;

public class InterviewFeedback {
	private Interview interview;
	private List<Feedback> feedback;
	public Interview getInterview() {
		return interview;
	}
	public void setInterview(Interview interview) {
		this.interview = interview;
	}
	public List<Feedback> getFeedback() {
		return feedback;
	}
	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}
	
}
