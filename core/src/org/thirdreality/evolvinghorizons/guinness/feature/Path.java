package org.thirdreality.evolvinghorizons.guinness.feature;

import java.io.File;

public interface Path
{
	public static final String USER_DIR = System.getProperty("user.dir");
	
	public static final String GUINNESS_FOLDER = USER_DIR + File.separator + "media";
	
	public static final String FONT_FOLDER = GUINNESS_FOLDER + File.separator + "image" + File.separator + "font";
	
	public static final String ICON_FOLDER = GUINNESS_FOLDER + File.separator + "image" + File.separator + "icon";
}
