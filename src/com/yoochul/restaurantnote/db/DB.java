package com.yoochul.restaurantnote.db;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

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
    
    private String[] dummyRestaurantNameArr = { "고궁", "마라도", "이촌 삼겹살", "밀면사무소", "봉추찜닭",
			"오리불고기집", "강남면옥", "홍콩반점", "진진바라", "바스버거", "굽네치킨", "홍콩반점",
			"최고집", "부어치킨", "할매순대국", "곱창전골", "신의주 부대찌개", "맥도날드", "피자헛",
			"본도시락", "다담", "왕십리 칼국수", "진짜라면", "한옥집", "멘야미토기", "고고꽈배기",
			"대우식당", "프레시지", "피자마루" };
	
	private String[] dummyAddressesArr = { "서울시 강남구 역삼동 123-1",
			"서울시 강서구 화곡동 456-2", "서울시 송파구 잠실동 789-3", "서울시 서초구 서초동 456-4",
			"서울시 노원구 상계동 789-5", "서울시 종로구 종로동 123-6", "서울시 성동구 성수동 456-7",
			"서울시 강북구 수유동 789-8", "서울시 중랑구 망우동 123-9", "서울시 도봉구 방학동 456-10",
			"서울시 강동구 상일동 789-11", "서울시 동작구 사당동 123-12", "서울시 관악구 봉천동 456-13",
			"서울시 마포구 서교동 789-14", "서울시 은평구 녹번동 123-15", "서울시 양천구 신정동 456-16",
			"서울시 강남구 삼성동 789-17", "서울시 서초구 방배동 123-18", "서울시 송파구 가락동 456-19",
			"서울시 강서구 방화동 789-20" };
	
	private String[] dummyNoteArr = { "맛이 좋고 서비스도 친절해서 자주 가는 식당입니다.",
			"분위기가 좋고 음식도 맛있습니다. 다음에 또 방문하고 싶네요.", "가격 대비 음식의 양과 맛이 좋은 곳입니다.",
			"직원들이 친절하고 음식이 신선해서 맛있습니다.", "인테리어가 아늑하고 음식이 맛있어요.",
			"가게가 깔끔하고 음식이 맛있습니다. 추천합니다.", "분위기가 좋고 메뉴 다양해서 자주 가는 곳입니다.",
			"음식이 정갈하고 맛있어요. 가격도 합리적입니다.", "가족 모두가 만족하는 식당입니다. 다음에 또 방문하겠습니다.",
			"여기 음식 맛있어요! 다음에도 꼭 방문할 거에요.", "서비스가 좋고 음식이 맛있어서 자주 가는 식당입니다.",
			"음식이 정갈하고 맛있습니다. 추천해요.", "가격이 저렴하고 음식이 맛있는 곳입니다.",
			"분위기도 좋고 음식도 맛있습니다. 자주 가는 곳입니다.", "가격이 저렴하고 양도 푸짐해서 좋아요.",
			"서비스가 좋고 음식도 맛있습니다. 다음에 또 방문하겠습니다.",
			"분위기가 아늑하고 음식이 맛있습니다. 자주 가는 식당입니다.",
			"가격 대비 음식의 질이 좋은 곳입니다. 추천합니다.", "음식이 정갈하고 맛있습니다. 자주 방문하게 되네요.",
			"가족이 모두 좋아하는 식당입니다. 다음에도 또 방문하겠습니다." };
    
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
			//System.out.println("식당 생성 완료: " + restaurantToAdd.toLiteString());
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
				                .name(getRandomName())
				                .type(getRandomFoodType())
				                .address(getRandomAddress())
				                .mapUrl(getRandomMapUrl())
				                .note(getRandomNote())
				                .picturesLocation(createRandomPicLocation())
				                .menu(createMenu(dummyDataNumber))
				                .build();
    }
	
	
	private String getRandomNote() {
		return dummyNoteArr[random.nextInt(dummyNoteArr.length)];
	}

	private String getRandomName() {
		return dummyRestaurantNameArr[random.nextInt(dummyRestaurantNameArr.length)];
	}
	
	private String getRandomAddress() {
		return dummyAddressesArr[random.nextInt(dummyAddressesArr.length)];
	}

	/**
	 * 유니크한 식당 ID 생성
	 */
	private long getNextUuid() {
        return uuidCounter.incrementAndGet();
    }

	private FoodType getRandomFoodType() {
        FoodType[] foodTypesExcludingAll = Arrays.stream(FoodType.values())
                .filter(foodType -> foodType != FoodType.ALL)
                .toArray(FoodType[]::new);
        return foodTypesExcludingAll[random.nextInt(foodTypesExcludingAll.length)];
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
		// new Path("resources/1/Snipaste_2024-02-13_10-25-05.png")
		return "resources/" + (random.nextInt(4) + 1) + "/";
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
	
	public void add(Restaurant restaurant) {
		restaurant.setId(getNextUuid());
		restaurantTableSet.add(restaurant);
	}
	
	public void delete(List<Restaurant> selectedRestaurants) {
		for (Restaurant res : selectedRestaurants) {
			if (restaurantTableSet.contains(res)) {
				restaurantTableSet.remove(res);
			}
		}
	}
}

