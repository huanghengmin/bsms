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
import de.tbsol.iptablesjava.iptc.structs.xt_tcp;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.match.MatchModule;
import de.tbsol.iptablesjava.rules.match.ModTcp;

/**
 * converts iptables tcp matching module
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class TcpMatchConverter implements MatchModuleConverter {

	@Override
	public MatchModule kernelStructToJava(xt_entry_match matchStruct) {
		xt_tcp xtTcp = new xt_tcp(matchStruct.getPointer(),
				LibIptc.XT_ENTRY_MATCH_SIZE);

		ModTcp tcp = new ModTcp();

		tcp.setSourcePortStart(xtTcp.sport_start);
		tcp.setSourcePortEnd(xtTcp.sport_end);
		tcp.setDestinationPortStart(xtTcp.dport_start);
		tcp.setDestinationPortEnd(xtTcp.dport_end);
		tcp.setOption(xtTcp.option);
		tcp.setFlagMask(xtTcp.flg_mask);
		tcp.setFlagCompare(xtTcp.flg_cmp);
		tcp.setInverseFlags(xtTcp.invflags);
		return tcp;
	}

	@Override
	public int javaToKernelStruct(MatchModule iptcModule,
			xt_entry_match matchStruct, IpRule ipRule)
			throws InvalidArgumentException {

		if (ipRule.getProtocol() != IpRule.IpProto.IPPROTO_TCP) {
			throw new InvalidArgumentException(
					"You have to set protocol of IpRule to TCP in order to use the tcp match module.");
		}

		ModTcp tcpMod = (ModTcp) iptcModule;

		xt_tcp xtTcp = new xt_tcp(matchStruct.getPointer(),
				LibIptc.XT_ENTRY_MATCH_SIZE);

		xtTcp.dport_start = (short) tcpMod.getDestinationPortStart();
		xtTcp.dport_end = (short) tcpMod.getDestinationPortEnd();
		xtTcp.sport_start = (short) tcpMod.getSourcePortStart();
		xtTcp.sport_end = (short) tcpMod.getSourcePortEnd();
		xtTcp.option = tcpMod.getOption();
		xtTcp.flg_mask = tcpMod.getFlagMask();
		xtTcp.flg_cmp = tcpMod.getFlagCompare();
		xtTcp.invflags = tcpMod.getInverseFlags();

		xtTcp.write();

		matchStruct.user.name = new String("tcp").getBytes();
		matchStruct.write();

		return xtTcp.size();
	}

	@Override
	public int getModuleSize() {
		return new xt_tcp().size();
	}
}
