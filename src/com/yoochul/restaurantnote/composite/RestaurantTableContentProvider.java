package com.yoochul.restaurantnote.composite;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.yoochul.restaurantnote.db.DB;


public class RestaurantTableContentProvider implements IStructuredContentProvider {
	
	private final DB db = DB.getInstance();

    @Override
    public Object[] getElements(Object inputElement) {
        return db.getData().toArray();
    }

    @Override
    public void dispose() {
        // No action needed
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // No action needed
    }
}
