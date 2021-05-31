package com.samet.fruitgame;

import com.badlogic.gdx.math.Vector2;

public class Fruit {

    public static float radius = 60f;
    Type type;
    Vector2 pos, velocity;

    Fruit(Vector2 pos, Vector2 velocity) {
        this.pos = pos;
        this.velocity = velocity;
        type = Type.REGULAR;

    }

    public boolean clicked(Vector2 click) {

        return pos.dst2(click) <= radius * radius + 1;
    }

    public final Vector2 getPos() {
        return pos;
    }

    public boolean outOfScreen() {
        return (pos.y < -2f * radius);
    }

    public void update(float dt) {
        pos.mulAdd(velocity, dt);
    }

    public enum Type {
        REGULAR, EXTRA, ENEMY, LIFE
    }


}
