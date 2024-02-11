package com.yoochul.restaurantnote.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.yoochul.restaurantnote.model.FoodType;
import com.yoochul.restaurantnote.view.TopLeftView;

public class RestaurantCategorySelection extends Composite {

	private Button[] radioButtons;
	
	private TopLeftView topLeftView;
	
	public RestaurantCategorySelection(Composite parent, int style, TopLeftView topLeftView) {
		super(parent, style);
		this.topLeftView = topLeftView;
		setLayout(new GridLayout(1, false));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fillComposite();
	}

	private void fillComposite() {
		Group group = new Group(this, SWT.SHADOW_ETCHED_IN);
		group.setLayout(new GridLayout(7, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("범주");
		
		radioButtons = new Button[FoodType.values().length];
		for (FoodType foodType : FoodType.values()) {
			Button button = new Button(group, SWT.RADIO);
			button.setText(foodType.getName());
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					topLeftView.syncFoodType(getSelectedIndex());
					topLeftView.refreshTable();
				}
			});
			radioButtons[foodType.ordinal()] = button;
		}
		radioButtons[0].setSelection(true);
	}
	
	public void syncFoodType(int index) {
		// TODO: index 유효한 범위 체크 
		for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].setSelection(i == index);
        }
	}
	
	private int getSelectedIndex() {
	    for (int i = 0; i < radioButtons.length; i++) {
	        if (radioButtons[i].getSelection()) {
	            return i; // 선택된 라디오 버튼의 인덱스 반환
	        }
	    }
	    return -1; // 선택된 라디오 버튼이 없을 경우 -1 반환
	}
}
