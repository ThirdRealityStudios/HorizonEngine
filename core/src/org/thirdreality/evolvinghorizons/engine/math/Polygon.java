package org.thirdreality.evolvinghorizons.engine.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

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

    public Line2D.Float[] loadTriangleLine(short[] triangle)
    {
        float[] vertices = loadTriangleVertices(triangle);

        Line2D.Float lineAB = new Line2D.Float(vertices[0],vertices[1],vertices[2],vertices[3]);
        Line2D.Float lineBC = new Line2D.Float(vertices[2],vertices[3],vertices[4],vertices[5]);
        Line2D.Float lineCA = new Line2D.Float(vertices[4],vertices[5],vertices[0],vertices[1]);

        return new Line2D.Float[]{lineAB, lineBC, lineCA};
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



    private short[][] buildCombination()
    {
        short[][] combination = new short[getVectorVertices().length][2];

        int maxVertex = (short) getVectorVertices().length;

        for(short i = 0; i < combination.length; i++)
        {
            combination[i][0] = (short) (i % maxVertex);
            combination[i][1] = ((short) ((i+1) % maxVertex));
        }

        return combination;
    }

    private Line2D.Float getTriangleLine(short[] triangle)
    {
        Line2D.Float[] lineTriangle = loadTriangleLine(triangle);

        Point2D.Float[] centerPointTriangle = new Point2D.Float[3];

        for(int i = 0; i < lineTriangle.length; i++)
        {
            Line2D triangleLine = lineTriangle[i];

            Vector2 centerPoint = center(triangleLine.getP1(), triangleLine.getP2());

            // Conversion
            centerPointTriangle[i] = new Point2D.Float(centerPoint.x, centerPoint.y);
        }

        // Erase the point (Point2D) which is not contained in this polygon.
        // The remaining point (only one) will then represent the line which builds up the full triangle.
        for(Line2D.Float shapeLine : getLineShape())
        {
            if(shapeLine.contains(centerPointTriangle[0]))
            {
                centerPointTriangle[0] = null;
            }

            if(shapeLine.contains(centerPointTriangle[1]))
            {
                centerPointTriangle[1] = null;
            }

            if(shapeLine.contains(centerPointTriangle[2]))
            {
                centerPointTriangle[2] = null;
            }
        }

        int lineIndex = -1;

        // Find the line which represents the inner triangle line.
        for(int i = 0; i < centerPointTriangle.length; i++)
        {
            if(centerPointTriangle[i] != null)
            {
                lineIndex = i;
            }
        }

        Line2D.Float lineCenterPoint = lineTriangle[lineIndex];

        return lineCenterPoint;
    }

    // Calculates the triangles for this polygon.
    // But note: too complex shapes (e.g. circular-like shapes) are not meant to be calculated with this method.
    // If you do this anyway, the algorithm could fail, so you might receive a NullPointerException / a value of 'null'.
    private short[] calcTriangles()
    {
        short[][] combination = buildCombination();

        int maxVertex = (short) getVectorVertices().length;

        ArrayList<Line2D.Float> validTriangles = new ArrayList<Line2D.Float>();

        // Try to find a valid triangle for each combination.
        for(int i = 0; i < maxVertex; i++)
        {
            short b = combination[i][0];
            short c = combination[i][1];

            // To do so, combine 'b' & 'c' with a value chosen for 'a' in the loop below.
            for(short a = 0; a < maxVertex; a++)
            {
                short[] triangleCombined = new short[]{a,b,c};

                Line2D.Float l0 = new Line2D.Float(0,-2,3,4.5f);
                Line2D.Float l1 = new Line2D.Float(0,0,4,4);
                Line2D.Float l2 = new Line2D.Float(0,-2,4,2);

                System.out.println("l0 -> l1: " + LinTools.crossingEachOther(l0,l1));
                System.out.println("l1 -> l2: " + LinTools.crossingEachOther(l1,l2));
                System.out.println("l0 -> l2: " + LinTools.crossingEachOther(l0,l2));

                /*
                Line2D.Float triangleLine = getTriangleLine(triangleCombined);

                triangleLine.

                // Remember the current line of the triangle
                validTriangles.add(triangleLine);

                boolean isValidTriangle = contains(triangleCombined) && ;
                 */

                /*
                if(a == x && b == y && c == z)
                System.out.println("> " + triangleLine.getP1() + " - " + triangleLine.getP2());
                 */
            }
        }

        triangles = new short[]{0,1,2};

        /*
        for(short[] s : combination)
        {
            System.out.print("> b = " + s[0] + " | c = " + s[1]);
            System.out.println();
        }
         */

        /*
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
         */

        return triangles;
    }

    public short[] getTriangles()
    {
        return triangles;
    }
}
