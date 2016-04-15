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
package de.tbsol.iptables.ipv4;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.tbsol.iptablesjava.IpTables;
import de.tbsol.iptablesjava.exceptions.GeneralIptcException;
import de.tbsol.iptablesjava.exceptions.InvalidArgumentException;
import de.tbsol.iptablesjava.exceptions.NoSuchIptcTableException;
import de.tbsol.iptablesjava.exceptions.ReadIptcRuleException;
import de.tbsol.iptablesjava.exceptions.UnsupportedIptcModuleException;
import de.tbsol.iptablesjava.rules.IpRule;
import de.tbsol.iptablesjava.rules.IptcCounters;
import de.tbsol.iptablesjava.rules.IpRule.IpProto;
import de.tbsol.iptablesjava.rules.match.MatchModule;
import de.tbsol.iptablesjava.rules.match.ModMac;
import de.tbsol.iptablesjava.rules.match.ModTcp;
import de.tbsol.iptablesjava.rules.match.ModUdp;
import de.tbsol.iptablesjava.rules.target.DnatTarget;
import de.tbsol.iptablesjava.rules.target.RedirectTarget;

/**
 * JUnit test class. Must be run with root privileges.
 * 
 * @author Torsten Boob <info@tb-solutions.biz>
 * 
 */
public class IpTablesImplTest extends TestCase {

	// private IpTables filterIpTables;

	@Override
	protected void setUp() throws Exception {
		// filterIpTables = new IpTablesImpl("filter");
		// assertNotNull(filterIpTables.getIptcHandle());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIptc_is_chain() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			assertTrue("INPUT Rule gibt es immer",
					filterIpTables.isChain("INPUT"));
			filterIpTables.free();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testGetAllChains() {
		try {
			IpTables myNatTable = new IpTables("filterq");
			List<String> rules = myNatTable.getAllChains();
			assertEquals("INPUT", rules.get(0));
			assertEquals("FORWARD", rules.get(1));
			assertEquals("OUTPUT", rules.get(2));
			myNatTable.free();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptc_create_and_delete_chain() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			assertFalse(filterIpTables.isChain("JUNITTEST"));
			filterIpTables.createChain("JUNITTEST");
			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertTrue(filterIpTables.isChain("JUNITTEST"));
			filterIpTables.deleteChain("JUNITTEST");
			assertFalse(filterIpTables.isChain("JUNITTEST"));
			filterIpTables.commit();
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptc_create_rename_delete_chain() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			assertFalse(filterIpTables.isChain("JUNITTEST"));
			filterIpTables.createChain("JUNITTEST");
			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertTrue(filterIpTables.isChain("JUNITTEST"));
			filterIpTables.renameChain("JUNITTEST", "JUNITTESTNEU");

			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertFalse(filterIpTables.isChain("JUNITTEST"));
			assertTrue(filterIpTables.isChain("JUNITTESTNEU"));
			filterIpTables.deleteChain("JUNITTESTNEU");
			assertFalse(filterIpTables.isChain("JUNITTESTNEU"));
			filterIpTables.commit();
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptcGetFirstAndNextChain() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			assertEquals("Erste Chain ist INPUT", "INPUT", filterIpTables
					.getAllChains().get(0));
			assertEquals("Second chain is always FORWARD", "FORWARD",
					filterIpTables.getAllChains().get(1));
			filterIpTables.free();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}

	}

	public void testIptcPolicy() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			filterIpTables.setPolicy("OUTPUT", "ACCEPT");

			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertEquals("Policy of OUTPUT should be ACCEPT",
					filterIpTables.getChainPolicy("OUTPUT"), "ACCEPT");
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptcPolicyCounter() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			IptcCounters c = new IptcCounters(1111, 2222);
			filterIpTables.setCounter("OUTPUT", "ACCEPT", c);

			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertEquals(1111, filterIpTables.getChainCounter("OUTPUT")
					.getByteCounter());
			assertEquals(2222, filterIpTables.getChainCounter("OUTPUT")
					.getPacketCounter());

