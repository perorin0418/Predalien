package org.perorin.predalien;

import java.awt.Point;

public class PredalienUtil {

	public static void main(String[] args) {
		System.out.println(convMousePosString2Point("x=100,y=520"));
		System.out.println(convMousePosPoint2String(new Point(152, 954)));
	}

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

}
