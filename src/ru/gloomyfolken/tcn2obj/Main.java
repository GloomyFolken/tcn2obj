package ru.gloomyfolken.tcn2obj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.gloomyfolken.tcn2obj.obj.ObjModel;
import ru.gloomyfolken.tcn2obj.tcn.TechneModel;

public class Main {

	private static final String tcn = ".tcn";
	private static final String obj = ".obj";
	
	public static void main(String[] args) throws Exception {
		File baseDir = new File(".");
		List<File> files = getFiles(baseDir, tcn);
		Converter converter = new Converter();
		
		for (File tcnFile : files){
			System.out.println("Processing " + tcnFile.getAbsolutePath());
			
			String filename = tcnFile.getName().substring(0, tcnFile.getName().length()-obj.length());
			File objFile = new File(tcnFile.getParentFile(), filename + ".obj");
			
			if (objFile.exists()){
				System.out.println("File " + objFile.getAbsolutePath() + " already exists.");
				continue;
			}
			
			TechneModel tcnModel = new TechneModel(tcnFile);
			ObjModel objModel = converter.tcn2obj(tcnModel, 0.0625f);

			saveFile(objFile, objModel.toStringList());
		}
		
		System.out.println("Done!");
	}
	
	private static void saveFile(File file, List<String> lines) throws IOException{
		FileWriter writer = new FileWriter(file); 
		for(String str: lines) {
		  writer.write(str);
		  writer.write("\n");
		}
		writer.close();
	}
	
	private static List<File> getFiles(File dir, String postfix) throws IOException{
		ArrayList<File> files = new ArrayList<File>();
		File[] filesArray = dir.listFiles();
		if (filesArray != null){
			for (File file : dir.listFiles()){
				if (file.isDirectory()){
					files.addAll(getFiles(file, postfix));
				} else if (file.getName().endsWith(postfix)){
					files.add(file);
				}
			}
		}
		return files;
	}
	
}
