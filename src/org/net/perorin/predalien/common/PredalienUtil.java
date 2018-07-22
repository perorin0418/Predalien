package org.net.perorin.predalien.common;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class PredalienUtil {

	private static FileFilter flagFileFilter = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.getName().matches("Recording-.*");
		}
	};

	/**
	 * インスタンス作成禁止
	 */
	private PredalienUtil() {
	}

	/**
	 * 文字列のマウス位置をポイントに変換するべ
	 * @param mousePos マウス位置
	 * @return マウス位置
	 */
	public static Point convMousePosString2Point(String mousePos) {
		String sx = mousePos.split(",")[0];
		String sy = mousePos.split(",")[1];
		int ix = Integer.parseInt(sx.substring(2, sx.length()));
		int iy = Integer.parseInt(sy.substring(2, sy.length()));
		return new Point(ix, iy);
	}

	/**
	 * ポイントのマウス位置を文字列に変換するぞなもし
	 * @param mousePos マウス位置
	 * @return マウス位置
	 */
	public static String convMousePosPoint2String(Point mousePos) {
		return "x=" + mousePos.x + ",y=" + mousePos.y;
	}

	/**
	 * 一時ファイルの格納場所を返却するプリ
	 * @return 一時ファイルの格納場所
	 */
	public static String getTempFilePath() {
		String ret = "";
		try {
			File temp = File.createTempFile("temp-file-name", ".tmp");
			String absolutePath = temp.getAbsolutePath();
			temp.deleteOnExit();
			temp.delete();
			ret = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 一時ファイル格納用のディレクトリ作成
	 */
	public static void createTempDir() {
		String tmpPath = getTempFilePath();
		File tmpDir = new File(tmpPath + File.separator + "Predalien");
		tmpDir.mkdirs();
	}

	/**
	 * 記録中かどうか設定
	 * @param b 記録中
	 */
	public static void setRecording(boolean b) {
		createTempDir();
		File[] files = new File(getTempFilePath() + File.separator + "Predalien").listFiles(flagFileFilter);
		for (File file : files) {
			file.delete();
		}
		try {
			File file = File.createTempFile("Predalien" + File.separator + "Recording-", ".tmp");
			file.deleteOnExit();
			PrintWriter pw = new PrintWriter(file);
			pw.println(String.valueOf(b));
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 記録中かどうか
	 * @return 記録中かどうか
	 */
	public static boolean isRecording() {
		try {
			File file = new File(getTempFilePath() + File.separator + "Predalien").listFiles(flagFileFilter)[0];
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			br.close();
			return Boolean.parseBoolean(line);
		} catch (Exception e) {
			return false;
		}
	}

	/** tmpファイル読み込み用 */
	private static Properties properties = new Properties();

	public static PredalienDatum convProperties2Datum(File file) {
		try {
			// ファイル取得
			FileInputStream fis = new FileInputStream(file);

			// ファイル読み込み
			properties.load(fis);

			// ファイルロック解除
			fis.close();

			// データムに変換
			PredalienDatum datum = propertiesLoad(properties);

			return datum;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void writeDatumOnFile(File file, PredalienDatum datum) {
		try {
			PrintWriter fw = new PrintWriter(file);
			fw.println("Target = " + datum.getTarget());
			fw.println("Name = " + datum.getName());
			fw.println("ClassName = " + datum.getClassName());
			fw.println("MouseInfo = " + datum.getMouseInfo());
			fw.println("RelaMousePos = " + datum.getRelaMousePos());
			fw.println("AbsMousePos = " + datum.getAbsMousePos());
			fw.println("KeyInfo = " + datum.getKeyInfo());
			fw.println("KeyCode = " + datum.getKeyCode());
			fw.println("KeyModifiers = " + datum.getKeyModifiers());
			fw.println("Delay = " + datum.getDelay());
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * tmpファイル読み込み
	 * @param properties
	 * @return
	 */
	private static PredalienDatum propertiesLoad(Properties properties) {
		String target = properties.getProperty("Target");
		String name = properties.getProperty("Name");
		String className = properties.getProperty("ClassName");
		String mouseInfo = properties.getProperty("MouseInfo");
		String relaMousePos = properties.getProperty("RelaMousePos");
		String absMousePos = properties.getProperty("AbsMousePos");
		String keyInfo = properties.getProperty("KeyInfo");
		String keyCode = properties.getProperty("KeyCode");
		String keyModifiers = properties.getProperty("KeyModifiers");
		String delay = properties.getProperty("Delay");
		PredalienDatum datum = new PredalienDatum(target, name, className, delay);
		datum.registMouse(mouseInfo, relaMousePos, absMousePos);
		datum.registKey(keyInfo, keyCode, keyModifiers);
		return datum;
	}

	public static void cleanTmp(){
		// TODO tmpファルダ内のお掃除
	}

}
