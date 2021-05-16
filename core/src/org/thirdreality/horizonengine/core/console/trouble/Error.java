package org.thirdreality.horizonengine.core.console.trouble;

public enum Error
{
    INVALID_GAME_LAYER_ZINDEX;

    private static final String INVALID_GAME_LAYER_ZINDEX_MSG = "Prohibited to add a GameLayer object to another GameLayer if the z-index is the same or greater.";

    public static String getMessage(Error error)
    {
        switch(error)
        {
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
