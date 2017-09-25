package com.voidstrike.alanlin.dao;

import java.io.File;

public class TestClass {

	public static void main(String[] args) {
		//System.out.println("Count: " + UserDao.getPostCount("25"));
		File folder = new File(ResourcePath.userDirPath+"25");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			System.out.println(file.getAbsolutePath());
		}
	}

}
