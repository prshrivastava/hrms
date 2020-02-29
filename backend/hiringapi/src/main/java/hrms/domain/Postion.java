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
		
}
