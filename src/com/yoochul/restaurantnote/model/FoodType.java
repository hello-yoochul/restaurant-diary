package com.yoochul.restaurantnote.model;

import java.util.Arrays;

public enum FoodType {
	ALL("ALL"),
	WESTURN("양식"), 
	CHINSESE("중식"), 
	JAPANESE("일식"), 
	KOREAN("한식"), 
	ETC("기타");

	private final String name;

	FoodType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static String[] getNames() {
		return Arrays
				.stream(FoodType.values())
				.map(FoodType::getName)
				.toArray(String[]::new);
	}

	public static FoodType getByName(String name) {
		return Arrays
				.stream(FoodType.values())
				.filter(foodType -> foodType.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(name + " 이름의 음식타입이 없습니다."));
	}
}
