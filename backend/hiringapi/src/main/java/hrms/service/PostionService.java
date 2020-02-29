package hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hrms.dao.PositionDao;
import hrms.domain.Postion;

@Service
public class PostionService {
	
	@Autowired
	private PositionDao positionDao;
	
	public List<Postion> getPositions() {
		return positionDao.getPositions();
	}
	public Postion getPosition(short positionId) {
		return positionDao.getPosition(positionId);
	}
	public Postion createPosition(Postion position) {
		System.out.println("Before "+ position);
		Postion p = positionDao.createPosition(position);
		System.out.println("After "+ p);
		return p;
	}
	public Postion savePosition(Postion position) {
		return positionDao.updatePosition(position);
	}

}
