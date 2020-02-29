package hrms.dao;

import java.util.List;

import hrms.domain.Postion;


public interface PositionDao {
	public Postion getPosition(short positionId);
	public List<Postion> getPositions();
	public Postion createPosition(Postion position);
	public Postion updatePosition(Postion position);
}
