package com.yoochul.restaurantnote.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.yoochul.restaurantnote.composite.SearchableRestaurantTableViewerComposite;

public class TopLeftView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.topleftview";
	
	private SearchableRestaurantTableViewerComposite bottomComposite;
    
	@Override
	public void createPartControl(Composite parent) {
		fillComposite(parent);
	}

	@Override
	public void setFocus() {
	}
	
	private void fillComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		bottomComposite = new SearchableRestaurantTableViewerComposite(parent, SWT.BORDER);
    }
}
