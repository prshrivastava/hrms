package hrms.domain;



public class Postion {
	
	private short id;
	private String name;
	private String skills;
	private String experienceRange;
	private String hiringManager;
	private short priority;
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getExperienceRange() {
		return experienceRange;
	}
	public void setExperienceRange(String experienceRange) {
		this.experienceRange = experienceRange;
	}
	public String getHiringManager() {
		return hiringManager;
	}
	public void setHiringManager(String hiringManager) {
		this.hiringManager = hiringManager;
	}
	public short getPriority() {
		return priority;
	}
	public void setPriority(short priority) {
		this.priority = priority;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Postion [id=" + id + ", name=" + name + ", skills=" + skills + ", experienceRange=" + experienceRange
				+ ", hiringManager=" + hiringManager + ", priority=" + priority + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((experienceRange == null) ? 0 : experienceRange.hashCode());
		result = prime * result + ((hiringManager == null) ? 0 : hiringManager.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + priority;
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Postion other = (Postion) obj;
		if (experienceRange == null) {
			if (other.experienceRange != null)
				return false;
		} else if (!experienceRange.equals(other.experienceRange))
			return false;
		if (hiringManager == null) {
			if (other.hiringManager != null)
				return false;
		} else if (!hiringManager.equals(other.hiringManager))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority != other.priority)
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		return true;
	}
	
		
}
