package ch.nostromo.brosapi.bot;


import ch.nostromo.brosapi.api.input.sprites.EnemySprite;
import ch.nostromo.brosapi.api.input.sprites.BlockSprite;
import ch.nostromo.brosapi.api.input.BrosApiInput;
import ch.nostromo.brosapi.api.input.BrosApiInputListener;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GamePane extends Pane implements BrosApiInputListener {

	int boxWidth = 16;
	int boxHeight = 16;

	int marioWidth = boxWidth;
	int marioHeight = boxHeight * 2;

	int backGroundWidth = 16 * boxWidth + 16;
	int backGroundHeight = 15 * boxHeight + 16;

	public GamePane() {
		setMinHeight(400);
        setMinWidth(400);
	}
	
	@Override
	public void botInput(BrosApiInput botInput) {
		

		Platform.runLater(new Runnable() {
		    public void run() {

		    	getChildren().clear();

		    	Rectangle backGround = new Rectangle(0, 0, backGroundWidth, backGroundHeight);
		    	backGround.setFill(Color.BLUE);
				getChildren().add(backGround);

				for (BlockSprite tile : botInput.getBlocks()) {
					Rectangle rectangle = new Rectangle(tile.getX(), tile.getY(), boxWidth, boxHeight);
					rectangle.setFill(Color.LIGHTCORAL);
					getChildren().add(rectangle);
				}

				for (EnemySprite enemy : botInput.getEnemies()) {
					Rectangle rectangle = new Rectangle(enemy.getX(), enemy.getY(), boxWidth, boxHeight);
					rectangle.setFill(Color.RED);
					getChildren().add(rectangle);
				}

				Rectangle mario = new Rectangle(botInput.getMario().getX(), botInput.getMario().getY(), marioWidth, marioHeight);
				mario.setFill(Color.GREEN);
				getChildren().add(mario);

			}
		});

	}
	
}
