package com.zqb.datastruct.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 子网掩码
 * @author zhengqb
 *        created by 2014年6月6日
 */
public class SubnetMark {

	public static void main(String[] args) throws UnknownHostException {
		
		//System.out.println("10110111111110001011000010110000".length());
		
		System.out.println(Integer.valueOf("01000000",2));
		
		System.out.println(Integer.toBinaryString(111));
		
		//-1208438608
		//-1208438624
		
		//-1208438611
		//-1208438612
		
		/*int ip = getIpInt("183.248.176.175");
		System.out.println(ip);
		System.out.println(Integer.toBinaryString(ip));
		
		int mask = 0xffffffff << 2;
		System.out.println(mask);
		
		System.out.println((ip & mask));*/
		
		
		System.out.println(isIpInWhitelist("121.14.162.6", "121.14.162.0/27"));
		
		
	}
	
	private static int getIpInt(String hostIp) throws UnknownHostException {
		byte[] address = InetAddress.getByName(hostIp).getAddress();
		
		for(int i=0; i<address.length; i++) {
			System.out.print(address[i]);
		}
		System.out.println();
		
		int ipI = 0;
		ipI |= ((address[0] << 24) & 0xff000000);
		ipI |= ((address[1] << 16) & 0xff0000);
		ipI |= ((address[2] << 8) & 0xff00);
		ipI |= (address[3] & 0xff);
		return ipI;
	}
	
	
	public static boolean isIpInWhitelist(String ip, String whitelist) {
		boolean result = false;
		whitelist = whitelist.trim();
		String[] whiteListConfig = whitelist.split("/");
		if (whiteListConfig.length == 1) {
			if (whiteListConfig[0].equals(ip)) {
				result = true;
			}
		} else if (whiteListConfig.length == 2) {
			try {
				int whiteListIp = getIpInt(whiteListConfig[0]);
				int requestIp = getIpInt(ip);
				int mask = 0xffffffff << (32 - Integer
						.valueOf(whiteListConfig[1]));
				if ((requestIp & mask) == (whiteListIp & mask)) {
					result = true;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
