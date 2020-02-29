package hrms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hrms.domain.Postion;
import hrms.service.PostionService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class PositionController {
	
	@Autowired
	private PostionService positionService;
	
	@GetMapping("/positions")
	public List<Postion> getPositions() {
		return positionService.getPositions();
	}
	
	@GetMapping("/positions/{id}")
	public Postion getPosition(@PathVariable short id) {
		return positionService.getPosition(id);
	}
	
	@PostMapping("/positions")
	public Postion createPosition(@RequestBody Postion position) {
		return positionService.createPosition(position);
	}
	@PutMapping("positions")
	public Postion updatePosition(@RequestBody Postion position) {
		return positionService.savePosition(position);
	}
}
