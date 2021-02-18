package org.thirdreality.evolvinghorizons.engine.math;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Polygon extends com.badlogic.gdx.math.Polygon
{
    private short[] triangles;

    private Vector2[] vectorVertices;

    private ArrayList<Line2D.Float> lineShape;

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

        prepareForRendering();
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

        ArrayList<Line2D.Float> linePolygon = getLineShape();

        boolean lineAValid = isValidLine(lineTriangle[0]);
        boolean lineBValid = isValidLine(lineTriangle[1]);
        boolean lineCValid = isValidLine(lineTriangle[2]);

        // When the loop was not broken or interrupted, that means there was no invalidation.
        // In this case, if the conditions within the loops have always been valid,
        // the checked triangle is correct.
        return lineAValid && lineBValid && lineCValid;
    }

    private ArrayList<Line2D.Float> toLine()
    {
        Vector2[] vertices = getVectorVertices();

        ArrayList<Line2D.Float> lines = new ArrayList<Line2D.Float>(vertices.length);

        Vector2 a;
        Vector2 b;

        for(int i = 0; (i+1) < vertices.length; i++)
        {
            a = new Vector2(vertices[i]);
            b = new Vector2(vertices[i+1]);

            lines.add(new Line2D.Float(a.x, a.y, b.x, b.y));
        }

        // Always connect the last two points of the polygon to close it.
        a = new Vector2(vertices[vertices.length-1]);
        b = new Vector2(vertices[0]);

        lines.add(new Line2D.Float(a.x, a.y, b.x, b.y));

        return lines;
    }

    public ArrayList<Line2D.Float> getLineShape()
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

    public boolean isPolyLine(Line2D.Float line)
    {
        for(Line2D.Float comparedLine : getLineShape())
        {
            boolean hasSameSteepness = LinTools.getSteepness(comparedLine) == LinTools.getSteepness(line);

            if(line.intersectsLine(comparedLine) && hasSameSteepness)
            {
                return true;
            }
            /*
            if(comparedLine.getBounds().equals(line.getBounds()))
            {
                return true;
            }
             */
        }

        return false;
    }

    public boolean isLineCenterPointInsidePolygon(Line2D.Float line, com.badlogic.gdx.math.Polygon poly)
    {
        return poly.contains(center(line.getP1(), line.getP2()));
    }

    private boolean intersectsTriangleLines(Line2D.Float triangleLine, ArrayList<Line2D.Float> triangleLines)
    {
        return LinTools.intersects_IgnoreEndsAndIntersectionPointsBetweenLines(triangleLine, triangleLines);
    }

    private Short[] writeVertexConnections(short a, ArrayList<Short> vertexConnections)
    {
        Short[] vertexConnectionsWritten = new Short[vertexConnections.size()+1];

        for(int i = 1; i < vertexConnectionsWritten.length; i++)
        {
            vertexConnectionsWritten[i] = vertexConnections.get(i-1);
        }

        return vertexConnectionsWritten;
    }

    // Checks whether two vertex lines refer to the same direction.
    // This prevents double triangles.
    // Used in the algorithm to determine all triangles for a polygon correctly.
    private boolean isDoubleConnection(ArrayList<Short> a, ArrayList<Short> b)
    {
        return (a.size() == 2 && b.size() == 2 && a.get(0) == b.get(1) && a.get(1) == b.get(0)) || (a.size() == 1 && b.size() == 1 && a.get(0) == b.get(0));
    }

    private Short[] toArray(ArrayList<Short> list)
    {
        Short[] array = new Short[list.size()];

        for(int i = 0; i < array.length; i++)
        {
            array[i] = list.get(i);
        }

        return array;
    }

    private Short[][] removeDoublesAndPrepare(ArrayList<ArrayList<Short>> connections)
    {
        // Removes double appearances from the connected vertices.
        for(short a = 0; a < connections.size(); a++)
        {
            for(int b = 0; b < connections.size(); b++)
            {
                if(isDoubleConnection(connections.get(a), connections.get(b)))
                {
                    connections.remove(b);
                }
            }
        }

        // The remaining part will convert the connections to a better readable format.
        // The new format is then used to construct every triangle by a mathematic method.

        ArrayList<ArrayList<Short>> buffer = null;

        int size = 0;

        // Initialize the buffer with the correct size and add the nodes to the beginning.
        {
            buffer = new ArrayList<ArrayList<Short>>();

            size = connections.get(connections.size() - 1).get(0) + 1;

            for (int c = 0; c < size; c++)
            {
                buffer.add(new ArrayList<Short>());
                buffer.get(c).add((short) c);
            }
        }

        // Add the connections to the according nodes.
        for(int i = 0; i < connections.size(); i++)
        {
            int writeIndex = connections.get(i).get(0);

            for(int content = 1; content < connections.get(i).size(); content++)
            {
                short n = connections.get(i).get(1);

                buffer.get(writeIndex).add(n);
            }
        }

        Short[][] array = new Short[buffer.size()][];

        for(int i = 0; i < array.length; i++)
        {
            ArrayList<Short> content = buffer.get(i);

            array[i] = new Short[content.size()];

            for(int arrPtr = 0; arrPtr < array[i].length; arrPtr++)
            {
                array[i][arrPtr] = buffer.get(i).get(arrPtr);
            }
        }

        return array;
    }

    private Short[][] connectVertices()
    {
        if(getVectorVertices().length == 3)
        {
            Short[][] triangle = new Short[3][1];

            triangle[0][0] = 0;
            triangle[1][0] = 1;
            triangle[2][0] = 2;

            return triangle;
        }

        if(getVectorVertices().length == 4)
        {
            Short[][] triangle = new Short[4][1];

            triangle[0][0] = 0;

            triangle[1][0] = 1;

            triangle[2] = new Short[]{2,0};

            triangle[3][0] = 3;

            return triangle;
        }

        ArrayList<ArrayList<Short>> connections = new ArrayList<ArrayList<Short>>();

        ArrayList<Line2D.Float> triangleLines = new ArrayList<Line2D.Float>();

        // The double-loop just goes through all possibilities to build line which goes to every vertex point of the polygon.
        // This will make sense in order to check whether the constructed line can be used to build up a triangle later.
        for(short a = 0; a < getVectorVertices().length; a++)
        {
            // The loop-condition prevents the end point from being the start point (pointing at itself).
            for(short n = (short) ((a + 1) % getVectorVertices().length); n != a; n = (short) ((n + 1) % getVectorVertices().length))
            {
                Point2D.Float start = new Point2D.Float(getVectorVertices()[a].x, getVectorVertices()[a].y);
                Point2D.Float end = new Point2D.Float(getVectorVertices()[n].x, getVectorVertices()[n].y);

                Line2D.Float connection = new Line2D.Float(start, end);

                boolean intersectsPolyLines = LinTools.intersects_IgnoreEndsAndIntersectionPointsBetweenLines(connection, getLineShape());

                if(!isPolyLine(connection) && !intersectsPolyLines && isLineCenterPointInsidePolygon(connection, this) && !intersectsTriangleLines(connection, triangleLines))
                {
                    triangleLines.add(connection);

                    ArrayList<Short> list = new ArrayList<Short>();
                    list.add(a);
                    list.add(n);

                    connections.add(list);
                }
                else
                {
                    ArrayList<Short> list = new ArrayList<Short>();
                    list.add(a);

                    connections.add(list);
                }
            }
        }

        Short[][] connectionsPrepared = removeDoublesAndPrepare(connections);

        for(int i = 0; i < connectionsPrepared.length; i++)
        {
            System.out.print("[");

            for(int u = 0; u < connectionsPrepared[i].length; u++)
            {
                System.out.print(connectionsPrepared[i][u] + ",");
            }

            System.out.println("]");
        }

        return connectionsPrepared;
    }

    private void print(Short[] s)
    {
        System.out.print("[");

        for(Short sh : s)
        {
            System.out.print(sh + ",");
        }

        System.out.println("]");
    }

    private void prepareForRendering()
    {
        connectVertices();

        triangles = new short[]{0,1,2};
    }

    public short[] getTriangles()
    {
        return triangles;
    }
}
