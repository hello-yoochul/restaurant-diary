package com.yoochul.restaurantnote.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.yoochul.restaurantnote.model.FoodType;
import com.yoochul.restaurantnote.model.Menu;
import com.yoochul.restaurantnote.model.Restaurant;

/**
 * In-memory DB
 * 싱글턴으로 프로그램이 구동하는 1개만 생성된다.
 * 식당 정보 테이블을 가지고 있다.
 */
public class DB {
	private static DB instance = null;
	private static Set<Restaurant> restaurantTableSet = new HashSet<>();
    private static AtomicLong uuidCounter = new AtomicLong(0);
    private List<String> mapSearchTermList = new ArrayList<>();
    private Random random = new Random();
    
    /**
     * Singleton 유지를 위한 private 접근제한자
     */
    private  DB() {
		loadDummyData();
	}

    /**
     * 싱글톤 DB 객체 가져오기
     */
	public static synchronized DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

	/**
	 * 식당 30개 생성 
	 */
	private void loadDummyData() {
		createDummyMapSearchTerm();
		for (int i = 0; i < 30; i++) {
			Restaurant restaurantToAdd = createDummyRestaurant(i, random);
			System.out.println("식당 생성 완료: " + restaurantToAdd.toLiteString());
			restaurantTableSet.add(restaurantToAdd);
		}
	}
	
	/**
	 * 검색어를 생성
	 */
	private void createDummyMapSearchTerm() {
		mapSearchTermList.addAll(Arrays.asList("판교 맛집", "야탑 맛집", "모란 맛집", "서현 맛집"));
	}
	
	private Restaurant createDummyRestaurant(int dummyDataNumber, Random random) {
        return new Restaurant.Builder()
				                .id(getNextUuid())
				                .name("NAME" + dummyDataNumber)
				                .type(getRandomFoodType())
				                .address("ADDRESS" + dummyDataNumber)
				                .mapUrl(getRandomMapUrl())
				                .note("NOTE" + dummyDataNumber)
				                .picturesLocation(createRandomPicLocation())
				                .menu(createMenu(dummyDataNumber))
				                .build();
    }
	
	/**
	 * 유니크한 식당 ID 생성
	 */
	private long getNextUuid() {
        return uuidCounter.incrementAndGet();
    }

	private FoodType getRandomFoodType() {
        FoodType[] foodTypes = FoodType.values();
        return foodTypes[random.nextInt(foodTypes.length)];
	}
	
	/**
	 * 식당 주소를 검색해야 하지만, 임시로 아무 사이트로 redirect용 URL 생성
	 */
	private String getRandomMapUrl() {
		return "https://map.naver.com/p/search/" + mapSearchTermList.get(random.nextInt(mapSearchTermList.size()));
	}

	/**
	 * 식당 마다 각자의 메뉴 사진이 들어간 폴더 위치가 있다. 
	 * 사진을 넣어둔 임시 4개 폴더를 랜덤으로 배정.
	 */
	private String createRandomPicLocation() {
		return "resources/" + (random.nextInt(4) + 1);
	}

	/**
	 * 식당 마다 메뉴 데이터 생성
	 */
	private Menu createMenu(int dummyDataNumber) {
		Map<String, String> menuAndPriceMap = new HashMap<>();
		String[] menuNames = { "짜장면", "짬뽕", "탕수육", "볶음밥", "깐풍기", "마파두부", "양장피", "김치찌개", "된장찌개", "불고기" };
		for (String menuName : menuNames) {
			menuAndPriceMap.put(menuName + dummyDataNumber, String.valueOf(random.nextInt(5000) + 7000));
		}
		return new Menu(menuAndPriceMap);
	}

	public Set<Restaurant> getData() {
		return restaurantTableSet;
	}
}

