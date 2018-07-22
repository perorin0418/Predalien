package org.net.perorin.predalien.common;

import java.util.Vector;

public class PredalienDatum {

	private String target;
	private String name;
	private String className;
	private String mouseInfo;
	private String relaMousePos;
	private String absMousePos;
	private String keyInfo;
	private String keyCode;
	private String keyModifiers;
	private String delay;

	public PredalienDatum() {
	}

	public PredalienDatum(String target, String name, String className, String delay) {
		setTarget(target);
		setName(name);
		setClassName(className);
		setDelay(delay);
	}

	public PredalienDatum(Vector datum) {
		setTarget(String.valueOf(datum.elementAt(0)));
		setName(String.valueOf(datum.elementAt(1)));
		setClassName(String.valueOf(datum.elementAt(2)));
		setMouseInfo(String.valueOf(datum.elementAt(3)));
		setRelaMousePos(String.valueOf(datum.elementAt(4)));
		setAbsMousePos(String.valueOf(datum.elementAt(5)));
		setKeyInfo(String.valueOf(datum.elementAt(6)));
		setKeyCode(String.valueOf(datum.elementAt(7)));
		setKeyModifiers(String.valueOf(datum.elementAt(8)));
		setDelay(String.valueOf(datum.elementAt(9)));
	}

	public PredalienDatum registMouse(String mouseInfo, String relaMousePos, String absMousePos) {
		setMouseInfo(mouseInfo);
		setRelaMousePos(relaMousePos);
		setAbsMousePos(absMousePos);
		return this;
	}

	public PredalienDatum registKey(String keyInfo, String keyCode, String keyModifiers) {
		setKeyInfo(keyInfo);
		setKeyCode(keyCode);
		setKeyModifiers(keyModifiers);
		return this;
	}

	public Object[] toArray() {
		return new Object[] { target, name, className, mouseInfo, relaMousePos, absMousePos, keyInfo, keyCode, keyModifiers, delay };
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		if (target == null || "".equals(target) || "null".equals(target)) {
			this.target = "null";
		} else {
			this.target = target;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || "".equals(name) || "null".equals(name)) {
			this.name = "";
		} else {
			this.name = name;
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		if (className == null || "".equals(className) || "null".equals(className)) {
			this.className = "";
		} else {
			this.className = className;
		}
	}

	public String getMouseInfo() {
		return mouseInfo;
	}

	public void setMouseInfo(String mouseInfo) {
		if (mouseInfo == null || "".equals(mouseInfo) || "null".equals(mouseInfo)) {
			this.mouseInfo = "";
		} else {
			this.mouseInfo = mouseInfo;
		}
	}

	public String getRelaMousePos() {
		return relaMousePos;
	}

	public void setRelaMousePos(String relaMousePos) {
		if (relaMousePos == null || "".equals(relaMousePos) || "null".equals(relaMousePos)) {
			this.relaMousePos = "x=0,y=0";
		} else {
			this.relaMousePos = relaMousePos;
		}
	}

	public String getAbsMousePos() {
		return absMousePos;
	}

	public void setAbsMousePos(String absMousePos) {
		if (absMousePos == null || "".equals(absMousePos) || "null".equals(absMousePos)) {
			this.absMousePos = "x=0,y=0";
		} else {
			this.absMousePos = absMousePos;
		}
	}

	public String getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		if (keyInfo == null || "".equals(keyInfo) || "null".equals(keyInfo)) {
			this.keyInfo = "";
		} else {
			this.keyInfo = keyInfo;
		}
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		if (keyCode == null || "".equals(keyCode) || "null".equals(keyCode)) {
			this.keyCode = "0";
		} else {
			this.keyCode = keyCode;
		}
	}

	public String getKeyModifiers() {
		return keyModifiers;
	}

	public void setKeyModifiers(String keyModifiers) {
		if (keyModifiers == null || "".equals(keyModifiers) || "null".equals(keyModifiers)) {
			this.keyModifiers = "0";
		} else {
			this.keyModifiers = keyModifiers;
		}
	}

	public String getDelay() {
		return delay;
	}

	public int getDelayAsInt() {
		return Integer.parseInt(delay.substring(0, delay.length() - 2));
	}

	public void setDelay(String delay) {
		if (delay == null || "".equals(delay) || "null".equals(delay)) {
			this.delay = "0ms";
		} else {
			this.delay = delay;
		}
	}

}
