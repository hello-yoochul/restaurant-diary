package com.yoochul.restaurantnote.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.yoochul.restaurantnote.util.ColorManager;

public class ExitDialog extends Dialog {
	private final int WIDTH = 500;
	private final int HEIGHT = 200;

	public ExitDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(WIDTH, HEIGHT);
	}

	@Override
	public Control createDialogArea(Composite parent) {
		GridLayout parentLayout = new GridLayout();
		parentLayout.numColumns = 1;
		parent.setBackground(ColorManager.WHITE);
		parent.setLayout(new GridLayout(1, false));

		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(ColorManager.WHITE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		CLabel exitConfirmText = new CLabel(container, SWT.CENTER);
		exitConfirmText.setText("프로그램을 정말 종료하겠습니까?");
		exitConfirmText.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				true, true));
		exitConfirmText.setBackground(ColorManager.WHITE);

		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// super.createButtonsForButtonBar(parent);
		parent.setBackground(ColorManager.WHITE);
		createButton(parent, IDialogConstants.OK_ID, "종료", false).setBackground(ColorManager.WHITE);
		createButton(parent, IDialogConstants.CANCEL_ID, "아니오", false).setBackground(ColorManager.WHITE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("프로그램 종료 확인");
	}

	@Override
	protected void okPressed() {
		Display.getCurrent().close();
		super.okPressed();
	}
}
