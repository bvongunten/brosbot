package ch.nostromo.brosapi.api;

import java.util.ArrayList;
import java.util.List;

import ch.nostromo.brosapi.api.input.sprites.BlockSprite;
import ch.nostromo.brosapi.api.input.BrosApiInput;
import ch.nostromo.brosapi.api.input.BrosApiInputListener;
import ch.nostromo.brosapi.api.input.sprites.EnemySprite;
import ch.nostromo.brosapi.api.input.sprites.MarioSprite;
import nintaco.api.API;
import nintaco.api.ApiSource;

public class BrosApi extends Thread {

	private API api ;

	private BrosApiInputListener listener;

	public void setListener (BrosApiInputListener listener) {
	    this.listener = listener;
    }

	public void launchApi() {
        ApiSource.initRemoteAPI("localhost", 9999);

        api = ApiSource.getAPI();

        start();
    }

	public void run(){
		api.addFrameListener(this::frameRendered);
		api.run();
	}

    private void frameRendered() {
        listener.botInput(createInput());
    }


    public API getAPI() {
	    return api;
    }


	private BrosApiInput createInput() {

        // Mario position on current screen
        int marioScreenX = api.readCPU(0x03AD);
        int marioScreenY = api.readCPU(0x03B8);

        // TODO: Improve level offset in enemies & blocks methods
        // Horizontal level offset to Mario
		int xOffset = api.readCPU(0x6D) * 0x100 + api.readCPU(0x86) - marioScreenX;

        // Mario
    	MarioSprite marioSprite = new MarioSprite(marioScreenX, marioScreenY);

    	// Enemies
		List<EnemySprite> enemies = getEnemies(xOffset);

        // Blocks
        List<BlockSprite> blocks = getBlocks(xOffset);

		return new BrosApiInput(marioSprite, blocks, enemies);
	}


    private List<EnemySprite> getEnemies(int xOffset) {
        List<EnemySprite> result = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int enemy = api.readCPU(0xF + i);
            if (enemy != 0) {
                int enemyX = api.readCPU(0x6E + i) * 0x100 + api.readCPU(0x87 + i) - xOffset;
                int enemyY = api.readCPU(0xCF + i) + 8;

                result.add(new EnemySprite(enemyX, enemyY));
            }
        }

        return result;
    }

	private List<BlockSprite> getBlocks(int xOffset) {
        List<BlockSprite> tiles = new ArrayList<>();

        // Blocks move trough scrolling too ... offset from left border
        int xTileOffSet = 16 - (api.readCPU(0x071C) % 16);

        for (int inputX = 0; inputX < 16; inputX++) {
            for (int inputY = 0; inputY < 15; inputY++) {

                int coordX = ((inputX + 1) * 16) + xOffset;
                int coordY = inputY * 16;

                if (isBlock(coordX, coordY)) {
                    tiles.add(new BlockSprite(inputX * 16 + xTileOffSet, inputY * 16));
                }

            }
        }

        return tiles;

    }


	private boolean isBlock(int x, int y) {

		double page = Math.floor(x / 256) % 2;
		double subx = Math.floor((x % 256) / 16);
		double suby = Math.floor((y - 32) / 16);

		double addr = 0x500 + page * 13 * 16 + suby * 16 + subx;

		if (suby >= 13 || suby < 0) {
			return false;
		}

		return api.readCPU((int) addr) != 0;
	}

}
