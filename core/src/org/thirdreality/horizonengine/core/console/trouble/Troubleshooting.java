package org.thirdreality.horizonengine.core.console.trouble;

public enum Troubleshooting
{
    REPORT_TO_DEV,
    INVALID_GAME_LAYER_ZINDEX;

    private static final String REPORT_TO_DEV_MSG = "If you only use this program, please report the issue to the developer!",
                               INVALID_GAME_LAYER_ZINDEX_MSG = "Check your added GameLayers (to the GameManager) in your implementation. Possible source of mistake: you did not mind the steady structure of all GameObjects (drawing layers behind first, followed by the other ones in front and so on. For input detection it is the exact opposite way around. Hence, GameLayers can ONLY point to other GameLayers in front of). " + REPORT_TO_DEV_MSG;

    public static String getHelp(Troubleshooting troubleshooting)
    {
        switch(troubleshooting)
        {
            case REPORT_TO_DEV:
            {
                return REPORT_TO_DEV_MSG;
            }
            case INVALID_GAME_LAYER_ZINDEX:
            {
                return INVALID_GAME_LAYER_ZINDEX_MSG;
            }

            default:
            {
                return null;
            }
        }
    }
}
