package com.yoochul.restaurantnote;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.yoochul.restaurantnote.view.BottomView;
import com.yoochul.restaurantnote.view.TopLeftView;
import com.yoochul.restaurantnote.view.TopRightView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		layout.addView(BottomView.ID, IPageLayout.BOTTOM, 0.7f, editorArea);
		layout.addView(TopLeftView.ID, IPageLayout.LEFT, 0.5f, editorArea);
		layout.addView(TopRightView.ID, IPageLayout.RIGHT, 0.5f, editorArea);
	}
}
