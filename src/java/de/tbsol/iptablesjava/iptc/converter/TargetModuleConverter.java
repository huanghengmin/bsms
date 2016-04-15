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
import de.tbsol.iptablesjava.iptc.structs.xt_entry_target;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.target.TargetModule;

/**
 * This interface describes the converter for iptables target modules.
 * 
 * It converts from kernel struct to corresponding java target module and back.
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public interface TargetModuleConverter {
	public TargetModule kernelStructToJava(xt_entry_target targetStruct)
			throws ReadIptcRuleException;

	public int javaToKernelStruct(TargetModule iptcModule,
			xt_entry_target targetStruct, IpRule ipRule)
			throws InvalidArgumentException;

	/**
	 * return size of the matching module struct
	 * 
	 */
	public int getModuleSize();

}