			IptcCounters d = new IptcCounters(0, 0);
			filterIpTables.setCounter("OUTPUT", "ACCEPT", d);

			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertEquals(0, filterIpTables.getChainCounter("OUTPUT")
					.getByteCounter());
			assertEquals(0, filterIpTables.getChainCounter("OUTPUT")
					.getPacketCounter());
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void atestGetFirstRule() {
		try {
			IpTables filterIpTables = new IpTables("nat");
			IpRule a = filterIpTables.getAllRules("BLABLA").get(0);
			System.out.println(a.getInInterface());
			System.out.println(a.getSource().toString());
			System.out.println(a.getDestination().toString());
			System.out.println(a.getDestinationMask().toString());
			// System.out.println(a.getTarget().getName());
			// System.out.println("*"
			// + ((ModUdp) a.getModules().get(0))
			// .getDestinationPortStart());

			System.out.println("ModPrint:" + a.getTarget().toString());// ads

			// ModMac mod = (ModMac) a.getModules().get(0);

			// byte[] hu = { 1, 2, 3, 4, 5, 6 };
			// ((ModMac) a.getModules().get(0)).setSrcaddr(hu);
			// System.out.println("mac:"
			// + new String(((ModMac) a.getModules().get(0))
			// .getSrcaddrAsString()));
			// byte[] bla = ((ModMac) a.getModules().get(0)).getSrcaddr();
			// for (int i = 0; i < bla.length; i++) {
			// System.out.printf("%02X:", bla[i]);
			// }
			// System.out.println(Arrays.toString(bla));
			filterIpTables.free();

		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}

	}

	public void testIptcCreateAndDeleteRule() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			filterIpTables.createChain("RULETESTJUNIT");
			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			assertTrue(filterIpTables.isChain("RULETESTJUNIT"));
			assertNull("Chain should be empty on startup", filterIpTables
					.getAllRules("RULETESTJUNIT").get(0));

			IpRule rule = new IpRule();
			rule.setSource(InetAddress.getByName("192.168.11.11"));
			rule.setSourceMask(InetAddress.getByName("255.255.255.127"));
			rule.setDestination(InetAddress.getByName("192.168.22.22"));
			rule.setDestinationMask(InetAddress.getByName("255.255.255.1"));
			rule.setInInterface("eth0");
			rule.setOutInterface("eth0");
			rule.setProtocol(IpProto.IPPROTO_TCP);
			rule.setBcnt(5);
			rule.setPcnt(7);
			rule.setJump("ACCEPT");

			filterIpTables.appendEntry("RULETESTJUNIT", rule);
			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			IpRule ruleOut = filterIpTables.getAllRules("RULETESTJUNIT").get(0);
			assertEquals(rule.getSource(), ruleOut.getSource());
			assertEquals(rule.getSourceMask(), ruleOut.getSourceMask());
			assertEquals(rule.getDestination(), ruleOut.getDestination());
			assertEquals(rule.getDestinationMask(),
					ruleOut.getDestinationMask());
			assertEquals(rule.getInInterface(), ruleOut.getInInterface());
			assertEquals(rule.getOutInterface(), ruleOut.getOutInterface());
			assertEquals(rule.getProtocol(), ruleOut.getProtocol());
			assertEquals(rule.getBcnt(), ruleOut.getBcnt());
			assertEquals(rule.getPcnt(), ruleOut.getPcnt());
			assertEquals("ACCEPT", rule.getJump());

