package com.besaba.anvarov.accessbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtils {
	public static File EXTERNAL_DIR = Environment.getExternalStorageDirectory();

	private FileUtils() {
	};

	public static boolean writeInternal(Context context, InputStream source,
			String fileName, int mode) {
		BufferedOutputStream dest = null;

		try {
			dest = new BufferedOutputStream(context.openFileOutput(fileName,
					mode));
			CommonIO.writeFromInputToOutput(source, dest);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			CommonIO.close(dest);
		}
	}

	public static InputStream readFromInternalile(Context context,
			String fileName) {
		BufferedInputStream source = null;
		try {
			source = new BufferedInputStream(context.openFileInput(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return source;
	}

	/*
	 * остановлюсь на параметре mode метода writeInternal(). Этот параметр
	 * задает разрешения и стратегию работы с фалом и может иметь следующие
	 * значиния:
	 * 
	 * MODE_APPEND = 0×00008000 : сообщает о том, что если файл существует, то
	 * не нужно затирать содежимое, а дописывать в конец; MODE_PRIVATE =
	 * 0×00000000 : говорит о том, что доступ к файлу будет только у приложения,
	 * которое его создало; MODE_WORLD_READABLE = 0×00000001 : данной файл
	 * смогут читать другие приложения; MODE_WORLD_WRITEABLE = 0×00000002 :
	 * данный файл будет доступен для записи другим приложениям;
	 */

	public static boolean writeExternal(InputStream source, String filePath, Boolean append) {
		if (!CommonIO.isExternalAvailable())
			return false;

		File dest = new File(EXTERNAL_DIR + File.separator + filePath);
		FileOutputStream writer = null;

		try {
			if (!dest.exists()) {
				dest.getParentFile().mkdirs();
				dest.createNewFile();
			}
			writer = new FileOutputStream(dest, append);
			CommonIO.writeFromInputToOutput(source, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			CommonIO.close(writer);
		}
	}

	public static InputStream readExternal(String filePath) {
		if (!CommonIO.isExternalAvailable())
			return null;

		File source = new File(EXTERNAL_DIR + File.separator + filePath);
		InputStream out = null;
		if (source.exists()) {
			try {
				out = new FileInputStream(source);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return out;
	}
}
