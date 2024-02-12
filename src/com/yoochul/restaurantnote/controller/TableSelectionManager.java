package com.yoochul.restaurantnote.controller;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.yoochul.restaurantnote.model.Restaurant;
import com.yoochul.restaurantnote.view.BottomView;
import com.yoochul.restaurantnote.view.TopRightView;

public class TableSelectionManager {
    private static TableSelectionManager instance;
	
	private TableSelectionManager() {}

    public static TableSelectionManager getInstance() {
        if (instance == null) {
            instance = new TableSelectionManager();
        }
        return instance;
    }

    public void propagateTableSelection(Restaurant restaurant) {
    	// TODO: 매번 테이블 행을 클릭할때 마다 active page를 찾아오는게 맞는가?
    	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	    TopRightView topRight = (TopRightView) page.findView(TopRightView.ID);
	    BottomView bottom = (BottomView) page.findView(BottomView.ID);
    	
		if (topRight != null) {
			topRight.updateUI(restaurant);
		}
		
		if (bottom != null) {
			bottom.updateUI(restaurant);
		}
    }
}
