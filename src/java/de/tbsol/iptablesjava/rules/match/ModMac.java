/**
 * Copyright 2011-2011 by Torsten Boob
 * 
 * This file is part of iptables-java project.
 * 
 * iptables-java is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * iptables-java is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this software. If not, see http://www.gnu.org/licenses/.
 * 
 */
package de.tbsol.iptablesjava.rules.match;

import java.util.Formatter;

/**
 * Match source MAC address. It must be of the form XX:XX:XX:XX:XX:XX. Note that
 * this only makes sense for packets coming from an Ethernet device and entering
 * the PREROUTING, FOR‐ WARD or INPUT chains.
 * <p>
 * Description is taken from iptables manpage.
 * <p>
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class ModMac implements MatchModule {
	// shouldnt this be 10 ?!?
	// public static int MODULE_SIZE = 12;
	public static int ETH_LEN = 6;

	private byte[] srcaddr;

	private int invert;

	public byte[] getSrcaddr() {
		return srcaddr;
	}

	public void setSrcaddr(byte[] srcaddr) {
		this.srcaddr = srcaddr;
	}

	public int getInvert() {
		return invert;
	}

	public void setInvert(int invert) {
		this.invert = invert;
	}

	public boolean isInvert() {
		return invert == 1;
	}

	public void setInvert(boolean value) {
		if (value)
			invert = 1;
		else
			invert = 0;
	}

	public String getSrcaddrAsString() {
		Formatter f = new Formatter();
		String out = f.format("%02X:%02X:%02X:%02X:%02X:%02X", srcaddr[0],
				srcaddr[1], srcaddr[2], srcaddr[3], srcaddr[4], srcaddr[5])
				.toString();
		return out;
	}
}
