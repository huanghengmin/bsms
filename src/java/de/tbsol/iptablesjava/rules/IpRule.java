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
package de.tbsol.iptablesjava.rules;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import de.tbsol.iptablesjava.rules.match.MatchModule;
import de.tbsol.iptablesjava.rules.target.TargetModule;

/**
 * IpRule represents a iptables rule model. It contains ip adresses, match
 * modules and target or jump name.
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class IpRule {
	/**
	 * IP protocoll types
	 */
	public static class IpProto {
		/**
		 * dummy protocol
		 */
		public static short IPPROTO_ALL = 0;
		public static short IPPROTO_ICMP = 1;
		public static short IPPROTO_TCP = 6;
		public static short IPPROTO_UDP = 17;
	}

	public IpRule() {
		super();
	}

	public IpRule(InetAddress sourceIp, InetAddress sourceMask,
			InetAddress destinationIp, InetAddress destinationMask, String jump) {
		this.source = sourceIp;
		this.sourceMask = sourceMask;
		this.destination = destinationIp;
		this.destinationMask = destinationMask;
		this.jump = jump;
	}

	/**
	 * source ip address
	 */
	private InetAddress source;
	/**
	 * source ip address mask
	 */
	private InetAddress sourceMask;
	/**
	 * destination ip address
	 */
	private InetAddress destination;
	/**
	 * destination mask
	 */
	private InetAddress destinationMask;
	/**
	 * name of the network device that receives the packet
	 */
	private String inInterface;
	/**
	 * name of the network device the packet will be send
	 */
	private String outInterface;
	/**
	 * network protocoll used
	 * 
	 * returns IpProto value
	 */
	private short protocol;
	/**
	 * simple chain-target if rule matches
	 * 
	 * you can set jump OR target
	 */
	private String jump = "";
	/**
	 * target module
	 * 
	 * you can set jump OR target
	 */
	private TargetModule target;
	/**
	 * packet counter
	 */
	private long pcnt;
	/**
	 * byte counter
	 */
	private long bcnt;
	/**
	 * 
	 * @return
	 */
	private List<MatchModule> modules = new ArrayList<MatchModule>();

	public InetAddress getSource() {
		return source;
	}

	public void setSource(InetAddress source) {
		this.source = source;
	}

	public InetAddress getDestination() {
		return destination;
	}

	public void setDestination(InetAddress destination) {
		this.destination = destination;
	}

	public String getInInterface() {
		return inInterface;
	}

	public void setInInterface(String inInterface) {
		this.inInterface = inInterface;
	}

	public String getOutInterface() {
		return outInterface;
	}

	public void setOutInterface(String outInterface) {
		this.outInterface = outInterface;
	}

	public short getProtocol() {
		return protocol;
	}

	public void setProtocol(short protocol) {
		this.protocol = protocol;
	}

	public String getJump() {
		return jump;
	}

	public void setJump(String jump) {
		this.jump = jump;
	}

	public long getPcnt() {
		return pcnt;
	}

	public void setPcnt(long pcnt) {
		this.pcnt = pcnt;
	}

	public long getBcnt() {
		return bcnt;
	}

	public void setBcnt(long bcnt) {
		this.bcnt = bcnt;
	}

	public InetAddress getSourceMask() {
		return sourceMask;
	}

	public void setSourceMask(InetAddress sourceMask) {
		this.sourceMask = sourceMask;
	}

	public InetAddress getDestinationMask() {
		return destinationMask;
	}

	public void setDestinationMask(InetAddress destinationMask) {
		this.destinationMask = destinationMask;
	}

	public List<MatchModule> getModules() {
		return modules;
	}

	public void setModules(List<MatchModule> modules) {
		this.modules = modules;
	}

	public void addModule(MatchModule module) {
		this.modules.add(module);
	}

	public TargetModule getTarget() {
		return target;
	}

	public void setTarget(TargetModule target) {
		this.target = target;
	}

}
