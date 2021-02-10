package org.thirdreality.evolvinghorizons.engine.math;

public class Polygon extends com.badlogic.gdx.math.Polygon
{
    short[] triangles;

    public Polygon(float[] vertices)
    {
        super(vertices);

        triangles = calcTriangles();
    }

    @Override
    public void setVertices(float[] vertices)
    {
        super.setVertices(vertices);

        triangles = calcTriangles();
    }

    private short[] calcTriangles()
    {
        short[] triangles = new short[getVertices().length-2];

        short a = 0, b = 1, c = 2;

        for(int i = 0; i < triangles.length; i += 3)
        {
            triangles[i] = a;
            triangles[i+1] = b;
            triangles[i+2] = c;

            System.out.println("b = " + b);
            System.out.println("c = " + c);

            b += 1;
            c += 1;
        }

        return triangles;
    }

    public short[] getTriangles()
    {
        return triangles;
    }
}
