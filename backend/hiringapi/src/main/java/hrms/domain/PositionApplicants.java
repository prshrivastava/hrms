package hrms.domain;

import java.util.Set;

//import org.springframework.data.annotation.Id;

public class PositionApplicants {

	private String positionId;
	private Set<String> candidates;
	private Set<Applicant> shortlisted;
	private Set<Applicant> rejected;
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public Set<String> getCandidates() {
		return candidates;
	}
	public void setCandidates(Set<String> candidates) {
		this.candidates = candidates;
	}
	public Set<Applicant> getShortlisted() {
		return shortlisted;
	}
	public void setShortlisted(Set<Applicant> shortlisted) {
		this.shortlisted = shortlisted;
	}
	public Set<Applicant> getRejected() {
		return rejected;
	}
	public void setRejected(Set<Applicant> rejected) {
		this.rejected = rejected;
	}
	

}
