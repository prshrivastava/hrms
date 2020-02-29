package hrms.domain;

import java.util.Date;

public class Applicant {

	private String candidateId;
	private Date processedOn;
	
	public Applicant(String c) {
		this.candidateId = c;
		processedOn = new Date();
	}
	
	public String getCandidateId() {
		return candidateId;
	}

	public Date getProcessedOn() {
		return processedOn;
	}
	
	@Override
	public String toString() {
		return "Applicant [candidateId=" + candidateId + ", processedOn=" + processedOn + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Applicant other = (Applicant) obj;
		if (candidateId == null) {
			if (other.candidateId != null)
				return false;
		} else if (!candidateId.equals(other.candidateId))
			return false;
		return true;
	}
	
}
