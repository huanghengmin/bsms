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
package de.tbsol.iptablesjava.iptc;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

import de.tbsol.iptablesjava.iptc.structs.ipt_entry;
import de.tbsol.iptablesjava.iptc.structs.xt_counters;
import de.tbsol.iptablesjava.iptc.structs.xt_entry_match;
import de.tbsol.iptablesjava.iptc.structs.xt_entry_target;

/**
 * 
 * JNA interface, you should not use this directly. Use IpTables class instead.
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
/**
 * http://tldp.org/HOWTO/Querying-libiptc-HOWTO/
 * http://wiki.tldp.org/iptc%20library%20HOWTO
 */
public interface LibIptc extends Library {

	/**
	 * size including counter and ipt_ip struct
	 */
	public static int IPT_ENTRY_SIZE = new ipt_entry().size();

	public static int IFNAMSIZ = 16;
	public int MAX_TARGET_NAME = 64;
	public static int XT_EXTENSION_MAXNAMELEN = 29;

	public static int XT_ENTRY_MATCH_SIZE = new xt_entry_match().size();

	public static int XT_ENTRY_TARGET_SIZE = new xt_entry_target().size();

	// Maintenance
	public Pointer iptc_init(String tablename);

	public void iptc_free(Pointer iptc_handle);

	// Chains
	public int iptc_is_chain(String chain, Pointer iptc_handle);

	public int iptc_create_chain(String chainName, Pointer iptc_handle);

	public int iptc_delete_chain(String chainName, Pointer iptc_handle);

	public int iptc_rename_chain(String oldName, String newName,
			Pointer iptc_handle);

	public String iptc_first_chain(Pointer iptc_handle);

	public String iptc_next_chain(Pointer iptc_handle);

	public String iptc_get_policy(String chainName, xt_counters counters,
			Pointer iptc_handle);

	public int iptc_set_policy(String chainName, String policyName,
			xt_counters counters, Pointer iptc_handle);

	public int iptc_flush_entries(String chain, Pointer iptc_handle);

	public int iptc_zero_entries(String chain, Pointer iptc_handle);

	// public

	// Rules
	public ipt_entry iptc_first_rule(String chain, Pointer iptc_handle);

	public ipt_entry iptc_next_rule(ipt_entry e, Pointer iptc_handle);

	public String iptc_get_target(ipt_entry e, Pointer iptc_handle);

	public int iptc_builtin(String chain, Pointer iptc_handle);

	public int iptc_append_entry(String chain, ipt_entry entry,
			Pointer iptc_handle);

	public int iptc_insert_entry(String chain, ipt_entry entry, int ruleNum,
			Pointer iptc_handle);

	public int iptc_replace_entry(String chain, ipt_entry entry, int ruleNum,
			Pointer iptc_handle);

	public int iptc_delete_entry(String chain, ipt_entry entry,
			String unusedMatchMask, Pointer iptc_handle);

	public int iptc_delete_num_entry(String chain, int ruleNum,
			Pointer iptc_handle);

	public int iptc_commit(Pointer iptc_handle);

	public String iptc_strerror(Integer err);

	/**
	 * Cleanup after iptc_init().
	 * 
	 * @param iptc_handle
	 */
	public void free(Pointer iptc_handle);

}
