package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hrms.domain.*;

@Component
public class FeedbackDaoImpl implements FeedbackDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static String insertComments = "insert into feedback (interview_id, comments) values (?,?)";
	private static String queryInterviewFeedback = "select i.id, i.slot, i.panel, i.state, f.submitted_on,"
			+ " f.comments from interview as i inner join feedback as f on i.id = f.interview_id"
			+ " where i.candidate_id=? order by f.submitted_on";

	@Override
	@Transactional
	public int submitFeedback(int interviewId, String comments) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(insertComments, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, interviewId);
			ps.setString(2, comments);
			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public List<InterviewFeedback> getFeedback(int candidateId) {
		return jdbcTemplate.query(queryInterviewFeedback, new InterviewFeedbackExtractor(), candidateId);
	}

}

class InterviewFeedbackExtractor implements ResultSetExtractor<List<InterviewFeedback>> {
	private Map<Integer, Interview> interviewMap;
	private Map<Interview, List<Feedback>> feedbackMap;

	public InterviewFeedbackExtractor() {
		interviewMap = new HashMap<Integer, Interview>();
		feedbackMap = new HashMap<Interview, List<Feedback>>();
	}

	@Override
	public List<InterviewFeedback> extractData(ResultSet rs) throws SQLException {
		while (rs.next()) {
			Interview interview = mapInterview(rs);
			List<Feedback> feedback = feedbackMap.get(interview);
			if (feedback == null) {
				feedback = new ArrayList<Feedback>();
			}
			feedback.add(mapFeedback(rs));
			feedbackMap.put(interview, feedback);
		}
		List<InterviewFeedback> interviewFeedbackList = new ArrayList<InterviewFeedback>();
		for(Interview i: feedbackMap.keySet()) {
			InterviewFeedback invfback = new InterviewFeedback();
			invfback.setInterview(i);
			invfback.setFeedback(feedbackMap.get(i));
			interviewFeedbackList.add(invfback);
		}
		return interviewFeedbackList;
	}

	private Feedback mapFeedback(ResultSet rs) throws SQLException {
		Feedback f = new Feedback();
		f.setComments(rs.getString("comments"));
		f.setSubmittedOn(rs.getDate("submitted_on"));
		return f;
	}

	private Interview mapInterview(ResultSet rs) throws SQLException {
		Interview interview = interviewMap.get(rs.getInt("id"));
		if (interview == null) {
			Interview i = new Interview();
			i.setId(rs.getString("id"));
			i.setPanel(rs.getString("panel"));
			i.setSlot(new Date(rs.getTimestamp("slot").getTime()));
			i.setState(InterviewState.valueOf(rs.getString("state")));

			interviewMap.put(rs.getInt("id"), i);
			return i;
		} else
			return interview;
	}

}
