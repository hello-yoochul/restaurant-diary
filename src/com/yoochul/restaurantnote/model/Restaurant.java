package com.yoochul.restaurantnote.model;

import java.util.Objects;

public class Restaurant {
	private long id;
	private String name;
	private FoodType type;
	private String address;
	private String mapUrl;
	private String note;
	private String picturesLocation;
	private Menu menu;

	private Restaurant(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.type = builder.type;
		this.address = builder.address;
		this.mapUrl = builder.mapUrl;
		this.note = builder.note;
		this.picturesLocation = builder.picturesLocation;
		this.menu = builder.menu;
	}

	public static class Builder {
		private long id;
		private String name;
		private FoodType type;
		private String address;
		private String mapUrl;
		private String note;
		private String picturesLocation;
		private Menu menu;

		public Builder() {
		}

		public Builder id(long l) {
			this.id = l;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder type(FoodType type) {
			this.type = type;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder mapUrl(String mapUrl) {
			this.mapUrl = mapUrl;
			return this;
		}

		public Builder note(String note) {
			this.note = note;
			return this;
		}

		public Builder picturesLocation(String picturesLocation) {
			this.picturesLocation = picturesLocation;
			return this;
		}

		public Builder menu(Menu menu) {
			this.setMenu(menu);
			return this;
		}

		public Restaurant build() {
			return new Restaurant(this);
		}

		public void setMenu(Menu menu) {
			this.menu = menu;
		}

		public Menu getMenu() {
			return menu;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodType getType() {
		return type;
	}

	public void setType(FoodType type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPicturesLocation() {
		return picturesLocation;
	}

	public void setPicturesLocation(String picturesLocation) {
		this.picturesLocation = picturesLocation;
	}


	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Menu getMenu() {
		return menu;
	}
	
	@Override
	public String toString() {
		return "Restaurant{" + "id=" + id 
					+ ", name='" + name + '\''
					+ ", type='" + type + '\'' 
					+ ", address='" + address + '\''
					+ ", mapUrl='" + mapUrl 
					+ '\'' + ", note='" + note + '\''
					+ ", picturesLocation='" 
					+ picturesLocation + '\'' + '}';
	}

	public String toLiteString() {
		return "{id='" + id + "\'" 
					+ ", name='" + name + "\'" 
					+ ", type='" + type + "\'" 
					+ ", note='" + note + "\'}";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Restaurant that = (Restaurant) obj;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
