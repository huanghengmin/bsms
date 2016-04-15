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

/* it is the same as xt_entry_target */
public class xt_entry_match extends Structure {

	public xt_entry_match(Pointer elems, int offset) {
		useMemory(elems, offset);
		read();
	}

	public xt_entry_match() {
		super();
	}

	// / C type : user_struct
	public user_struct user;

	public static class user_struct extends Structure {
		// / C type : __u16
		public short match_size;
		/**
		 * Used by userspace<br>
		 * C type : char[XT_EXTENSION_MAXNAMELEN]
		 */
		public byte[] name = new byte[LibIptc.XT_EXTENSION_MAXNAMELEN];
		// / C type : __u8
		public byte revision;
	};

}
