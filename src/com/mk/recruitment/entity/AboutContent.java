package com.mk.recruitment.entity;

public class AboutContent {
	private String aboutguid;
	private String aboutcontent;

	public String getAboutguid() {
		return aboutguid;
	}

	public void setAboutguid(String aboutguid) {
		this.aboutguid = aboutguid;
	}

	public String getAboutcontent() {
		return aboutcontent;
	}

	public void setAboutcontent(String aboutcontent) {
		this.aboutcontent = aboutcontent;
	}

	@Override
	public String toString() {
		return "AboutContent [aboutguid=" + aboutguid + ", aboutcontent=" + aboutcontent + "]";
	}

}
