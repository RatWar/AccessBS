package com.besaba.anvarov.accessbs;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class CommonIO {
	public final static String LINE_SEPARTOR = System
			.getProperty("line.separator");
	private static final int BUFFER_SIZE = 2048;
	private static final int EOF_MARK = -1;

	private CommonIO() {
	};

	/*
	 * ��������� ����� ������, �������������� ��������� Closeable, � ��� �����:
	 * ������, �������, ������, c����� � ������ ������. �������� ������ null �
	 * �������� ���������.
	 */
	public static boolean close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * �������� �������������� InputStream � ������. �������� ����� ��
	 * ���������� ������ �� Scanner. ���������� ��������� ��������� �������
	 * (��������� � ���� � �����?).
	 */
	public static String convertStreamToString(InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	/*
	 * ������������� �������� ������ �� ����������� ������ �� InputStream �
	 * OutputStream.
	 */
	public static int writeFromInputToOutput(InputStream source,
			OutputStream dest) {
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = EOF_MARK;
		int count = 0;
		try {
			while ((bytesRead = source.read(buffer)) != EOF_MARK) {
				dest.write(buffer, 0, bytesRead);
				count += bytesRead;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	/* ��������� ����������� ������� ������ � SD-�����. */
	public static boolean isExternalAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}
}
