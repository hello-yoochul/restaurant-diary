package com.yoochul.restaurantnote.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yoochul.restaurantnote.util.ColorManager;

public class AboutDialog extends Dialog {
	public AboutDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
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

		GridLayout sideLayout = new GridLayout();
		sideLayout.horizontalSpacing = 5;

		Composite leftComposite = new Composite(container, SWT.NONE);
		leftComposite.setBackground(ColorManager.WHITE);
		leftComposite.setLayout(sideLayout);
		leftComposite.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false,
				true));

		Label titleLabel = new Label(leftComposite, SWT.NONE);
		titleLabel.setText("프로그램 이름:");
		titleLabel.setBackground(ColorManager.WHITE);

		Label versionLabel = new Label(leftComposite, SWT.NONE);
		versionLabel.setText("버전:");
		versionLabel.setBackground(ColorManager.WHITE);

		 Label makerLabel = new Label(leftComposite, SWT.NONE);
		 makerLabel.setText("만든이:");
		 makerLabel.setBackground(ColorManager.WHITE);
		
		 Label purposeLabel = new Label(leftComposite, SWT.NONE);
		 purposeLabel.setText("만든 목적:");
		 purposeLabel.setBackground(ColorManager.WHITE);
		
		 Label noteLabel = new Label(leftComposite, SWT.NONE);
		 noteLabel.setText("릴리즈 노트:");
		 noteLabel.setBackground(ColorManager.WHITE);
		
		 Composite rightComposite = new Composite(container, SWT.NONE);
		 rightComposite.setBackground(ColorManager.WHITE);
		 rightComposite.setLayout(sideLayout);
		 rightComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		 Label titleValueLabel = new Label(rightComposite, SWT.NONE);
		 titleValueLabel.setText("맛집 다이어리");
		 titleValueLabel.setBackground(ColorManager.WHITE);
		
		 Label versionValueLabel = new Label(rightComposite, SWT.NONE);
		 versionValueLabel.setText("v0.1");
		 versionValueLabel.setBackground(ColorManager.WHITE);
		
		 Label makerValueLabel = new Label(rightComposite, SWT.NONE);
		 makerValueLabel.setText("김유철");
		 makerValueLabel.setBackground(ColorManager.WHITE);
		
		 Label purposeValueLabel = new Label(rightComposite, SWT.NONE);
		 purposeValueLabel.setText("다녀온 맛집 기록");
		 purposeValueLabel.setBackground(ColorManager.WHITE);
		
		
		 ScrolledComposite scrolledComposite = new
		 ScrolledComposite(rightComposite, SWT.BORDER | SWT.V_SCROLL);
		 Text noteValueLabel = new Text(scrolledComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		 noteValueLabel.setText("v0.1 (2024년 02월 14일)");
		 scrolledComposite.setContent(noteValueLabel);
		 scrolledComposite.setExpandVertical(true);
		 scrolledComposite.setExpandHorizontal(true);
		 scrolledComposite.setMinSize(noteValueLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		parent.setBackground(ColorManager.WHITE);
		createButton(parent, IDialogConstants.OK_ID, "확인", false).setBackground(ColorManager.WHITE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("이 프로그램에 대하여");
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}
}
