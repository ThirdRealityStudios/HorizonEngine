package org.thirdreality.evolvinghorizons.engine.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Polygon extends com.badlogic.gdx.math.Polygon
{
    private short[] triangles;

    private Vector2[] vectorVertices;

    private Line2D.Float[] lineShape;

    public Polygon(float[] vertices)
    {
        super(vertices);

        initData();
    }

    private void initData()
    {
        // Do NOT change the order here of the methods as the values calculated depend on each other!

        vectorVertices = calcVectorVertices();

        lineShape = toLine();

        triangles = calcTriangles();
    }

    @Override
    public void setVertices(float[] vertices)
    {
        super.setVertices(vertices);

        initData();
    }

    public Vector2[] getVectorVertices()
    {
        return vectorVertices;
    }

    private Vector2 center(Point2D a, Point2D b)
    {
        float aX = (float) a.getX();
        float aY = (float) a.getY();

        float bX = (float) b.getX();
        float bY = (float) b.getY();

        return new Vector2(aX + (bX - aX) / 2, aY + (bY - aY) / 2);
    }

    public float[] loadTriangleVertices(short[] triangle)
    {
        int tri0 = triangle[0]*2;
        int tri1 = triangle[1]*2;
        int tri2 = triangle[2]*2;

        float x0 = getVertices()[tri0];
        float y0 = getVertices()[tri0+1];
        float x1 = getVertices()[tri1];
        float y1 = getVertices()[tri1+1];
        float x2 = getVertices()[tri2];
        float y2 = getVertices()[tri2+1];

        return new float[]{x0,y0,x1,y1,x2,y2};
    }

    public com.badlogic.gdx.math.Polygon loadTriangle(short[] triangle)
    {
        float[] vertices = loadTriangleVertices(triangle);

        return new com.badlogic.gdx.math.Polygon(vertices);
    }

    public Line2D[] loadTriangleLine(short[] triangle)
    {
        float[] vertices = loadTriangleVertices(triangle);

        Line2D lineAB = new Line2D.Float(vertices[0],vertices[1],vertices[2],vertices[3]);
        Line2D lineBC = new Line2D.Float(vertices[2],vertices[3],vertices[4],vertices[5]);
        Line2D lineCA = new Line2D.Float(vertices[4],vertices[5],vertices[0],vertices[1]);

        return new Line2D[]{lineAB, lineBC, lineCA};
    }

    private boolean isValidLine(Line2D triangleLine)
    {
        int intersections = 0;

        for(Line2D polygonLine : lineShape)
        {
            if(triangleLine.intersectsLine(polygonLine))
            {
                intersections++;
            }
        }

        switch(intersections)
        {
            case 3:
            {
                // Valid triangle
                break;
            }
            case 4:
            {
                // Triangle (line) could be placed valid but needs to be checked by the condition whether
                // the center point of the current triangle line is contained by the polygon.

                Vector2 lineCenterPoint = center(triangleLine.getP1(), triangleLine.getP2());

                if(!contains(lineCenterPoint))
                {
                    // If it isn't contained that means the center point is outside the polygon.
                    // That means on the other hand that the triangle was calculated outside the polygon (it was connected / calculated wrong).
                    return false;
                }

                break;
            }

            default:
            {
                // Return 'false' which means the triangle crosses 1,2,5 or more polygon lines.
                // Hence that means the checked triangle is invalid.
                return false;
            }
        }

        return true;
    }

    public boolean contains(short[] triangle)
    {
        com.badlogic.gdx.math.Polygon triangleShape = loadTriangle(triangle);

        Line2D[] lineTriangle = loadTriangleLine(triangle);

        Line2D[] linePolygon = getLineShape();

        boolean lineAValid = isValidLine(lineTriangle[0]);
        boolean lineBValid = isValidLine(lineTriangle[1]);
        boolean lineCValid = isValidLine(lineTriangle[2]);

        // When the loop was not broken or interrupted, that means there was no invalidation.
        // In this case, if the conditions within the loops have always been valid,
        // the checked triangle is correct.
        return lineAValid && lineBValid && lineCValid;
    }

    private Line2D.Float[] toLine()
    {
        Vector2[] vertices = getVectorVertices();

        Line2D.Float[] lines = new Line2D.Float[vertices.length];

        Vector2 a;
        Vector2 b;

        for(int i = 0; (i+1) < vertices.length; i++)
        {
            a = new Vector2(vertices[i]);
            b = new Vector2(vertices[i+1]);

            lines[i] = new Line2D.Float(a.x, a.y, b.x, b.y);
        }

        // Always connect the last two points of the polygon to close it.
        a = new Vector2(vertices[vertices.length-1]);
        b = new Vector2(vertices[0]);

        lines[lines.length-1] = new Line2D.Float(a.x, a.y, b.x, b.y);

        return lines;
    }

    public Line2D.Float[] getLineShape()
    {
        return lineShape;
    }

    private Vector2[] calcVectorVertices()
    {
        Vector2[] vectorVertices = new Vector2[getVertices().length/2];

        int pointer = 0;

        for(int i = 0; i < vectorVertices.length; i++)
        {
            vectorVertices[i] = new Vector2(getVertices()[pointer], getVertices()[pointer+1]);

            pointer += 2;
        }

        return vectorVertices;
    }

    private short getVertexIndex(int initialVertexIndex, int add, int maxVertexIndex)
    {
        return (short) ((initialVertexIndex + add) % maxVertexIndex);
    }

    // Calculates all triangles for this polygon, starting from the given position (vertex) to draw them all.
    // If nothing is returned (null), no combination of triangles was found to make up this polygon.
    // In this case, just try another starting point. If you yet tried every starting point, this polygon is simple too complex for this algorithm.
    // Remember, that most non-continuous polygons are too complex, e.g. having a circular-like shape is generally critical.
    private short[] calcTriangles(int initialVertexIndex)
    {
        System.out.println();
        Gdx.app.log("Triangle Calc.","i = " + initialVertexIndex);
        System.out.println();

        short[] triangles = new short[getVectorVertices().length*3];

        int maxVertex = getVectorVertices().length;

        short a = (short) (initialVertexIndex % maxVertex);
        short b = (short) ((initialVertexIndex + 1) % maxVertex);
        short c = (short) ((initialVertexIndex + 2) % maxVertex);

        int pointer = 0;

        for(int i = 0; i < maxVertex-2; i++)
        {
            triangles[pointer] = a;
            triangles[pointer+1] = b;
            triangles[pointer+2] = c;

            // Checks whether the currently calculated triangle of the polygon is not outside it.
            // In different words: the calculated triangle MUST be inside this polygon.
            if(!contains(new short[]{a,b,c}))
            {
                System.out.println();

                return null;
            }

            b += 1;
            b %= maxVertex;

            c += 1;
            c %= maxVertex;

            pointer += 3;
        }

        System.out.println();

        return triangles;
    }

    // Calculates the triangles for this polygon.
    // But note: too complex shapes (e.g. circular-like shapes) are not meant to be calculated with this method.
    // If you do this anyway, the algorithm could fail, so you might receive a NullPointerException / a value of 'null'.
    private short[] calcTriangles()
    {
        short[] triangles = new short[0];

        int maxVertex = getVectorVertices().length;

        for(int currentApproach = 0; currentApproach < maxVertex; currentApproach++)
        {
            triangles = calcTriangles(currentApproach);

            // See whether the calculation of the triangles for this polygon was successful.
            // In this case, the algorithm will just return the calculated set of triangles.
            if(triangles != null)
            {
                break;
            }
        }

        return triangles;
    }

    public short[] getTriangles()
    {
        return triangles;
    }
}
