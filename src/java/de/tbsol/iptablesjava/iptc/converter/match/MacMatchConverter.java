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
package de.tbsol.iptablesjava.iptc.converter.match;

import de.tbsol.iptablesjava.exceptions.InvalidArgumentException;
import de.tbsol.iptablesjava.iptc.LibIptc;
import de.tbsol.iptablesjava.iptc.converter.MatchModuleConverter;
import de.tbsol.iptablesjava.iptc.structs.xt_entry_match;
import de.tbsol.iptablesjava.iptc.structs.xt_mac_info;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.match.MatchModule;
import de.tbsol.iptablesjava.rules.match.ModMac;

/**
 * Converts iptables mac matching module
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class MacMatchConverter implements MatchModuleConverter {

	@Override
	public MatchModule kernelStructToJava(xt_entry_match matchStruct) {
		xt_mac_info xt = new xt_mac_info(matchStruct.getPointer(),
				LibIptc.XT_ENTRY_MATCH_SIZE);

		ModMac mod = new ModMac();

		mod.setSrcaddr(xt.srcaddr);
		mod.setInvert(xt.invert);

		return mod;
	}

	@Override
	public int javaToKernelStruct(MatchModule iptcModule,
			xt_entry_match matchStruct, IpRule ipRule)
			throws InvalidArgumentException {

		ModMac mod = (ModMac) iptcModule;

		xt_mac_info xt = new xt_mac_info(matchStruct.getPointer(),
				LibIptc.XT_ENTRY_MATCH_SIZE);
		xt.srcaddr = mod.getSrcaddr();
		xt.invert = mod.getInvert();

		xt.write();

		matchStruct.user.name = new String("mac").getBytes();
		matchStruct.write();

		return xt.size();
	}

	@Override
	public int getModuleSize() {
		return new xt_mac_info().size();
	}

}
