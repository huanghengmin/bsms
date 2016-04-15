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

public class xt_counters extends Structure {

	public xt_counters() {
		super();
	}

	public xt_counters(Pointer p, int offset) {
		useMemory(p, offset);
		read();
	}

	public long pcnt;
	public long bcnt;
}