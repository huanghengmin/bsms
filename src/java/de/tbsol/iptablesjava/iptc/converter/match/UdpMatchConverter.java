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
import de.tbsol.iptablesjava.iptc.structs.xt_udp;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.match.MatchModule;
import de.tbsol.iptablesjava.rules.match.ModUdp;

/**
 * converts iptables udp match module
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class UdpMatchConverter implements MatchModuleConverter {

	@Override
	public MatchModule kernelStructToJava(xt_entry_match matchStruct) {
		xt_udp xtUdp = new xt_udp(matchStruct.getPointer(),
				LibIptc.XT_ENTRY_MATCH_SIZE);

		ModUdp udp = new ModUdp();

		udp.setSourcePortStart(xtUdp.sport_start);
		udp.setSourcePortEnd(xtUdp.sport_end);
		udp.setDestinationPortStart(xtUdp.dport_start);
		udp.setDestinationPortEnd(xtUdp.dport_end);
		udp.setInverseFlags(xtUdp.invflags);

		return udp;
	}

	@Override
	public int javaToKernelStruct(MatchModule iptcModule,
			xt_entry_match matchStruct, IpRule ipRule)
			throws InvalidArgumentException {
		if (ipRule.getProtocol() != IpRule.IpProto.IPPROTO_UDP) {
			throw new InvalidArgumentException(
					"You have to set protocol of IpRule to UDP in order to use the udp match module.");
		}

		ModUdp udpMod = (ModUdp) iptcModule;
		xt_udp xtUdp = new xt_udp(matchStruct.getPointer(),
				LibIptc.XT_ENTRY_MATCH_SIZE);

		xtUdp.dport_start = (short) udpMod.getDestinationPortStart();
		xtUdp.dport_end = (short) udpMod.getDestinationPortEnd();
		xtUdp.sport_start = (short) udpMod.getSourcePortStart();
		xtUdp.sport_end = (short) udpMod.getSourcePortEnd();
		xtUdp.invflags = udpMod.getInverseFlags();

		xtUdp.write();

		matchStruct.user.name = new String("udp").getBytes();
		matchStruct.write();
		return xtUdp.size();
	}

	@Override
	public int getModuleSize() {
		return new xt_udp().size();
	}

}
