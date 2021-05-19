package org.thirdreality.horizonengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.thirdreality.horizonengine.core.console.trouble.Error;
import org.thirdreality.horizonengine.core.console.trouble.Troubleshooting;

public class HorizonEngine
{
    private static LwjglApplication currentApp;

    private static final String LOG_INFO = "INFO",
            LOG_WARNING = "WARNING",
            LOG_ERROR = "ERROR",
            LOG_ERROR_EXITING = "ERROR / EXITING",
            LOG_TROUBLESHOOTING = "TROUBLESHOOTING";

    public static void info(String information)
    {
        Gdx.app.log(LOG_INFO, information);
    }

    public static void warning(String warning)
    {
        Gdx.app.error(LOG_WARNING, warning);
    }

    public static void error(Error error)
    {
        Gdx.app.error(LOG_ERROR + " | " + error.ordinal(), Error.getMessage(error));
    }

    public static void error(Error error, String additionalInfo)
    {
        Gdx.app.error(LOG_ERROR + " | " + error.ordinal(), Error.getMessage(error) + " " + additionalInfo);
    }

    public static void errorExit(Error error)
    {
        Gdx.app.error(LOG_ERROR_EXITING + " | " + error.ordinal(), Error.getMessage(error));
        exit();
    }

    public static void errorExit(Error error, String additionalInfo)
    {
        Gdx.app.error(LOG_ERROR_EXITING + " | " + error.ordinal(), Error.getMessage(error) + " " + additionalInfo);
        exit();
    }

    public static void troubleshoot(String hintOrAdvice)
    {
        Gdx.app.error(LOG_TROUBLESHOOTING, hintOrAdvice);
    }

    public static void troubleshoot(Troubleshooting troubleshooting)
    {
        troubleshoot(Troubleshooting.getHelp(troubleshooting));
    }

    public static void errorExit(Error error, Troubleshooting troubleshooting)
    {
        Gdx.app.error(LOG_ERROR_EXITING, Error.getMessage(error));
        troubleshoot(troubleshooting);
        exit();
    }

    public static void exit()
    {
        Gdx.app.exit();
    }

    public static void start(HorizonApp app)
    {
        currentApp = new LwjglApplication(app.caller, app.LWJGLConfig);
    }
}
