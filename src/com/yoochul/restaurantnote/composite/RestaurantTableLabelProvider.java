package com.yoochul.restaurantnote.composite;


import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.yoochul.restaurantnote.model.Restaurant;

public class RestaurantTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		
		Restaurant elem = (Restaurant) element;
			
		switch (columnIndex) {
			case 0:
				return String.valueOf(elem.getId());
			case 1:
				return elem.getName();
			case 2:
				return elem.getType().getName();
			case 3:
				return elem.getAddress();
			case 4:
				return elem.getNote();
			default:
				return "f";
		}
	}
}
