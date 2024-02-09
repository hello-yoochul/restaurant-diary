package com.yoochul.restaurantnote.model;

import java.util.Map;
import java.util.stream.Collectors;

public class Menu {
	private Map<String, String> items;

	public Menu(Map<String, String> items) {
		this.items = items;
	}

	public void setItems(Map<String, String> items) {
		this.items = items;
	}

	public Map<String, String> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return items.entrySet().stream()
				.map(entry -> entry.getKey() + ": " + entry.getValue())
				.collect(Collectors.joining("\n"));
	}
}
