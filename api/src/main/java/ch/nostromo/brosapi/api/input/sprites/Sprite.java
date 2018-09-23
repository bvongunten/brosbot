package ch.nostromo.brosapi.api.input.sprites;

public abstract class Sprite {

    int x;
    int y;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
