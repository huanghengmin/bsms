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
package de.tbsol.iptablesjava.iptc.structs;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class xt_tcp extends Structure {
	public xt_tcp(Pointer pointer, int offset) {
		useMemory(pointer, offset);
		read();
	}

	public xt_tcp() {
		super();
	}

	/**
	 * Source port range.<br>
	 * C type : __u16[2]
	 */
	public short sport_start;
	public short sport_end;
	/**
	 * Destination port range.<br>
	 * C type : __u16[2]
	 */
	public short dport_start;
	public short dport_end;
	/**
	 * TCP Option iff non-zero<br>
	 * C type : __u8
	 */
	public byte option;
	/**
	 * TCP flags mask byte<br>
	 * C type : __u8
	 */
	public byte flg_mask;
	/**
	 * TCP flags compare byte<br>
	 * C type : __u8
	 */
	public byte flg_cmp;
	/**
	 * Inverse flags<br>
	 * C type : __u8
	 */
	public byte invflags;
}