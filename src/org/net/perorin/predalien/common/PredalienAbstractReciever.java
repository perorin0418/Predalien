package org.net.perorin.predalien.common;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public abstract class PredalienAbstractReciever {

	/** tmpファイルパス */
	private File tmpFilePath = new File(PredalienUtil.getTempFilePath() + File.separator + "Predalien");

	/** ファイルソート用 */
	private FileComparator fc = new FileComparator();

	/**
	 * ファイルを特定する正規表現
	 * @return 正規表現
	 */
	public abstract String getPattern();

	/**
	 * 受信時の処理
	 * @param file 受信ファイル
	 * @return
	 * <ul>
	 * <li> true - 処理後ファイル削除
	 * <li> false - 処理後ファイル削除しない
	 * </ul>
	 */
	public abstract boolean recieve(File file);

	public void run() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				// tmpフォルダ内のファイル一覧取得
				File[] files = tmpFilePath.listFiles(filter);

				// 取得できなけりゃリターン
				if (files == null) {
					return;
				}

				Arrays.sort(files, fc);
				for (File file : files) {
					try {
						boolean filedel = recieve(file);

						if (filedel) {
							// ファイルは削除
							file.delete();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 0, 100);
	}

	FileFilter filter = new FileFilter() {

		public boolean accept(File pathname) {
			return pathname.getName().matches(getPattern());
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
