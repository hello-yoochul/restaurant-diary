package com.yoochul.restaurantnote.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class BottomView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.bottomview";

	private Label label;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		label = new Label(parent, SWT.NONE);
		label.setText("Hello, Eclipse RCP!");
	}

	@Override
	public void setFocus() {
	}
}