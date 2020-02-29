package hrms.domain;


public class Candidate {
	private String id;
	private String name;
	private boolean referral;
	private String referrer;
	private String resumeLink;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isReferral() {
		return referral;
	}
	public void setReferral(boolean referral) {
		this.referral = referral;
	}
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public String getResumeLink() {
		return resumeLink;
	}
	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}
	@Override
	public String toString() {
		return "Candidate [id=" + id + ", name=" + name + ", referral=" + referral + ", referrer=" + referrer
				+ ", resumeLink=" + resumeLink + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (referral ? 1231 : 1237);
		result = prime * result + ((referrer == null) ? 0 : referrer.hashCode());
		result = prime * result + ((resumeLink == null) ? 0 : resumeLink.hashCode());
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
		Candidate other = (Candidate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (referral != other.referral)
			return false;
		if (referrer == null) {
			if (other.referrer != null)
				return false;
		} else if (!referrer.equals(other.referrer))
			return false;
		if (resumeLink == null) {
			if (other.resumeLink != null)
				return false;
		} else if (!resumeLink.equals(other.resumeLink))
			return false;
		return true;
	}
	
}
