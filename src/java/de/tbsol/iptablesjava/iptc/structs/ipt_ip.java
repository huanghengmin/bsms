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

import de.tbsol.iptablesjava.iptc.LibIptc;

public class ipt_ip extends Structure {
	public ipt_ip() {
		super();
	}

	public ipt_ip(Pointer p, int offset) {
		useMemory(p, offset);
		read();
	}

	/**
	 * Source and destination IP addr<br>
	 * C type : in_addr
	 */
	public in_addr src;
	/**
	 * Source and destination IP addr<br>
	 * C type : in_addr
	 */
	public in_addr dst;
	/**
	 * Mask for src and dest IP addr<br>
	 * C type : in_addr
	 */
	public in_addr smsk;
	/**
	 * Mask for src and dest IP addr<br>
	 * C type : in_addr
	 */
	public in_addr dmsk;
	// / C type : char[IFNAMSIZ]
	public byte[] iniface = new byte[LibIptc.IFNAMSIZ];
	// / C type : char[IFNAMSIZ]
	public byte[] outiface = new byte[LibIptc.IFNAMSIZ];
	// / C type : unsigned char[IFNAMSIZ]
	public byte[] iniface_mask = new byte[LibIptc.IFNAMSIZ];
	// / C type : unsigned char[IFNAMSIZ]
	public byte[] outiface_mask = new byte[LibIptc.IFNAMSIZ];
	// Protocol, 0 = ANY
	public short proto;
	// / Flags word
	public byte flags;
	// / Inverse flags
	public byte invflags;
}
