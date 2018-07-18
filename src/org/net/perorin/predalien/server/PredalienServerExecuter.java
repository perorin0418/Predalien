package org.net.perorin.predalien.server;

import org.net.perorin.predalien.client.PredalienDatum;
import org.net.perorin.predalien.client.PredalienSender;

public class PredalienServerExecuter {
	public static void main(String[] args) throws InterruptedException {
		PredalienReciever rec = new PredalienReciever() {
			@Override
			public void recieve(PredalienDatum datum) {
				System.out.println(datum.getTarget());
			}
		};
		while (true) {
			Thread.sleep(1000);
			PredalienDatum datum = new PredalienDatum(String.valueOf(System.currentTimeMillis()), "name", "classname", "10ms");
			datum.registMouse("mouseinfo", "rela", "abs");
			datum.registKey("keyinfo", "keycode", "keymod");
			PredalienSender.send(datum);
		}
	}
}
