package org.net.perorin.predalien.server;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.net.perorin.predalien.client.PredalienDatum;
import org.net.perorin.predalien.client.PredalienUtil;

public class PredalienReciever {

	/** tmpファイルパス */
	private File tmpFilePath = new File(PredalienUtil.getTempFilePath() + File.separator + "Predalien");

	/** tmpファイル読み込み用 */
	private Properties properties = new Properties();

	/** ファイルソート用 */
	private FileComparator fc = new FileComparator();

	/** ファイルフィルターの正規表現 */
	private String tmpPattern = "^[0-9]{16}-PredalienDatum.*$";

	public PredalienReciever() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				// tmpフォルダ内のファイル一覧取得
				File[] files = tmpFilePath.listFiles(filter);

				// 取得できなけりゃリターン
				if (files == null) {
					System.out.println("return");
					return;
				}

				Arrays.sort(files, fc);
				for (File file : files) {
					try {
						// ファイル取得
						FileInputStream fis = new FileInputStream(file);

						// ファイル読み込み
						properties.load(fis);

						// ファイルロック解除
						fis.close();

						// データムに変換
						PredalienDatum datum = load(properties);

						// レシーブ呼び出し
						recieve(datum);

						// ファイルは削除
						file.delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 0, 100);
	}

	/**
	 * これオーバーライドしてやりたい処理をしてくんろ
	 * @param datum
	 */
	public void recieve(PredalienDatum datum) {
		// NOP
	}

	/**
	 * tmpファイル読み込み
	 * @param properties
	 * @return
	 */
	public PredalienDatum load(Properties properties) {
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

	FileFilter filter = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.getName().matches(tmpPattern);
		}
	};

	class FileComparator implements Comparator<File> {

		public int compare(File f1, File f2) {
			long l1 = Long.parseLong(f1.getName().substring(0, 16).replaceAll("^0+", ""));
			long l2 = Long.parseLong(f2.getName().substring(0, 16).replaceAll("^0+", ""));
			if (l1 < l2) {
				return -1;
			} else if (l1 > l2) {
				return 1;
			} else {
				return 0;
			}
		}

	}
}
