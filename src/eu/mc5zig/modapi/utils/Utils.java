package eu.mc5zig.modapi.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

	public static void deleteFiles(File dir) throws IOException {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				deleteFiles(file);
				return;
			}
			Files.delete(file.toPath());
		}
		Files.delete(dir.toPath());
	}
	
}
