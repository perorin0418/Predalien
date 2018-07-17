package org.net.perorin.predalien.server;

public class PredalienServerExecuter {
	public static void main(String[] args) {
		String str = "0123456789013456-PredalienDatum.tmp";
		System.out.println(str.matches("^[0-9]{16}-PredalienDatum.tmp$"));
	}
}
