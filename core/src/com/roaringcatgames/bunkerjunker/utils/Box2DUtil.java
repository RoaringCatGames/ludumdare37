package com.roaringcatgames.bunkerjunker.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Helper Functions for Box2D operations
 */
public class Box2DUtil {

    public static Body buildBoxBody(World world, boolean isStatic, float x, float y, float w, float h, float angle, float density, float friction, float restitution){
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        // Set its world position
        groundBodyDef.position.set(new Vector2(x, y));
        groundBodyDef.angle = angle;

        // Create a body from the defintion and add it to the world
        Body body = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)

        groundBox.setAsBox(w, h);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = groundBox;

        // Create a fixture from our polygon shape and add it to our ground body
        body.createFixture(fixtureDef);


        // Clean up after ourselves
        groundBox.dispose();

        return body;
    }

    public static Body buildBallBody(World world, boolean isStatic, float x, float y, float radius,
                                     float density, float friction, float restitution){
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        // Set our body's starting position in the world
        bodyDef.position.set(x, y);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        return body;
    }

    public static Body buildChainBody(World world, float[] xVals, float[] yVals, float density, float friction, float restitution){
        BodyDef chainBody = new BodyDef();
        chainBody.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(chainBody);

        // Create our body definition
        ChainShape chainShape = new ChainShape();
        float[] vertices = new float[xVals.length*2];
        int v=0;
        for(int i=0;i<xVals.length;i++){
            vertices[v++] = xVals[i];
            vertices[v++] = yVals[i];
        }
        chainShape.createChain(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chainShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;


        // Create a fixture from our polygon shape and add it to our ground body
        body.createFixture(fixtureDef);

        // Clean up after ourselves
        chainShape.dispose();

        return body;
    }
}
