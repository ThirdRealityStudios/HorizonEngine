package org.thirdreality.evolvinghorizons.engine.math;

public class LinSolve
{
    public static int solveX(int m1, int b1, int m2, int b2)
    {
        return (m1 > m2) ? ((b2-b1)/(m1-m2)) : ((b1-b2)/(m2-m1));
    }
}
