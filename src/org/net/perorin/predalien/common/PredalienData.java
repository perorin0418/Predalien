package org.net.perorin.predalien.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PredalienData implements Iterable<PredalienDatum> {

	private List<PredalienDatum> list = new LinkedList<PredalienDatum>();

	public PredalienData() {
		// NOP
	}

	public PredalienData(PredalienDatum[] datum) {

	}

	public List<PredalienDatum> getList() {
		return list;
	}

	public int size() {
		return list.size();
	}

	public void add(PredalienDatum datum) {
		list.add(datum);
	}

	public void remobe(PredalienDatum datum) {
		list.remove(datum);
	}

	public Iterator<PredalienDatum> iterator() {
		return list.iterator();
	}

}
