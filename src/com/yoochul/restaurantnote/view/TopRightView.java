package com.yoochul.restaurantnote.view;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

import com.yoochul.restaurantnote.model.Restaurant;
import com.yoochul.restaurantnote.util.StringUtil;

public class TopRightView extends ViewPart {
	public static final String ID = "com.yoochul.restaurantnote.view.toprightview";
	private final int IMAGE_NUMBER_PER_LINE = 3;
	private Composite parentComposite;
	private Composite topComposite;
	private ScrolledComposite bottomScrolledComposite;
	private Label topLabel;
    
	@Override
	public void createPartControl(Composite parent) {
		parentComposite = new Composite(parent, SWT.NONE);
		parentComposite.setLayout(new FillLayout(SWT.VERTICAL));
        createTop(parentComposite);
        createBottom(parentComposite);
	}

	private void createTop(Composite parent) {
		topComposite = new Composite(parent, SWT.BORDER);
	    topComposite.setLayout(new FillLayout(SWT.HORIZONTAL)); // 수평 방향으로 채워지도록 설정
	    topComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false)); // 중앙에 위치하도록 설정
	    
	    topLabel = new Label(topComposite, SWT.CENTER | SWT.WRAP); // 수평 및 수직 중앙 정렬, 자동 줄 바꿈
	    topLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));

	    topLabel.addMouseListener(new MouseListener() {
	        @Override
	        public void mouseDoubleClick(MouseEvent e) {
	            Program.launch(topLabel.getText());
	        }

	        @Override
	        public void mouseDown(MouseEvent e) {
	            // 마우스 클릭 이벤트 처리
	        }

	        @Override
	        public void mouseUp(MouseEvent e) {
	            // 마우스 업 이벤트 처리
	        }
	    });
	}
	
	private void createBottom(Composite parent) {
		bottomScrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	    bottomScrolledComposite.setLayout(new FillLayout());
	}
	
	public void updateUI(Restaurant selected) {
		if (selected == null) {
			resetUI();
			return;
		}
		
		if (selected.getPicturesLocation() == null) {
			resetBottomScrolledComposite();
			return;
		}
		
		setMapUrlOnTop(selected);
		setImage(selected.getPicturesLocation());
	}
	
	private void resetUI() {
		topLabel.setText("");
		resetBottomScrolledComposite();
	}
	
	private void resetBottomScrolledComposite() {
		bottomScrolledComposite.setContent(new Composite(parentComposite, SWT.NONE));
	}
	
	private void setMapUrlOnTop(Restaurant restaurant) {
		if (restaurant == null) return;
		topLabel.setText(restaurant.getMapUrl());
	}
	
	private void setImage(String imagePath){
		//System.out.println("@@@@@@@@@@@@@@@@@@ imagePath: " + imagePath);
		if (StringUtil.isBlank(imagePath)) return;
		
		 // 내부 Composite 생성
	    Composite composite = new Composite(bottomScrolledComposite, SWT.NONE);
	    
	    // GridLayout 설정
	    GridLayout layout = new GridLayout(IMAGE_NUMBER_PER_LINE, true);
	    composite.setLayout(layout);
				
	    File directory = new File(imagePath);

		final File[] files = directory.listFiles();

	    if (files != null) {
	        for (File file : files) {
	        	//System.out.println("file: " + file.getName());
	            if (file.isFile() && isImageFile(file.getName())) {
	                // 이미지 로드
	                Image originalImage = new Image(parentComposite.getDisplay(), file.getAbsolutePath());
	                
	                // TODO: 이미지 크기 조정 composite 사이즈에 맞게 조정, resize할때 조정되게 
	                int scaledWidth = 150;
	                int scaledHeight = 130;
	                Image scaledImage = new Image(parentComposite.getDisplay(), originalImage.getImageData().scaledTo(scaledWidth, scaledHeight));
	                originalImage.dispose(); // 이미지 리소스 반환
	                
	                // 이미지를 보여주는 Label 생성
	                Label label = new Label(composite, SWT.NONE);
	                label.setImage(scaledImage);
	                
	                // Label의 GridData 설정
	                GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
	                gridData.widthHint = scaledWidth;
	                gridData.heightHint = scaledHeight;
	                label.setLayoutData(gridData);
	            }
	        }
	    }
	    bottomScrolledComposite.setContent(composite);
	    
	    // 스크롤 기능 활성화
	    bottomScrolledComposite.setExpandHorizontal(true);
	    bottomScrolledComposite.setExpandVertical(true);
	    bottomScrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	private static boolean isImageFile(String fileName) {
		String[] imageExtensions = { ".png", ".jpg", ".jpeg", ".gif", ".bmp" };
		for (String extension : imageExtensions) {
			if (fileName.toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setFocus() {
	}
}
