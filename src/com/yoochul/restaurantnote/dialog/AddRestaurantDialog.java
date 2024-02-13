package com.yoochul.restaurantnote.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yoochul.restaurantnote.model.FoodType;

public class AddRestaurantDialog extends Dialog {
	private Text nameText;
    private Text addressText;
    private Text noteText;
    private Combo typeCombo;
    
    private String name;
    private String address;
    private String note;
    private String mapUrl;
    private String picturesLocation;
    private String type;
    
    private static final int WIDTH = 450;
    private static final int HEIGHT = 300;

    public AddRestaurantDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        // 1. 음식점 이름
        Label nameLabel = new Label(container, SWT.NONE);
        nameLabel.setText("음식점 이름:");
        nameText = new Text(container, SWT.BORDER);
        nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        // 2. 타입
        Label typeLabel = new Label(container, SWT.NONE);
        typeLabel.setText("음식점 타입:");
        typeCombo = new Combo(container, SWT.READ_ONLY);
        typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        String[] foodTypes = FoodType.getNames();
        typeCombo.setItems(foodTypes);
        typeCombo.select(0);

        // 3. 주소
        Label addressLabel = new Label(container, SWT.NONE);
        addressLabel.setText("음식점 주소:");
        addressText = new Text(container, SWT.BORDER);
        addressText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // 4. 노트
        Label noteLabel = new Label(container, SWT.NONE);
        noteLabel.setText("음식점 노트:");
        noteText = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        noteText.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        return container;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Add Restaurant");
    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    @Override
    protected void okPressed() {
        name = nameText.getText();
        address = addressText.getText();
        type = typeCombo.getText();
        note = noteText.getText();
        super.okPressed();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public String getPicturesLocation() {
        return picturesLocation;
    }

    public String getType() {
		return type;
	}
    
    @Override
    protected Point getInitialSize() {
        return new Point(WIDTH, HEIGHT);
    }
}
