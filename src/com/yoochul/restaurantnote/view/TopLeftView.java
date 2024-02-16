package com.yoochul.restaurantnote.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.yoochul.restaurantnote.composite.RestaurantCategorySelection;
import com.yoochul.restaurantnote.composite.SearchableRestaurantTableViewerComposite;
import com.yoochul.restaurantnote.util.ColorManager;

public class TopLeftView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.topleftview";
	
	private RestaurantCategorySelection topComposite;

	private SearchableRestaurantTableViewerComposite bottomComposite;
	
    
	@Override
	public void createPartControl(Composite parent) {
		parent.setBackground(ColorManager.WHITE);
		fillComposite(parent);
	}

	@Override
	public void setFocus() {
	}
	
	private void fillComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		topComposite = new RestaurantCategorySelection(parent, SWT.BORDER, this);
		topComposite.setBackground(ColorManager.WHITE);
		bottomComposite = new SearchableRestaurantTableViewerComposite(parent, SWT.BORDER, this);
		bottomComposite.setBackground(ColorManager.WHITE);
    }
	
	public void syncFoodType(int index){ // TODO: index 말고 FoodType 으로 변경하고, index를 쓸거면 범위 체크하기 
		if (index == -1) return;
		topComposite.syncFoodType(index);
		bottomComposite.syncFoodType(index);
	}

	public void refreshTable() {
		bottomComposite.updateTable();
	}
}