			filterIpTables.flushEntries("RULETESTJUNIT");
			filterIpTables.deleteChain("RULETESTJUNIT");
			filterIpTables.commit();
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptablesCreateAndDeleteTcpModule() {
		try {

			IpTables filterIpTables = new IpTables("filter");

			System.out.println("IPT_ALIGNTEST:7 -> "
					+ filterIpTables.IPT_ALIGN(7));
			System.out.println("IPT_ALIGNTEST:8 -> "
					+ filterIpTables.IPT_ALIGN(8));
			System.out.println("IPT_ALIGNTEST:9 -> "
					+ filterIpTables.IPT_ALIGN(9));
			System.out.println("IPT_ALIGNTEST:10 -> "
					+ filterIpTables.IPT_ALIGN(10));
			System.out.println("IPT_ALIGNTEST:11 -> "
					+ filterIpTables.IPT_ALIGN(11));
			System.out.println("IPT_ALIGNTEST:12 -> "
					+ filterIpTables.IPT_ALIGN(12));
			System.out.println("IPT_ALIGNTEST:13 -> "
					+ filterIpTables.IPT_ALIGN(13));
			System.out.println("IPT_ALIGNTEST:14 -> "
					+ filterIpTables.IPT_ALIGN(14));
			System.out.println("IPT_ALIGNTEST:15 -> "
					+ filterIpTables.IPT_ALIGN(15));
			System.out.println("IPT_ALIGNTEST:16 -> "
					+ filterIpTables.IPT_ALIGN(16));
			System.out.println("IPT_ALIGNTEST:17 -> "
					+ filterIpTables.IPT_ALIGN(17));
			System.out.println("IPT_ALIGNTEST:18 -> "
					+ filterIpTables.IPT_ALIGN(18));

			filterIpTables.createChain("RULETESTJUNIT2");

			assertTrue(filterIpTables.isChain("RULETESTJUNIT2"));
			assertEquals("Chain should be empty on startup", 0, filterIpTables
					.getAllRules("RULETESTJUNIT2").size());

			IpRule rule = new IpRule();
			rule.setProtocol(IpProto.IPPROTO_TCP);

			ModTcp tcp = new ModTcp();
			tcp.setDestinationPortStart(50);
			tcp.setDestinationPortEnd(58);
			tcp.setSourcePortStart(60);
			tcp.setSourcePortEnd(68);
			tcp.setInvertDestinationPort(true);

			ArrayList<MatchModule> modList = new ArrayList<MatchModule>();
			modList.add(tcp);

			rule.setJump("ACCEPT");
			rule.setModules(modList);

			filterIpTables.appendEntry("RULETESTJUNIT2", rule);
			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");

			IpRule ruleOut = filterIpTables.getAllRules("RULETESTJUNIT2")
					.get(0);
			ModTcp tcpOut = (ModTcp) ruleOut.getModules().get(0);
			assertEquals(50, tcpOut.getDestinationPortStart());
			assertEquals(58, tcpOut.getDestinationPortEnd());
			assertEquals(60, tcpOut.getSourcePortStart());
			assertEquals(68, tcpOut.getSourcePortEnd());
			assertTrue(tcpOut.isInvertDestinationPort());
			assertFalse(tcpOut.isInvertOptionTest());
			assertFalse(tcpOut.isInvertSourcePort());
			assertFalse(tcpOut.isInvertTcpFlags());

			filterIpTables.flushEntries("RULETESTJUNIT2");
			filterIpTables.deleteChain("RULETESTJUNIT2");
			filterIpTables.commit();
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptablesCreateAndDeleteUdpModule() {
		try {
			IpTables filterIpTables = new IpTables("filter");
			filterIpTables.createChain("RULETESTJUNIT2");

			assertTrue(filterIpTables.isChain("RULETESTJUNIT2"));
			assertEquals("Chain should be empty on startup", 0, filterIpTables
					.getAllRules("RULETESTJUNIT2").size());

			IpRule rule = new IpRule();
			rule.setProtocol(IpProto.IPPROTO_UDP);
			ModUdp udp = new ModUdp();
			udp.setDestinationPortStart(50);
			udp.setDestinationPortEnd(58);
			udp.setSourcePortStart(60);
			udp.setSourcePortEnd(68);
			udp.setInvertDestinationPort(false);

			ArrayList<MatchModule> modList = new ArrayList<MatchModule>();
			modList.add(udp);
			rule.setModules(modList);

			filterIpTables.appendEntry("RULETESTJUNIT2", rule);
			filterIpTables.commit();
			filterIpTables.free();

			filterIpTables = new IpTables("filter");
			IpRule ruleOut = filterIpTables.getAllRules("RULETESTJUNIT2")
					.get(0);
			ModUdp udpOut = (ModUdp) ruleOut.getModules().get(0);
			assertEquals(50, udpOut.getDestinationPortStart());
			assertEquals(58, udpOut.getDestinationPortEnd());
			assertEquals(60, udpOut.getSourcePortStart());
			assertEquals(68, udpOut.getSourcePortEnd());
			assertTrue(udpOut.isInvertDestinationPort());
			assertFalse(udpOut.isInvertSourcePort());
			filterIpTables.flushEntries("RULETESTJUNIT2");
			filterIpTables.deleteChain("RULETESTJUNIT2");
			filterIpTables.commit();
			filterIpTables.free();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testIptablesCreateAndDeleteMacModule() {
		try {
			IpTables ipt = new IpTables("filter");
			ipt.createChain("RULETESTJUNIT2");

			assertTrue(ipt.isChain("RULETESTJUNIT2"));
			assertEquals("Chain should be empty on startup", 0, ipt
					.getAllRules("RULETESTJUNIT2").size());

			IpRule rule = new IpRule();
			rule.setProtocol(IpProto.IPPROTO_UDP);
			ModMac mod = new ModMac();
			byte[] hu = { 1, 2, 3, 4, 5, 6 };
			mod.setSrcaddr(hu);
			mod.setInvert(true);

			ArrayList<MatchModule> modList = new ArrayList<MatchModule>();
			modList.add(mod);
			rule.setModules(modList);

			ipt.appendEntry("RULETESTJUNIT2", rule);
			ipt.commit();
			ipt.free();

			ipt = new IpTables("filter");
			IpRule ruleOut = ipt.getAllRules("RULETESTJUNIT2").get(0);
			ModMac modOut = (ModMac) ruleOut.getModules().get(0);
			assertEquals(new String(mod.getSrcaddr()),
					new String(modOut.getSrcaddr()));
			assertEquals(mod.getInvert(), modOut.getInvert());
			assertEquals(mod.isInvert(), modOut.isInvert());

			ipt.flushEntries("RULETESTJUNIT2");
			ipt.deleteChain("RULETESTJUNIT2");
			ipt.commit();
			ipt.free();

		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void testDnatTarget() {
		try {
			IpTables ipt = new IpTables("nat");
			ipt.createChain("TESTTEST");
			assertTrue(ipt.isChain("TESTTEST"));
			ipt.commit();
			ipt.free();

			ipt = new IpTables("nat");
			IpRule ipRule = new IpRule();

			ipRule.setProtocol(IpProto.IPPROTO_TCP);

			DnatTarget d = new DnatTarget();
			d.setMinPort(33);
			d.setMaxPort(44);
			// assertEquals(d.getMinPort(), 3434);
			d.setMinIp(InetAddress.getByName("192.168.11.34"));
			d.setMaxIp(InetAddress.getByName("192.168.11.38"));

			assertFalse(d.isRandom());
			d.setRandom(true);
			assertTrue(d.isRandom());
			d.setRandom(false);
			assertFalse(d.isRandom());

			ipRule.setTarget(d);
			ipt.appendEntry("TESTTEST", ipRule);
			ipt.commit();
			ipt.free();

			ipt = new IpTables("nat");
			assertTrue(ipt.isChain("TESTTEST"));

			IpRule readRule = ipt.getAllRules("TESTTEST").get(0);

			DnatTarget d1 = (DnatTarget) readRule.getTarget();
			assertEquals(33, d1.getMinPort());
			assertEquals(44, d1.getMaxPort());
			assertEquals("192.168.11.34", d1.getMinIp().getHostAddress());
			assertEquals("192.168.11.38", d1.getMaxIp().getHostAddress());

			ipt.flushEntries("TESTTEST");
			ipt.deleteChain("TESTTEST");
			ipt.commit();
			ipt.free();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
			// } catch (ReadIptcRuleException e) {
			// e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		}
	}

	public void testRedirectTarget() {
		try {
			IpTables ipt = new IpTables("nat");
			ipt.createChain("TESTTEST");
			assertTrue(ipt.isChain("TESTTEST"));
			ipt.commit();
			ipt.free();

			ipt = new IpTables("nat");
			IpRule ipRule = new IpRule();

			ipRule.setProtocol(IpProto.IPPROTO_TCP);

			RedirectTarget d = new RedirectTarget();
			d.setMinPort(3434);
			d.setMaxPort(3463);
			assertFalse(d.isRandom());
			d.setRandom(true);

			ipRule.setTarget(d);

			ipt.appendEntry("TESTTEST", ipRule);
			ipt.commit();
			ipt.free();

			ipt = new IpTables("nat");
			assertTrue(ipt.isChain("TESTTEST"));

			IpRule readRule = ipt.getAllRules("TESTTEST").get(0);

			RedirectTarget d1 = (RedirectTarget) readRule.getTarget();
			assertEquals(3434, d1.getMinPort());
			assertEquals(3463, d1.getMaxPort());
			assertTrue(d.isRandom());

			ipt.flushEntries("TESTTEST");
			ipt.deleteChain("TESTTEST");
			ipt.commit();
			ipt.free();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
			// } catch (ReadIptcRuleException e) {
			// e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		}

	}

	public void testMassiveRuleCreation() {
		try {
			IpTables ipt = new IpTables("nat");
			ipt.createChain("TESTTEST");
			ipt.commit();
			ipt.free();

			for (int i = 0; i <= 800; i++) {
				System.out.println(i);
				IpTables ipt1 = new IpTables("nat");
				IpRule ipRule = new IpRule();
				ipRule.setSource(InetAddress.getByName("141.30.124.1"));
				ipt1.appendEntry("TESTTEST", ipRule);
				ipt1.commit();
				ipt1.free();
			}

			IpTables ipt1 = new IpTables("nat");
			ipt1.flushEntries("TESTTEST");
			ipt1.deleteChain("TESTTEST");
			ipt1.commit();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

	public void testListRules() {
		try {
			IpTables ipt = new IpTables("nat");
			ipt.createChain("TESTTEST");
			ipt.commit();
			ipt.free();

			for (int i = 1; i <= 10; i++) {
				System.out.println(i);
				IpTables ipt1 = new IpTables("nat");
				IpRule ipRule = new IpRule();
				ipRule.setSource(InetAddress.getByName("141.30.124." + i));
				ipt1.appendEntry("TESTTEST", ipRule);
				ipt1.commit();
				ipt1.free();
			}

			IpTables ipt1 = new IpTables("nat");
			List<IpRule> rules = ipt1.getAllRules("TESTTEST");
			assertEquals(10, rules.size());
			assertEquals("141.30.124.7", rules.get(6).getSource()
					.getHostAddress());
			assertEquals("141.30.124.9", rules.get(8).getSource()
					.getHostAddress());

			ipt1.flushEntries("TESTTEST");
			ipt1.deleteChain("TESTTEST");
			ipt1.commit();

		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		} catch (GeneralIptcException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		}
	}

	public void notestIptcCreateAndDeleteRule() {
		try {
			// filterIpTables.createChain("RULETESTJUNIT");
			IpTables filterIpTables = new IpTables("filter");

			IpRule rule = null;
			rule = filterIpTables.getAllRules("INPUT").get(0);

			System.out.println("Bytes: " + rule.getBcnt());
			assertNotSame("Counter sollten größer als 0 sein", 0,
					rule.getBcnt());
			assertEquals("Protokoll Check", IpProto.IPPROTO_TCP,
					rule.getProtocol());
			ModTcp module0 = (ModTcp) rule.getModules().get(0);
			assertEquals(80, module0.getDestinationPortStart());
			filterIpTables.free();
		} catch (ReadIptcRuleException e) {
			e.printStackTrace();
		} catch (UnsupportedIptcModuleException e) {
			e.printStackTrace();
		} catch (NoSuchIptcTableException e) {
			e.printStackTrace();
		}
	}

	public void notestIptc_append_entry() {
		IpRule ipRule = new IpRule();
		// ipRule.setDestination("192.168.99.99");
		// ipRule.setDestinationPort(99);
		// ipRule.setJump("ACCEPT");
		// ipRule.setProtocol(IpProto.IPPROsTO_TCP);
		try {
			ipRule.setSource(InetAddress.getByName("192.168.88.88"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// ipRule.setSourcePort(88);
		// assertTrue("Neue Rule anlegen in Input",
		// filterIpTables.appendEntry("INPUT", ipRule));

		// System.out.println(filterIpTables.strError(filterIpTables.commit()));
	}

}
