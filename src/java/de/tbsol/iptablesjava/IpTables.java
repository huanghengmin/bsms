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

package de.tbsol.iptablesjava;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import de.tbsol.iptablesjava.exceptions.GeneralIptcException;
import de.tbsol.iptablesjava.exceptions.InvalidArgumentException;
import de.tbsol.iptablesjava.exceptions.NoSuchIptcTableException;
import de.tbsol.iptablesjava.exceptions.ReadIptcRuleException;
import de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException;
import de.tbsol.iptablesjava.iptc.LibIptc;
import de.tbsol.iptablesjava.iptc.converter.MatchModuleConverter;
import de.tbsol.iptablesjava.iptc.converter.TargetModuleConverter;
import de.tbsol.iptablesjava.iptc.structs.ipt_entry;
import de.tbsol.iptablesjava.iptc.structs.xt_counters;
import de.tbsol.iptablesjava.iptc.structs.xt_entry_match;
import de.tbsol.iptablesjava.iptc.structs.xt_entry_target;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.IptcCounters;
import de.tbsol.iptablesjava.rules.match.MatchModule;
import de.tbsol.iptablesjava.rules.target.TargetModule;

/**
 * iptables-java is a java library to access iptables functionality using native
 * system access with Java Native Access.
 * <p>
 * This file is the starting point and represents the access to the libiptc
 * library to alter rules and chains in iptables.
 * <p>
 * For usage examples go to http://iptables-java.tb-solutions.biz
 * <p>
 * Not all functions of libiptc are implemented yet. And match and target
 * modules are missing.
 * <p>
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class IpTables {
	public static int ALIGNMENT_NOT_SET = 0;
	public static int ALIGNMENT_4_BYTES = 4;
	public static int ALIGNMENT_8_BYTES = 8;

	private Pointer iptc_handle;
	private static LibIptc libIptc;

	/**
	 * min_alignment is one of IpTables.ALIGNMENT_4_BYTES (mostly for 32 bit
	 * machines) IpTables.ALIGNMENT_8_BYTES (mostly for 64 bit machines)
	 * 
	 * This is needed, because the function IPT_ALIGN in libiptc.h is not yet
	 * implemented correctly.
	 * 
	 * Default: IpTables.ALIGNMENT_4_BYTES
	 */
	private int min_align = ALIGNMENT_4_BYTES;

	/** Clone of preprocessor IPT_ALIGN function in libiptc.h */
	public int IPT_ALIGN(int size) {
		int ret = ((size + ((min_align) - 1))) & ~((min_align - 1));
		return ret;
	}

	/**
	 * This connects java to the libiptc kernel library and takes a snapshot of
	 * the rules. tablename is either filter, nat, mangle or dropped.
	 * 
	 * @param tablename
	 * @throws de.tbsol.iptablesjava.exceptions.NoSuchIptcTableException
	 */
	public IpTables(String tablename) throws NoSuchIptcTableException {

		if (libIptc == null)
			libIptc = (LibIptc) Native.loadLibrary("iptc", LibIptc.class);

		iptc_handle = libIptc.iptc_init(tablename);

		if (iptc_handle == null) {
			throw new NoSuchIptcTableException(
					"itpc table "
							+ tablename
							+ " does not exist, perhaps missed to load kernel module ip_tables.");
		}
	}

	private Pointer getIptcHandle() {
		return iptc_handle;
	}

	/**
	 * Does this chain exist?
	 * 
	 * @param chainname
	 * @return
	 */
	public boolean isChain(String chainname) {
		if (libIptc.iptc_is_chain(chainname, iptc_handle) == 1) {
			return true;
		}
		return false;
	}

	private String getTargetName(ipt_entry rule) {
		return libIptc.iptc_get_target(rule, iptc_handle);
	}

	/**
	 * Read ipc match modules form kernel struct and convert it to a list of
	 * IptcModule
	 * 
	 * @param rule
	 * @return
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 * @throws de.tbsol.iptablesjava.exceptions.ReadIptcRuleException
	 */
	private List<MatchModule> getMatchModules(ipt_entry rule)
			throws UnsupportedIptcModuleException, ReadIptcRuleException {
		ArrayList<MatchModule> list = new ArrayList<MatchModule>();

		int actualOffset = LibIptc.IPT_ENTRY_SIZE;
		int maxOffset = rule.target_offset;

		while (actualOffset < maxOffset) {
			xt_entry_match matchModule = new xt_entry_match(rule.getPointer(),
					actualOffset);

			actualOffset += matchModule.user.match_size;

			String moduleName = matchModule.getPointer().getString(2);
			// String moduleName = new String(matchModule.user.name).trim();

			// erster Buchstabe groß, alles andere klein
			String moduleClassNameType = moduleName.substring(0, 1)
					.toUpperCase() + moduleName.substring(1).toLowerCase();
			String className = "de.tbsol.iptablesjava.iptc.converter.match."
					+ moduleClassNameType + "MatchConverter";

			MatchModuleConverter converter = null;

			// load converter module dynamically
			try {
				converter = (MatchModuleConverter) Class.forName(className)
						.newInstance();
			} catch (ClassNotFoundException e) {
				throw new UnsupportedIptcModuleException("Match module "
						+ className + " for protocol '" + moduleClassNameType
						+ "' is not found (" + e.getMessage() + ")");
			} catch (InstantiationException e) {
				throw new UnsupportedIptcModuleException("Match module "
						+ className + " for protocol '" + moduleClassNameType
						+ "' is not found (" + e.getMessage() + ")");
			} catch (IllegalAccessException e) {
				throw new UnsupportedIptcModuleException("Match module "
						+ className + " for protocol '" + moduleClassNameType
						+ "' is not found (" + e.getMessage() + ")");
			}

			list.add(converter.kernelStructToJava(matchModule));

		}
		return list;
	}

	/**
	 * Append a rule to the chain.
	 * 
	 * @param chainname
	 * @param ipRule
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 * @throws de.tbsol.iptablesjava.exceptions.InvalidArgumentException
	 */
	public void appendEntry(String chainname, IpRule ipRule)
			throws GeneralIptcException, UnsupportedIptcModuleException,
			InvalidArgumentException {
		ipt_entry e = convertIpRuleToIptEntry(ipRule);
		int ret = libIptc.iptc_append_entry(chainname, e, iptc_handle);
		if (ret != 1) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Replace a rule at position ruleNum with this rule.
	 * 
	 * @param chainname
	 * @param ipRule
	 * @param ruleNum
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 * @throws de.tbsol.iptablesjava.exceptions.InvalidArgumentException
	 */
	public void replaceEntry(String chainname, IpRule ipRule, int ruleNum)
			throws GeneralIptcException, UnsupportedIptcModuleException,
			InvalidArgumentException {
		ipt_entry e = convertIpRuleToIptEntry(ipRule);
		int ret = libIptc
				.iptc_replace_entry(chainname, e, ruleNum, iptc_handle);
		if (ret != 1) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Insert a rule at position ruleNum.
	 * 
	 * @param chainname
	 * @param ipRule
	 * @param ruleNum
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 * @throws de.tbsol.iptablesjava.exceptions.InvalidArgumentException
	 */
	public void insertEntry(String chainname, IpRule ipRule, int ruleNum)
			throws GeneralIptcException, UnsupportedIptcModuleException,
			InvalidArgumentException {
		ipt_entry e = convertIpRuleToIptEntry(ipRule);
		int ret = libIptc.iptc_insert_entry(chainname, e, ruleNum, iptc_handle);
		if (ret != 1) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Delete the first rule in "chainName" than matches ipRule.
	 * 
	 * @param chainName
	 * @param ipRule
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 * @throws de.tbsol.iptablesjava.exceptions.InvalidArgumentException
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 */
	public void deleteEntry(String chainName, IpRule ipRule)
			throws GeneralIptcException, UnsupportedIptcModuleException,
			InvalidArgumentException {
		ipt_entry e = convertIpRuleToIptEntry(ipRule);
		int ret = libIptc.iptc_delete_entry(chainName, e, null, iptc_handle);
		if (ret != 1) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Delete the rule in position `rulenum' in `chain'.
	 * 
	 * @param chainName
	 * @param ruleNum
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void deleteNumEntry(String chainName, int ruleNum)
			throws GeneralIptcException {
		int ret = libIptc
				.iptc_delete_num_entry(chainName, ruleNum, iptc_handle);
		if (ret != 1) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	private IpRule convertIptEntryToRule(ipt_entry rule)
			throws ReadIptcRuleException, UnsupportedIptcModuleException {
		if (rule == null)
			return null;
		IpRule ipRule = new IpRule();
		try {
			ipRule.setSource(InetAddress.getByAddress(rule.ip.src.s_addr));
			ipRule.setSourceMask(InetAddress.getByAddress(rule.ip.smsk.s_addr));
			ipRule.setDestination(InetAddress.getByAddress(rule.ip.dst.s_addr));
			ipRule.setDestinationMask(InetAddress
					.getByAddress(rule.ip.dmsk.s_addr));
		} catch (UnknownHostException e) {
			throw new ReadIptcRuleException(
					"Cannot parse IP adress or mask from iptc rule");
		}
		ipRule.setInInterface(new String(rule.ip.iniface).trim());
		ipRule.setOutInterface(new String(rule.ip.outiface).trim());
		ipRule.setProtocol(rule.ip.proto);
		ipRule.setJump(getTargetName(rule));
		ipRule.setBcnt(rule.counters.bcnt);
		ipRule.setPcnt(rule.counters.pcnt);
		ipRule.setModules(getMatchModules(rule));
		ipRule.setTarget(getTarget(rule));
		return ipRule;
	}

	private TargetModule getTarget(ipt_entry rule)
			throws UnsupportedIptcModuleException, ReadIptcRuleException {
		int offset = rule.target_offset;

		xt_entry_target targetModule = new xt_entry_target(rule.getPointer(),
				offset);

		String moduleName = targetModule.getPointer().getString(2);

		if (moduleName.equals("")) {
			// no target module defined
			return null;
		}

		// erster Buchstabe groß, alles andere klein
		String moduleClassNameType = moduleName.substring(0, 1).toUpperCase()
				+ moduleName.substring(1).toLowerCase();
		String className = "de.tbsol.iptablesjava.iptc.converter.target."
				+ moduleClassNameType + "TargetConverter";

		TargetModuleConverter converter = null;

		// load converter module dynamically
		try {
			converter = (TargetModuleConverter) Class.forName(className)
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		} catch (InstantiationException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		} catch (IllegalAccessException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		}

		return converter.kernelStructToJava(targetModule);

	}

	private ipt_entry convertIpRuleToIptEntry(IpRule ipRule)
			throws UnsupportedIptcModuleException, InvalidArgumentException {
		if (ipRule == null)
			return null;

		int matchModulesSize = getMatchModulesSize(ipRule);
		int targetModuleSize = getTargetModuleSize(ipRule);

		long memSize = LibIptc.IPT_ENTRY_SIZE + matchModulesSize
				+ targetModuleSize;

		Memory allocatedMemory = new Memory(memSize);
		// if (memSize >= MAX_MEMORY_ALLOCATED) {
		// throw new RuntimeException(
		// "memsize is bigger than preallocated memory");
		// }
		allocatedMemory.clear();

		ipt_entry e = new ipt_entry(allocatedMemory, 0);

		if (ipRule.getSource() != null)
			e.ip.src.s_addr = ipRule.getSource().getAddress();
		if (ipRule.getSourceMask() != null)
			e.ip.smsk.s_addr = ipRule.getSourceMask().getAddress();
		if (ipRule.getDestination() != null)
			e.ip.dst.s_addr = ipRule.getDestination().getAddress();
		if (ipRule.getDestinationMask() != null)
			e.ip.dmsk.s_addr = ipRule.getDestinationMask().getAddress();
		if (ipRule.getInInterface() != null)
			e.ip.iniface = ipRule.getInInterface().trim().getBytes();
		if (ipRule.getOutInterface() != null)
			e.ip.outiface = ipRule.getOutInterface().trim().getBytes();
		e.ip.proto = ipRule.getProtocol();
		e.counters.bcnt = ipRule.getBcnt();
		e.counters.pcnt = ipRule.getPcnt();
		e.nfcache = 0; // 0x4000?

		e.target_offset = (short) (LibIptc.IPT_ENTRY_SIZE + matchModulesSize);
		e.next_offset = (short) (e.target_offset + targetModuleSize);
		e.write();

		addMatchModulesToIptcEntry(allocatedMemory, e, ipRule);
		addTargetToIptcEntry(allocatedMemory, e, ipRule);
		e.read();

		return e;
	}

	private void addMatchModulesToIptcEntry(Memory m, ipt_entry entry,
			IpRule ipRule) throws UnsupportedIptcModuleException,
			InvalidArgumentException {

		if (ipRule.getModules() == null) {
			return;
		}
		int actualOffset = IPT_ALIGN(LibIptc.IPT_ENTRY_SIZE);

		for (MatchModule module : ipRule.getModules()) {
			xt_entry_match iptcModule = new xt_entry_match(m, actualOffset);

			MatchModuleConverter converter = getMatchConverterForModule(module);

			int modSize = converter.javaToKernelStruct(module, iptcModule,
					ipRule);
			// actualOffset += LibIptc.XT_ENTRY_MATCH_SIZE
			// + converter.getModuleSize();
			int size = IPT_ALIGN(LibIptc.XT_ENTRY_MATCH_SIZE + modSize);
			actualOffset += size;

			iptcModule.read();

			iptcModule.user.match_size = (short) size;
			iptcModule.write();
		}
	}

	private MatchModuleConverter getMatchConverterForModule(MatchModule module)
			throws UnsupportedIptcModuleException {
		String moduleClassNameType = module.getClass().getSimpleName()
				.substring(3);
		String className = "de.tbsol.iptablesjava.iptc.converter.match."
				+ moduleClassNameType + "MatchConverter";

		MatchModuleConverter converter = null;
		try {
			converter = (MatchModuleConverter) Class.forName(className)
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		} catch (InstantiationException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		} catch (IllegalAccessException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		}
		return converter;
	}

	private TargetModuleConverter getTargetConverterForModule(
			TargetModule module) throws UnsupportedIptcModuleException {
		String moduleClassNameType = module.getClass().getSimpleName();
		// .substring(3);
		String className = "de.tbsol.iptablesjava.iptc.converter.target."
				+ moduleClassNameType + "Converter";

		TargetModuleConverter converter = null;
		try {
			converter = (TargetModuleConverter) Class.forName(className)
					.newInstance();
		} catch (ClassNotFoundException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		} catch (InstantiationException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		} catch (IllegalAccessException e) {
			throw new UnsupportedIptcModuleException("Match module "
					+ className + " for protocol '" + moduleClassNameType
					+ "' is not found (" + e.getMessage() + ")");
		}
		return converter;
	}

	private int getTargetModuleSize(IpRule ipRule)
			throws UnsupportedIptcModuleException {
		int size = 0;
		if (ipRule.getTarget() != null) {
			// size += ipRule.getTarget().getModuleSize();
			TargetModuleConverter converter = getTargetConverterForModule(ipRule
					.getTarget());
			size += IPT_ALIGN(LibIptc.XT_ENTRY_TARGET_SIZE
					+ converter.getModuleSize());
		} else if (ipRule.getJump() != null) {
			// i dont know why
			size += IPT_ALIGN(LibIptc.XT_ENTRY_TARGET_SIZE + 4);
		} else {

		}
		return IPT_ALIGN(size);
	}

	private int getMatchModulesSize(IpRule ipRule)
			throws UnsupportedIptcModuleException {
		if (ipRule.getModules() == null) {
			return 0;
		}
		int size = 0;
		for (MatchModule module : ipRule.getModules()) {
			MatchModuleConverter converter = getMatchConverterForModule(module);

			size += IPT_ALIGN(LibIptc.XT_ENTRY_MATCH_SIZE
					+ converter.getModuleSize());
		}
		return size;
	}

	/**
	 * adds the target Structure to ipt_entry
	 * 
	 * @param e
	 * 
	 * @param e
	 * @param ipRule
	 * @throws de.tbsol.iptablesjava.exceptions.InvalidArgumentException
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 */
	private void addTargetToIptcEntry(Pointer p, ipt_entry e, IpRule ipRule)
			throws InvalidArgumentException, UnsupportedIptcModuleException {
		xt_entry_target target = new xt_entry_target(p, e.target_offset);
		if (ipRule.getTarget() != null) {
			// target.user.name = "DNAT".getBytes();

			TargetModuleConverter conv = getTargetConverterForModule(ipRule
					.getTarget());
			int size = conv.javaToKernelStruct(ipRule.getTarget(), target,
					ipRule);
			target.read();
			target.user.target_size = (short) IPT_ALIGN(LibIptc.XT_ENTRY_TARGET_SIZE
					+ size);

		} else if (ipRule.getJump() != null) {
			target.user.name = ipRule.getJump().getBytes();

			// i dont know why we have to add 4 bytes
			target.user.target_size = (short) IPT_ALIGN(LibIptc.XT_ENTRY_TARGET_SIZE + 4);
		} else {

		}
		target.write();
	}

	// private static int convertByteArrayToInt(byte[] buffer) {
	// if (buffer.length != 4) {
	// throw new IllegalArgumentException("buffer length must be 4 bytes!");
	// }
	//
	// int value = (0xFF & buffer[0]) << 24;
	// value |= (0xFF & buffer[1]) << 16;
	// value |= (0xFF & buffer[2]) << 8;
	// value |= (0xFF & buffer[3]);
	//
	// return value;
	// }

	// private static byte[] convertIntToByteArray(int integer) {
	// byte[] bytes = new byte[4];
	// bytes[0] = (byte) (integer >> 24);
	// bytes[1] = (byte) ((integer << 8) >> 24);
	// bytes[2] = (byte) ((integer << 16) >> 24);
	// bytes[3] = (byte) ((integer << 24) >> 24);
	// return bytes;
	// }

	/**
	 * Makes the actual changes.
	 * 
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void commit() throws GeneralIptcException {
		if (libIptc.iptc_commit(iptc_handle) != 1) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	private String strError(int errCode) {
		return libIptc.iptc_strerror(errCode);
	}

	/**
	 * Flushes the entries in the given chain (ie. empties chain).
	 * 
	 * @param chainname
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void flushEntries(String chainname) throws GeneralIptcException {
		int ret = libIptc.iptc_flush_entries(chainname, iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Zeroes the counters in a chain.
	 * 
	 * @param chainname
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void zeroEntries(String chainname) throws GeneralIptcException {
		int ret = libIptc.iptc_zero_entries(chainname, iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Creates a new chain.
	 * 
	 * @param chainname
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void createChain(String chainname) throws GeneralIptcException {
		int ret = libIptc.iptc_create_chain(chainname, iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Deletes a chain.
	 * 
	 * @param chainname
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void deleteChain(String chainname) throws GeneralIptcException {
		int ret = libIptc.iptc_delete_chain(chainname, iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Renames a chain.
	 * 
	 * @param oldName
	 * @param newName
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void renameChain(String oldName, String newName)
			throws GeneralIptcException {
		int ret = libIptc.iptc_rename_chain(oldName, newName, iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Sets the policy on a built-in chain.
	 * 
	 * @param chainName
	 * @param policyName
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void setPolicy(String chainName, String policyName)
			throws GeneralIptcException {
		int ret = libIptc.iptc_set_policy(chainName, policyName, null,
				iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Sets the policy on a built-in chain and set the counters of the chain.
	 * 
	 * @param chainname
	 * @param policyName
	 * @param counter
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public void setCounter(String chainname, String policyName,
			IptcCounters counter) throws GeneralIptcException {
		xt_counters kernelCounter = new xt_counters();
		kernelCounter.bcnt = counter.getByteCounter();
		kernelCounter.pcnt = counter.getPacketCounter();

		int ret = libIptc.iptc_set_policy(chainname, policyName, kernelCounter,
				iptc_handle);
		if (ret == 0) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
	}

	/**
	 * Get the policy of a given built-in chain.
	 * 
	 * @param chainname
	 * @return
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public String getChainPolicy(String chainname) throws GeneralIptcException {
		xt_counters kernelCounter = new xt_counters();

		String name = libIptc.iptc_get_policy(chainname, kernelCounter,
				iptc_handle);
		if (name == null) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
		return name;
	}

	/**
	 * Get the counters of a builtin chain.
	 * 
	 * @param chainname
	 * @return
	 * @throws de.tbsol.iptablesjava.exceptions.GeneralIptcException
	 */
	public IptcCounters getChainCounter(String chainname)
			throws GeneralIptcException {
		xt_counters kernelCounter = new xt_counters();
		String name = libIptc.iptc_get_policy(chainname, kernelCounter,
				iptc_handle);
		if (name == null) {
			throw new GeneralIptcException(libIptc.iptc_strerror(Native
					.getLastError()));
		}
		IptcCounters counters = new IptcCounters(kernelCounter.bcnt,
				kernelCounter.pcnt);
		return counters;
	}

	/**
	 * Cleanup.
	 */
	public void free() {
		libIptc.free(iptc_handle);
	}

	/**
	 * Returns all rules of the chain.
	 * 
	 * @param userChainName
	 * @return
	 * @throws de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException
	 * @throws de.tbsol.iptablesjava.exceptions.ReadIptcRuleException
	 */
	public List<IpRule> getAllRules(String userChainName)
			throws ReadIptcRuleException, UnsupportedIptcModuleException {
		List<IpRule> rulesList = new ArrayList<IpRule>();

		ipt_entry rule = libIptc.iptc_first_rule(userChainName, iptc_handle);

		while (rule != null) {
			rulesList.add(convertIptEntryToRule(rule));
			rule = libIptc.iptc_next_rule(rule, iptc_handle);
		}

		return rulesList;
	}

	/**
	 * returns all names of the chains in this table.
	 * 
	 * @return
	 */
	public List<String> getAllChains() {
		List<String> chainsList = new ArrayList<String>();

		String chainName = libIptc.iptc_first_chain(iptc_handle);

		while (chainName != null) {
			chainsList.add(chainName);
			chainName = libIptc.iptc_next_chain(iptc_handle);
		}

		return chainsList;
	}
}
