package hrms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hrms.domain.*;
import hrms.service.InterviewService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/interview")
public class InterviewController {
	@Autowired InterviewService interviewService;
	
	@PostMapping("/schedule/{posid}/{candidateId}")
	public int scheduleInterview(@PathVariable short posid, @PathVariable String candidateId,
									@RequestBody Interview interview) {
		return interviewService.scheduleInterview(posid, candidateId, interview.getSlot(), interview.getPanel());
	}
	
	@GetMapping("/schedule/{posid}/{candidateId}")
	public List<Interview> getAllInterviews(@PathVariable short posid, @PathVariable String candidateId){
		return interviewService.getAllInterviews(posid, candidateId);
	}
	
	@GetMapping("/candidates/{posid}")
	public List<Candidate> getAllInterviewingCandidates(@PathVariable short posid){
		return interviewService.getAllInterviewingCandidates(posid);
	}
	
	@PostMapping("/feedback/{interviewId}")
	public int submitFeedback(@PathVariable int interviewId, @RequestBody String comments) {
		return interviewService.submitFeedback(interviewId, comments);
	}
	
	@GetMapping("/feedback/{candidateId}")
	public List<InterviewFeedback> getInterviewFeedback(@PathVariable int candidateId) {
		return interviewService.getInterviewFeedback(candidateId);
	}
}
