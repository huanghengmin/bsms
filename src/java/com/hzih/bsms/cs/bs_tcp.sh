#!/bin/sh
#ubuntu success 
IPTABLES=/sbin/iptables

$IPTABLES -t nat -A PREROUTING --dst $1 -p tcp --dport $2 -j DNAT --to-destination $3:$4
$IPTABLES -t nat -A POSTROUTING --dst $3 -p tcp --dport $4 -j SNAT --to-source $1
$IPTABLES -t nat -A OUTPUT --dst $1 -p tcp --dport $2 -j DNAT --to-destination $3:$4
