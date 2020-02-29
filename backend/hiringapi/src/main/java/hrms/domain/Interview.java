package hrms.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Interview {
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date slot;
	private String panel;
	private InterviewState state;
	
	
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
		return "Interview [slot=" + slot + ", panel=" + panel + ", state=" + state + "]";
	}
}

