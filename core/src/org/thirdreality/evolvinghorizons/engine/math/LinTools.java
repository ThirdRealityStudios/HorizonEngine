package org.thirdreality.evolvinghorizons.engine.math;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LinTools
{
    // Calculates the time when both linear functions intersect each other.
    // If there is no intersection, null is returned.
    // In this manner there is no intersection when both functions are the same or
    // they simply won't cross each other.
    public static Result<Float> solveX(float m1, float b1, float m2, float b2)
    {
        Result<Float> result;

        float m = 0;

        boolean divisionByZero = false;

        if(m1 > m2)
        {
            m = m1-m2;

            divisionByZero = m == 0;

            if(divisionByZero)
            {
                return new Result<Float>(false);
            }

            result = new Result<Float>(false);
            result.setResult((b2-b1)/m);

            return result;
        }
        else if(m1 == m2 && b1 == b2)
        {
            result = new Result<Float>(true);

            return result;
        }
        else
        {
            m = m2-m1;

            divisionByZero = m == 0;

            if(divisionByZero)
            {
                return new Result<Float>(false);
            }

            result = new Result<Float>(false);
            result.setResult((b1-b2)/m);

            return result;
        }
    }

    // Determines the steepness of a line.
    // If the line goes straight up (meaning the steepness is infinite),
    // the method will cause an error due to division by zero.
    public static float getSteepness(Line2D.Float line)
    {
        return (line.y2 - line.y1) / (line.x2 - line.x1);
    }

    // Returns the intersection point for both lines, and 'null' if there is none.
    public static Result<Vector2> getIntersectionVector(Line2D.Float line0, Line2D.Float line1)
    {
        float m1 = getSteepness(line0);
        float b1 = line0.y1;

        float m2 = getSteepness(line1);
        float b2 = line1.y1;

        Result<Float> x = solveX(m1, b1, m2, b2);

        Result<Vector2> resultVector = new Result<Vector2>(x.isInfinite);

        if(x.getResult() != null)
        {
            resultVector.setResult(new Vector2(x.getResult(), m1*x.getResult() + b1));
        }

        return resultVector;
    }

    private static boolean isContained(Vector2 vertex, Vector2[] container)
    {
        boolean contains = false;

        for(Vector2 comparedVertex : container)
        {
            contains |= (vertex.x == comparedVertex.x && vertex.y == comparedVertex.x);
        }

        return contains;
    }

    private static boolean isContained(Vector2 vertex, Line2D.Float[] container)
    {
        boolean contains = false;

        for(Line2D.Float comparedLine : container)
        {
            contains |= (comparedLine.contains(vertex.x, vertex.y));
        }

        return contains;
    }

    public static boolean intersects(Line2D.Float line, ArrayList<Line2D.Float> container)
    {
        boolean intersects = false;

        for(Line2D.Float comparedLine : container)
        {
            intersects |= (line.intersectsLine(comparedLine));
        }

        return intersects;
    }

    private static boolean isContained(Vector2 vertex, Polygon container)
    {
        return container.contains(vertex);
    }

    private static boolean equals(Point2D point0, Point2D point1)
    {
        return point0.getX() == point1.getX() && point0.getY() == point1.getY();
    }

    // Tells you whether two lines intersect each other.
    // In difference to their internal method intersectsLine(...) this method will consider their start and end points,
    // meaning it considers only intersections between them.
    public static boolean intersectsIgnoreEnds(Line2D.Float line0, Line2D.Float line1)
    {
        Vector2 result = getIntersectionVector(line0, line1).getResult();

        if(result == null)
        {
            return false;
        }

        boolean leftEquals = equals(line0.getP1(), line1.getP1());
        boolean rightEquals = equals(line0.getP2(), line1.getP2());

        boolean insideBounds = true;

        if(leftEquals || rightEquals || !line0.getBounds().contains(result.x, result.y) && !line1.getBounds().contains(result.x, result.y))
        {
            return false;
        }

        boolean resultExists = result != null;

        return resultExists;
    }

    public static boolean intersectsIgnoreEnds(Line2D.Float line, ArrayList<Line2D.Float> lines)
    {
        boolean intersects = false;

        for(Line2D.Float comparedLine : lines)
        {
            intersects |= intersectsIgnoreEnds(line, comparedLine);
        }

        return intersects;
    }
}