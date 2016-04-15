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
package de.tbsol.iptablesjava.iptc.converter;

import de.tbsol.iptablesjava.exceptions.InvalidArgumentException;
import de.tbsol.iptablesjava.exceptions.ReadIptcRuleException;
import de.tbsol.iptablesjava.iptc.structs.xt_entry_match;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.match.MatchModule;

/**
 * This interface describes the converter for iptables match modules.
 * 
 * It converts from kernel struct to corresponding java module and back.
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public interface MatchModuleConverter {
	public MatchModule kernelStructToJava(xt_entry_match matchStruct)
			throws ReadIptcRuleException;

	/**
	 * returns size of module
	 * 
	 * @param iptcModule
	 * @param matchStruct
	 * @param ipRule
	 * @return
	 * @throws de.tbsol.iptablesjava.exceptions.InvalidArgumentException
	 */
	public int javaToKernelStruct(MatchModule iptcModule,
			xt_entry_match matchStruct, IpRule ipRule)
			throws InvalidArgumentException;

	/**
	 * return size of the matching module struct
	 * 
	 */
	public int getModuleSize();

}
