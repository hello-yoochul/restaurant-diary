package com.yoochul.restaurantnote.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.yoochul.restaurantnote.db.DB;
import com.yoochul.restaurantnote.dialog.AddRestaurantDialog;
import com.yoochul.restaurantnote.model.FoodType;
import com.yoochul.restaurantnote.model.Restaurant;
import com.yoochul.restaurantnote.util.StringUtil;
import com.yoochul.restaurantnote.view.TopLeftView;

public class SearchableRestaurantTableViewerComposite extends Composite {
	private final DB database = DB.getInstance();
	
	private static final String[] columnNames = { "No.", "Name", "Type", "Address", "Note" };
	
	private static final int[] columnWidths = { 50, 150, 100, 220, 400 };
	
    private Combo foodTypeCombo;

	private Text searchText;
    
	private TableViewer tableViewer;
	
	private TopLeftView topLeftView;
	
	public SearchableRestaurantTableViewerComposite(Composite parent, int style, TopLeftView topLeftView) {
        super(parent, style); 
        this.topLeftView = topLeftView;
    	setLayout(new GridLayout());
    	setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        fillComposite();
    }

	private void fillComposite() {
        createFoodTypeComboBox();
        createSearchBox();
        createTable();
        createAddAndDeleteButtons();
	}

	private void createFoodTypeComboBox() {
		foodTypeCombo = new Combo(this, SWT.READ_ONLY);
    	foodTypeCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
    	foodTypeCombo.setItems(FoodType.getNames());
    	foodTypeCombo.select(0);
    	
    	foodTypeCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                topLeftView.syncFoodType(foodTypeCombo.getSelectionIndex());
				updateTable();
            }
        });
	}

	private void createSearchBox() {
		searchText = new Text(this, SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL | SWT.BORDER);
        searchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        searchText.setMessage("Search");
        
        searchText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateTable();
            }
        });
	}
	
	public void updateTable() {
		// TODO Auto-generated method stub
		tableViewer.refresh();
	}

	private void createTable() {
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
        Table table = tableViewer.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        createTableColumns(table);     
        
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.setLabelProvider(new RestaurantTableLabelProvider());
        tableViewer.setInput(database.getData());
        tableViewer.setFilters(new ViewerFilter[] { new RestaurantSearchFilter(foodTypeCombo, searchText)});
        
        tableViewer.setComparator(new ViewerComparator() {
            public int compare(Viewer viewer, Object e1, Object e2) {
            	Restaurant res1 = (Restaurant) e1;
                Restaurant res2 = (Restaurant) e2;
                Long id1 = res1.getId();
                Long id2 = res2.getId();
                return id1.compareTo(id2);
            };
        });
        
        topLeftView.getSite().setSelectionProvider(tableViewer);
	}

	private void createTableColumns(Table table) {
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(columnNames[i]);
			column.setWidth(columnWidths[i]);
		}
	}
	
	private static class RestaurantSearchFilter extends ViewerFilter {
		private Combo foodTypeCombo;
		private Text searchText;
		
		private FoodType selectedFoodType;
		private String searchString;

        public RestaurantSearchFilter(Combo foodTypeCombo, Text searchText) {
        	this.foodTypeCombo = foodTypeCombo;
        	this.searchText = searchText;
        }

        /**
         * 검색어와 음식점 타입에 따라 검색이 가능하여야 한다.
         * 음식타입은 항상 포함되는데 반해 검색어는 주어지지 않을 수 있으므로 대비한다. 
         * 검색어 O, 음식타입 
         * 검색어 O, 
         */
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (!(element instanceof Restaurant)) return false;
			
			selectedFoodType = FoodType.getByName(foodTypeCombo.getText());
			searchString = searchText.getText();
			
			Restaurant restaurant = (Restaurant) element;
			
			// 음식점 타입이 주어졌을때, 
			if( selectedFoodType != null && !selectedFoodType.equals(FoodType.ALL) ) {
				if(StringUtil.isNotBlank(searchString)){ // 검색어가 있다면, 식당 타입과 검색어로 식당 출력 여부 결정
					return restaurant.getType().equals(selectedFoodType) && searchStringIncluded(restaurant, searchString);
				} else { // 아니면, 식당 타입으로만 식당 출력 여부 결정
					return restaurant.getType().equals(selectedFoodType);
				}
			}
			
			// 검색어가 주어졌을때,  
			if(StringUtil.isNotBlank(searchString)) {
				return searchStringIncluded(restaurant, searchString);
			}
			
			return true; // 검색어가 없을때는 항상 식당 출력
		}
		
		/**
		 * @return 식당 정보에 검색어가 포함되어 있으면 true
		 */
		private boolean searchStringIncluded(Restaurant restaurant, String searchString){
			return restaurant.getName().toLowerCase().contains(searchString.toLowerCase())
					|| restaurant.getType().getName().toLowerCase().contains(searchString.toLowerCase())
					|| restaurant.getAddress().toLowerCase().contains(searchString.toLowerCase())
					|| restaurant.getNote().toLowerCase().contains(searchString.toLowerCase());
		}
	}

	public void syncFoodType(int index) {
		// TODO: index 유효한 범위 체크 
		foodTypeCombo.select(index);
	}
	
	private void createAddAndDeleteButtons() {
        Composite buttonComposite = new Composite(this, SWT.NONE);
        buttonComposite.setLayout(new GridLayout(2, false));
        buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Button addButton = new Button(buttonComposite, SWT.PUSH);
        addButton.setText("맛집 추가");
        addButton.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
        
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                AddRestaurantDialog dialog = new AddRestaurantDialog(getShell());
                
                if (dialog.open() == Window.OK) {
                    String name = dialog.getName();
                    FoodType type = FoodType.getByName(dialog.getType());
                    String note = dialog.getNote();
                    
                    Restaurant restaurant = new Restaurant.Builder()
                        .name(name == null ? "" : name)
                        .type(type)
                        .note(note == null ? "" : note)
                        .build();
                    
                    database.add(restaurant);
                    
                    updateTable();
                }
            }
        });
        
        
        Button deleteButton = new Button(buttonComposite, SWT.PUSH);
        deleteButton.setText("삭제");
        deleteButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
            	Object[] selectedItems = selection.toArray();
            	List<Restaurant> selectedRestaurants = new ArrayList<Restaurant>();
            	
            	for (Object selectedItem : selectedItems) {
            	    if (selectedItem instanceof Restaurant) {
            	        Restaurant restaurant = (Restaurant) selectedItem;
            	        selectedRestaurants.add(restaurant);
            	    }
            	}
            	
            	database.delete(selectedRestaurants);
            	
            	updateTable();
            }
        });
	}	
}
