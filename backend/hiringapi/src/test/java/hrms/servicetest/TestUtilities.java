package hrms.servicetest;

import hrms.domain.Candidate;

public class TestUtilities {
	
	public static Candidate generateRandomCandidate() {
		Candidate c = new Candidate();
		long currentTime = System.currentTimeMillis();
		String name = Long.toString(currentTime);
		c.setName(name);
		c.setReferral(false);
		c.setResumeLink("http://drive/"+name);
		return c;
	}

}
