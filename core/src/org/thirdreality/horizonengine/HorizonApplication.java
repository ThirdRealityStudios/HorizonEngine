package org.thirdreality.horizonengine;

import com.badlogic.gdx.Gdx;
import org.thirdreality.horizonengine.core.game.management.GameManager;
import org.thirdreality.horizonengine.core.console.trouble.Error;
import org.thirdreality.horizonengine.core.console.trouble.Troubleshooting;

public abstract class HorizonApplication
{
    // Will be initialized within the class HorizonEngine when starting a new HorizonApplication.
    public GameManager manager;

    private final String LOG_INFO = "INFO",
            LOG_WARNING = "WARNING",
            LOG_ERROR = "ERROR",
            LOG_ERROR_EXITING = "ERROR / EXITING",
            LOG_TROUBLESHOOTING = "TROUBLESHOOTING";

    public void info(String information)
    {
        Gdx.app.error(LOG_INFO, information);
    }

    public void warning(String warning)
    {
        Gdx.app.error(LOG_WARNING, warning);
    }

    public void error(Error error)
    {
        Gdx.app.error(LOG_ERROR + " | " + error.ordinal(), Error.getMessage(error));
    }

    public void error(Error error, String additionalInfo)
    {
        Gdx.app.error(LOG_ERROR + " | " + error.ordinal(), Error.getMessage(error) + " " + additionalInfo);
    }

    public void errorExit(Error error)
    {
        Gdx.app.error(LOG_ERROR_EXITING + " | " + error.ordinal(), Error.getMessage(error));
        exit();
    }

    public void errorExit(Error error, String additionalInfo)
    {
        Gdx.app.error(LOG_ERROR_EXITING + " | " + error.ordinal(), Error.getMessage(error) + " " + additionalInfo);
        exit();
    }

    public void troubleshoot(String hintOrAdvice)
    {
        Gdx.app.error(LOG_TROUBLESHOOTING, hintOrAdvice);
    }

    public void troubleshoot(Troubleshooting troubleshooting)
    {
        troubleshoot(Troubleshooting.getHelp(troubleshooting));
    }

    public void errorExit(Error error, Troubleshooting troubleshooting)
    {
        Gdx.app.error(LOG_ERROR_EXITING, Error.getMessage(error));
        troubleshoot(troubleshooting);
        exit();
    }

    public void exit()
    {
        post();

        Gdx.app.exit();
    }

    protected abstract void pre();

    protected abstract void loop();

    protected abstract void post();
}
