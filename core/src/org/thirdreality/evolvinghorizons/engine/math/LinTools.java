package org.thirdreality.evolvinghorizons.engine.math;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Line2D;

public class LinTools
{
    // Calculates the time when both linear functions intersect each other.
    // If there is no intersection, null is returned.
    public static Float solveX(float m1, float b1, float m2, float b2)
    {
        Integer result = 0;

        float m = 0;

        boolean divisionByZero = false;

        if(m1 > m2)
        {
            m = m1-m2;

            divisionByZero = m == 0;

            if(divisionByZero)
            {
                return null;
            }

            return (b2-b1)/m;
        }
        else
        {
            m = m2-m1;

            divisionByZero = m == 0;

            if(divisionByZero)
            {
                return null;
            }

            return (b1-b2)/m;
        }
    }

    // Returns the crossing / intersection point for both lines, and 'null' if there is none.
    public static Vector2 getCrossingVector(Line2D.Float line0, Line2D.Float line1)
    {
        float m1 = line0.y2 - line0.y1;
        float b1 = (line0.y2 > line0.y1) ? line0.y1 : line0.y2;

        float m2 = line1.y2 - line1.y1;
        float b2 = (line1.y2 > line1.y1) ? line1.y1 : line1.y2;

        Float x = solveX(m1, b1, m2, b2);

        return (x != null) ? new Vector2(x,(m1*x+b1)) : null;
    }

    // Tells you whether two lines cross each other.
    // In difference to their internal method intersectsLine(...) this method will consider their start and end points,
    // meaning it considers only intersections between them.
    public static boolean crossingEachOther(Line2D.Float line0, Line2D.Float line1)
    {
        return getCrossingVector(line0, line1) != null;
    }
}
