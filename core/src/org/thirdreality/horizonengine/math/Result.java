package org.thirdreality.horizonengine.math;

public class Result<ResultType>
{
    public final boolean isInfinite;

    // The result is undefined by default if not changed.
    private ResultType result = null;

    public Result(boolean isInfinite)
    {
        this.isInfinite = isInfinite;
    }

    public void setResult(ResultType result)
    {
        if(!isInfinite)
        {
            this.result = result;
        }
    }

    public ResultType getResult()
    {
        return result;
    }

    public boolean isUndefined()
    {
        return result == null && !isInfinite;
    }
}
