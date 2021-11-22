package santaJam;

public class SantaJam {
	private static Game game;
	public static void main(String[] args) {
		game = new Game();
		game.run();
	}
	public static Game getGame() {
		return game;
	}
	
}
