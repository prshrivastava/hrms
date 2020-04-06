package hrms.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Interview {
	
	private String id;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date slot;
	private String panel;
	private InterviewState state;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date  getSlot() {
		return slot;
	}
	public void setSlot(Date  slot) {
		this.slot = slot;
	}
	public String getPanel() {
		return panel;
	}
	public void setPanel(String panel) {
		this.panel = panel;
	}
	public InterviewState getState() {
		return state;
	}
	public void setState(InterviewState state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Interview [id=" + id + ", slot=" + slot + ", panel=" + panel + ", state=" + state + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Interview other = (Interview) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

