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
package de.tbsol.iptablesjava.rules.match;

/**
 * Represents iptables module for TCP Matching
 * <p>
 * These extensions can be used if `--protocol tcp' is specified.
 * <p>
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class ModTcp implements MatchModule {

	/* Values for "inv" field in struct ipt_tcp. */
	public byte XT_TCP_INV_SRCPT = 0x01; /* Invert the sense of source ports. */
	public byte XT_TCP_INV_DSTPT = 0x02; /* Invert the sense of dest ports. */
	public byte XT_TCP_INV_FLAGS = 0x04; /* Invert the sense of TCP flags. */
	public byte XT_TCP_INV_OPTION = 0x08; /* Invert the sense of option test. */
	public byte XT_TCP_INV_MASK = 0x0F; /* All possible flags. */

	/**
	 * Source port start.<br>
	 */
	private int sourcePortStart;
	private int sourcePortEnd;
	/**
	 * Destination port start.<br>
	 */
	private int destinationPortStart;
	private int destinationPortEnd;
	/**
	 * TCP Option iff non-zero<br>
	 */
	private byte option;
	/**
	 * TCP flags mask byte<br>
	 */
	private byte flagMask;
	/**
	 * TCP flags compare byte<br>
	 */
	private byte flagCompare;
	/**
	 * Inverse flags<br>
	 */
	private byte inverseFlags;

	public int getSourcePortStart() {
		return sourcePortStart;
	}

	public void setSourcePortStart(int sourcePortStart) {
		this.sourcePortStart = sourcePortStart;
	}

	public int getSourcePortEnd() {
		return sourcePortEnd;
	}

	public void setSourcePortEnd(int sourcePortEnd) {
		this.sourcePortEnd = sourcePortEnd;
	}

	public int getDestinationPortStart() {
		return destinationPortStart;
	}

	public void setDestinationPortStart(int destinationPortStart) {
		this.destinationPortStart = destinationPortStart;
	}

	public int getDestinationPortEnd() {
		return destinationPortEnd;
	}

	public void setDestinationPortEnd(int destinationPortEnd) {
		this.destinationPortEnd = destinationPortEnd;
	}

	public byte getOption() {
		return option;
	}

	public void setOption(byte option) {
		this.option = option;
	}

	public byte getFlagMask() {
		return flagMask;
	}

	public void setFlagMask(byte flagMask) {
		this.flagMask = flagMask;
	}

	public byte getFlagCompare() {
		return flagCompare;
	}

	public void setFlagCompare(byte flagCompare) {
		this.flagCompare = flagCompare;
	}

	public byte getInverseFlags() {
		return inverseFlags;
	}

	public void setInverseFlags(byte inverseFlags) {
		this.inverseFlags = inverseFlags;
	}

	public boolean isInvertSourcePort() {
		return (inverseFlags & XT_TCP_INV_SRCPT) == XT_TCP_INV_SRCPT;
	}

	public void setInvertSourcePort(boolean invertSourcePort) {
		inverseFlags = (byte) (inverseFlags | XT_TCP_INV_SRCPT);
	}

	public boolean isInvertDestinationPort() {
		return (inverseFlags & XT_TCP_INV_DSTPT) == XT_TCP_INV_DSTPT;
	}

	public void setInvertDestinationPort(boolean invertDestinationPort) {
		inverseFlags = (byte) (inverseFlags | XT_TCP_INV_DSTPT);
	}

	public boolean isInvertTcpFlags() {
		return (inverseFlags & XT_TCP_INV_FLAGS) == XT_TCP_INV_FLAGS;
	}

	public void setInvertTcpFlags(boolean invertTcpFlags) {
		inverseFlags = (byte) (inverseFlags | XT_TCP_INV_FLAGS);
	}

	public boolean isInvertOptionTest() {
		return (inverseFlags & XT_TCP_INV_OPTION) == XT_TCP_INV_OPTION;
	}

	public void setInvertOptionTest(boolean invertOptionTest) {
		inverseFlags = (byte) (inverseFlags | XT_TCP_INV_OPTION);
	}

}
