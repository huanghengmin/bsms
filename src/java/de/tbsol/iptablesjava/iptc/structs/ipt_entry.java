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

public class ipt_entry extends Structure {

	public ipt_entry() {
		super();
	}

	public ipt_entry(Pointer p, int offset) {
		useMemory(p, offset);
		read();
	}

	// / C type : ipt_ip
	public ipt_ip ip;
	// / Mark with fields that we care about.
	public int nfcache;
	// / Size of ipt_entry + matches
	public short target_offset;
	// / Size of ipt_entry + matches + target
	public short next_offset;
	// / Back pointer
	public int comefrom;
	/**
	 * Packet and byte counters.<br>
	 * C type : xt_counters
	 */
	public xt_counters counters;
	/**
	 * The matches (if any), then the target.<br>
	 * C type : unsigned char[0]
	 * 
	 * first match of entry list
	 */
	// public dummyStructure elems;
	// public xt_entry_match match ;
	// public xt_entry_target elems;
}
