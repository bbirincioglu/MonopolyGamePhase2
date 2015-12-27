package domain;

public class NearestRailRoad {
	public NearestRailRoad() {
		
	}
	
	public void picked(Player player) {
		while (!(player.getCurrentLocation() instanceof RailRoadSquare)) {
			player.move(1);
		}
	}
}
