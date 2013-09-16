package com.synflow.crypto;


@SuppressWarnings("all")
public class SHAFunctions {

	// constants

	// functions
	public static int Ch(int x, int y, int z) {
		return (x & y) ^ (~x & z);
	}
	public static int Maj(int x, int y, int z) {
		return ((x & y) ^ (x & z)) ^ (y & z);
	}
	public static int lcSigma0(int x) {
		return ((int) ((((((long) (x >>> 0x7)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x19))) ^ (((long) ((((long) (x >>> 0x12)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xe)))) & 0x3fffffffffffL)) ^ (((long) (x >>> 0x3)) & 0xffffffffL)) & 0xffffffff);
	}
	public static int lcSigma1(int x) {
		return ((int) ((((((long) (x >>> 0x11)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xf))) ^ (((long) ((((long) (x >>> 0x13)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xd)))) & 0x1fffffffffffL)) ^ (((long) (x >>> 0xa)) & 0xffffffffL)) & 0xffffffff);
	}
	public static int ucSigma0(int x) {
		return ((int) ((((((long) (x >>> 0x2)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x1e))) ^ (((long) ((((long) (x >>> 0xd)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x13)))) & 0x7ffffffffffffL)) ^ (((long) ((((long) (x >>> 0x16)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xa)))) & 0x3ffffffffffL)) & 0xffffffff);
	}
	public static int ucSigma1(int x) {
		return ((int) ((((((long) (x >>> 0x6)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x1a))) ^ (((long) ((((long) (x >>> 0xb)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x15)))) & 0x1fffffffffffffL)) ^ (((long) ((((long) (x >>> 0x19)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x7)))) & 0x7fffffffffL)) & 0xffffffff);
	}

}
