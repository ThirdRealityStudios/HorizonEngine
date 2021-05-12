package org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.settings.Path;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;

public class GCheckbox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean checked;

	private GStyle style;

	public GCheckbox(boolean checked)
	{
		style = new GStyle();

		getStyle().getBorderProperties().setBorderThicknessPx(1);
		getStyle().setBounds(new Rectangle(0, 0, 20, 20));

		init(checked);
	}

	public GStyle getStyle()
	{
		return style;
	}

	@Deprecated
	// Just some values to be set which are equally set in both constructors.
	private void init(boolean checked)
	{
		setChecked(checked);

		Texture t = new Texture(Gdx.files.internal(Path.ICON_FOLDER + File.separator + "check_sign.png"));

		getStyle().setTexture(t);
	}
	
	public boolean isChecked()
	{
		return checked;
	}
	
	// Sets the check-box checked or unchecked (false).
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
}
