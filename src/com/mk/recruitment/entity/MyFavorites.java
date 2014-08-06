package com.mk.recruitment.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class MyFavorites {
	private String myfavoritesguid;
	private String recruitpostguid;
	private String webuserguid;
	private Timestamp favoritestime;

	public String getMyfavoritesguid() {
		return myfavoritesguid;
	}

	public void setMyfavoritesguid(String myfavoritesguid) {
		this.myfavoritesguid = myfavoritesguid;
	}

	public String getRecruitpostguid() {
		return recruitpostguid;
	}

	public void setRecruitpostguid(String recruitpostguid) {
		this.recruitpostguid = recruitpostguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getFavoritestime() {
		return favoritestime;
	}

	public void setFavoritestime(Timestamp favoritestime) {
		this.favoritestime = favoritestime;
	}
}
