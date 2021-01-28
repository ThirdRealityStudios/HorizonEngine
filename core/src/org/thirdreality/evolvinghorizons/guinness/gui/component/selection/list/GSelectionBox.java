package org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.Path;
import org.thirdreality.evolvinghorizons.guinness.feature.container.SizedTexture;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;

public class GSelectionBox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private ArrayList<GSelectionOption> options;
	
	// This list contains all shapes related to an option.
	// This makes it easier for the rendering progress to just access the data more easily.
	// Also it will prevent all shapes from being calculated too frequently which enhances the performance actually.
	private ArrayList<Rectangle[]> updateShapeTable;
	
	private int index = -1;
	
	private boolean defaultOptionActive = false;

	// Keeps two different icons which illustrate two possible states of an option (selected / unselected).
	private SizedTexture[] icon;

	public GSelectionBox(Vector2 position, ArrayList<GSelectionOption> options)
	{
		super("selectionbox");
		
		updateShapeTable = new ArrayList<Rectangle[]>();
		
		initIcon();
		
		if(areValidOptions(options))
		{
			this.options = options;
			
			// Automatically selects a default option.
			selectDefaultOption();
		}
		else
		{
			throw new IllegalArgumentException("You wanted to add an ArrayList with options (type 'GSelectionOption') to a GSelectionBox.\nAnyway, one or more of the passed options are 'null' which is not valid, or there are multiple default options (added to) in the ArrayList passed to the GSelectionBox!\nThere can only be one default option for a whole GSelectionBox!\nMake sure you don't add double default options by using the method \"isValidOption(GSelectionOption option)\" or \"areValidOptions(ArrayList<GSelectionOption> options)\" respectively..");
		}
		
		// This will actually calculate a grid for every single option you add to this GSelectionBox.
		// Also, do not change order as the grid is important for the next method to determine the shape of the whole GSelectionBox.
		updateShapeTable(position);
		
		// Make sure, the current shape is updated with the correct size with the new options added.
		updateSelectionBoxShape(position);
		
		// Is always executed after having set the box shape because it transforms it directly to the given location.
		getStyle().getBounds().setPosition(position);
	}
	
	// Only there to load the images for the icons..
	public void initIcon()
	{
		icon = new SizedTexture[2];
		
		icon[0] = new SizedTexture(new Texture(Path.ICON_FOLDER + File.separator + "radio_unselected.png"),0,0);
		icon[1] = new SizedTexture(new Texture(Path.ICON_FOLDER + File.separator + "radio_selected.png"),0,0);
	}
	
	public ArrayList<GSelectionOption> getOptions()
	{
		return options;
	}
	
	public ArrayList<Rectangle[]> getShapeTable()
	{
		return updateShapeTable;
	}
	
	// This will select the default option officially if there is one.
	// Otherwise the first option is just the default option.
	private void selectDefaultOption()
	{
		for(int i = 0; i < options.size(); i++)
		{
			if(options.get(i).isDefaultOption())
			{
				options.get(i).setChecked(true);
				
				index = i;
				
				// Break off at this point before it would mistakenly continue after the loop.
				return;
			}
		}
		
		if(options.size() > 0)
		{
			options.get(0).setDefaultOption(true);
			options.get(0).setChecked(true);
			
			index = 0;
		}
	}
	
	// Makes sure, the current shape is updated with the correct size with new options added or removed.
	private void updateSelectionBoxShape(Vector2 origin)
	{
		float maxWidth = 0;
		
		float sumHeight = 0;
		
		for(int i = 0; i < getShapeTable().size(); i++)
		{
			maxWidth = Math.max(maxWidth, (getShapeTable().get(i)[0].width + getShapeTable().get(i)[1].width + getShapeTable().get(i)[2].width));
		}

		Rectangle lastShape = getShapeTable().get(getShapeTable().size()-1)[4];
		
		sumHeight = (lastShape.y + lastShape.height) - origin.y;
		
		getStyle().setBounds(new Rectangle(origin.x, origin.y, maxWidth, sumHeight));
	}

	// This will actually calculate a grid for every single option you add to this GSelectionBox.
	// It will to simplify the rendering process by not having to calculate these values frequently.
	// It can directly the draw method all necessary measurements of the symbol and title text of an option added.
	// Because of this, the "shape table" steadily needs to be updated when it is changed or just at the beginning.
	private void updateShapeTable(Vector2 origin)
	{
		int lastHeights = 0;

		for(int i = 0; i < options.size(); i++)
		{
			// Stores the current option.
			GSelectionOption option = getOptions().get(i);

			float iconSize = option.getLayout().height;

			Rectangle optionSymbolShape = null, optionSeparationWidth = null, optionTitleShape = null, optionPaddingTop = null, optionPaddingBottom = null;

			// The location of this GSelectionBox.
			Vector2 location = new Vector2(origin.x, origin.y + lastHeights);

			// Sizes (just dimensions) calculated here..
			{
				optionSymbolShape = new Rectangle(0,0,iconSize, iconSize);
				
				optionSeparationWidth = new Rectangle(0,0,iconSize / 2, iconSize);
				
				optionTitleShape = new Rectangle(0,0,option.getLayout().width, option.getLayout().height);
				
				optionPaddingBottom = new Rectangle(0,0,optionSymbolShape.width + optionSeparationWidth.width + optionTitleShape.width, option.getStyle().getPadding());
				
				optionPaddingTop = new Rectangle(0,0,optionPaddingBottom.width, option.getStyle().getPadding());
			}
			
			// Positions additionally applied here..
			{
				optionPaddingBottom.setPosition(location);
				
				optionSymbolShape.setPosition(location.x, optionPaddingBottom.y + optionPaddingBottom.height);
				
				optionSeparationWidth.setPosition(optionSymbolShape.x + optionSymbolShape.width, optionSymbolShape.y);
				
				optionTitleShape.setPosition(optionSeparationWidth.x + optionSeparationWidth.width, optionSeparationWidth.y);
				
				optionPaddingTop.setPosition(location.x, optionTitleShape.y + optionTitleShape.height);
			}
			
			// Creates an array of shapes for each option and adds it to the list.
			// This will give every option a set of shapes.
			{
				Rectangle[] optionShapes = new Rectangle[5];
				
				optionShapes[0] = new Rectangle(optionSymbolShape);
				
				// Apply the symbol size (reference "optionSymbolShape") to the icons (which will be the corresponding symbol for the "unselected" and "selected" state).
				// This way, it is guaranteed the icons are displayed correctly later depending on the font size.
				icon[0].setWidth(optionSymbolShape.width);
				icon[0].setHeight(optionSymbolShape.height);
				icon[1].setWidth(optionSymbolShape.width);
				icon[1].setHeight(optionSymbolShape.height);
				
				optionShapes[1] = new Rectangle(optionSeparationWidth);
				
				optionShapes[2] = new Rectangle(optionTitleShape);
				
				optionShapes[3] = new Rectangle(optionPaddingBottom);
				optionShapes[4] = new Rectangle(optionPaddingTop);
				
				updateShapeTable.add(optionShapes);
			}
			
			// Make sure, the next options offset to the current option in this cycle is correctly.
			// In this case, the height of "optionSymbolShape" is taken because it is identical to the height of "optionTitleShape" (it would make no difference which one).
			lastHeights += (optionPaddingBottom.height + optionSymbolShape.height + optionPaddingTop.height);
		}
	}
	
	public SizedTexture[] getIcons()
	{
		return icon;
	}
	
	public boolean isDefaultOptionActive()
	{
		return defaultOptionActive;
	}
	
	// Tells you whether the given option is valid.
	// Checks whether there is a default option yet which is not valid.
	// Only one option can be the default option at all!
	public boolean isValidOption(GSelectionOption option)
	{
		return option != null && option.isDefaultOption() && !isDefaultOptionActive();
	}
	
	// Simply the same thing as "isValidOption(GSelectionOption option)" but checks a whole ArrayList for the correctness of all options.
	// Also, this method is not related to the state of GSelectionBox itself.
	// That means, this method is only applicable for checking NEW ArrayLists which should be used for this GSelectionBox.
	public boolean areValidOptions(ArrayList<GSelectionOption> options)
	{
		int defaultOptions = 0;
		
		for(GSelectionOption option : options)
		{
			if(option.isDefaultOption())
			{
				defaultOptions++;
			}
			
			if(defaultOptions > 1 || option == null)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void addOption(GSelectionOption option) throws IllegalArgumentException
	{
		if(!isValidOption(option))
		{
			throw new IllegalArgumentException("You wanted to add an option of type 'GSelectionOption' to a GSelectionBox.\nAnyway, the passed option is 'null' which is not valid, or there is a default option (added) yet in the selection box!\nThere can only be one default option for a GSelectionBox!\nMake sure you don't add a double default option by using the method \"isValidOption(GSelectionOption option)\"..");
		}
		
		options.add(option);
		
		// Checks whether it is a default option (presumed there are no double default options anymore..).
		// Will select the default option also automatically at the beginning.
		if(option.isDefaultOption())
		{
			defaultOptionActive = true;
			
			index = options.size() - 1;
		}
		
		updateShapeTable(getStyle().getPosition());
		updateSelectionBoxShape(getStyle().getPosition());
	}
	
	private void unselectCompletelyAt(int index)
	{
		if(index < options.size())
		{
			options.get(index).setChecked(false);
			options.get(index).setDefaultOption(false);
		}
	}
	
	public void selectOptionAt(int index)
	{
		if(index < options.size())
		{
			// If there is the same option which wants to be selected then there is no change for a default option.
			// That means if you select an default option it will stay the default option and won't loose its state / recognition.
			if(this.index != index)
			{
				defaultOptionActive = false;
				
				// Completely unselect the old one including the default status..
				unselectCompletelyAt(this.index);
				
				// Simply sets the new index.
				this.index = index;
				
				// Also need to update the GSelectionOption object too to make all changes visible everywhere..
				options.get(index).setChecked(true);
			}
		}
		else
		{
			throw new IllegalArgumentException("You wanted to mark or set an option active of the specified index " + index + ".\nAnyway, this index does not exist and would exceed the real amount of available options in your GSelectionBox!");
		}
	}
}
