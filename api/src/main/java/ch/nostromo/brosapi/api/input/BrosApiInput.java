package ch.nostromo.brosapi.api.input;

import ch.nostromo.brosapi.api.input.sprites.EnemySprite;
import ch.nostromo.brosapi.api.input.sprites.MarioSprite;
import ch.nostromo.brosapi.api.input.sprites.BlockSprite;

import java.util.List;

public class BrosApiInput {

	MarioSprite mario;
	List<BlockSprite> blocks;
	List<EnemySprite> enemies;

	public BrosApiInput(MarioSprite mario, List<BlockSprite> blocks, List<EnemySprite> enemies) {
		this.mario = mario;
		this.blocks = blocks;
		this.enemies = enemies;
	}

	public MarioSprite getMario() { return mario; }

	public List<BlockSprite> getBlocks() { return blocks; }

	public List<EnemySprite> getEnemies() { return enemies; }

}
