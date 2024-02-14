package com.yoochul.restaurantnote.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.yoochul.restaurantnote.model.Restaurant;
import com.yoochul.restaurantnote.util.ColorManager;

public class BottomView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.bottomview";

	private Text selectedRestaurantMenu;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout());
        fillComposite(parent);
	}

	private void fillComposite(Composite parent) {
        // 스크롤이 가능하도록 ScrolledComposite 생성
        ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
        scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        scrolledComposite.setBackground(ColorManager.WHITE);
        
        // ScrolledComposite 안에 Text를 직접 배치하는 대신 inner Composite를 사용하여, 스크롤 동작이 더욱 정확하고 유연해집니다. 
        // 또한 inner Composite를 사용하면 필요에 따라 유연하게 다른 위젯도 추가할 수 있다.
        Composite innerComposite = new Composite(scrolledComposite, SWT.NONE);
        innerComposite.setLayout(new GridLayout());
        innerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        innerComposite.setBackground(ColorManager.WHITE);

        // 스크롤이 가능한 Text 생성
        selectedRestaurantMenu = new Text(innerComposite, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
        selectedRestaurantMenu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        selectedRestaurantMenu.setBackground(ColorManager.WHITE);
        
        // Text에 Scrollable 설정
        scrolledComposite.setContent(innerComposite);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setMinSize(innerComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public void setFocus() {
	}

	public void updateUI(Restaurant selected) {
		if(selected == null || selected.getMenu() == null) {
			resetUI();
			return;
		}
		
		selectedRestaurantMenu.setText(selected.getMenu().toString() + "\n" + selected.getMenu().toString() + "\n" + selected.getMenu().toString());
	}
	
	private void resetUI() {
		selectedRestaurantMenu.setText("");
	}
}

/*public class BottomView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.bottomview";

	private Text selectedRestaurantMenu;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		label = new Label(parent, SWT.NONE);
		label.setText("Hello, Eclipse RCP!");
        fillComposite(parent);
	}

	private void fillComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
        
        // 스크롤이 가능한 ScrolledComposite 생성
        ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
        scrolledComposite.setLayout(new FillLayout());
        scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        // 내부 Composite 생성
        Composite innerComposite = new Composite(scrolledComposite, SWT.NONE);
        innerComposite.setLayout(new FillLayout());
        
        // 스크롤이 가능한 Text 생성
        selectedRestaurantMenu = new Text(innerComposite, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
        
        // Text에 Scrollable 설정
        scrolledComposite.setContent(innerComposite);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setMinSize(innerComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public void setFocus() {
	}

	public void updateUI(Restaurant selected) {
		if(selected == null || selected.getMenu() == null) {
			resetUI();
			return;
		}
		
		//System.out.println("SELECT DATA: " + selected.getMenu().toString());
		selectedRestaurantMenu.setText(selected.getMenu().toString());
	}
	
	private void resetUI() {
		selectedRestaurantMenu.setText("");
	}
}*/