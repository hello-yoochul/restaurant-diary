package com.yoochul.restaurantnote.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.yoochul.restaurantnote.composite.RestaurantTableViewer;

public class TopLeftView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.topleftview";
	
	private Composite restaurantTableViewer;
    
	@Override
	public void createPartControl(Composite parent) {
		fillComposite(parent);
	}

	@Override
	public void setFocus() {
	}
	
	private void fillComposite(Composite parent) {
		parent.setLayout(new GridLayout());
    	createTop(parent);
    	createBottom(parent);
    }

	private void createTop(Composite parent) {
	}

	private void createBottom(Composite parent) {
		createTable(parent);
	}

	private void createTable(Composite parent) {
		restaurantTableViewer = new RestaurantTableViewer(parent, SWT.BORDER);
	}
 
}
