package hrms.domain;

public class Applicant extends Candidate {

	private ApplicantState state;

	public ApplicantState getState() {
		return state;
	}

	public void setState(ApplicantState state) {
		this.state = state;
	}
	
}
