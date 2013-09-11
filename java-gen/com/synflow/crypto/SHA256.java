package com.synflow.crypto;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;

@SuppressWarnings("all")
final public class SHA256 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(SHA256.class, args).run();
	}

	private final String _name;

	// constants
	private static final int K[] = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0xfc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x6ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

	// state variables
	private int H_i[] = new int[8];
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private int f;
	private int g;
	private int h;
	private int t;
	private int W[] = new int[64];

	private int _state;

	// ports
	private Port msg;
	private final Port hash;

	/**
	 * constructor
	 */
	public SHA256(String name, int flags) {
		this._name = name;
		msg = new Port(this, "msg", 32, Port.SYNC | flags);
		hash = new Port(this, "hash", 256, Port.SYNC | flags);
	}

	@Override
	public void commit() {
		hash.commit();
	}

	@Override
	public void connect(Port... ports) {
		this.msg = ports[0];

		msg.connect();
	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		int msg_in;
		BigInteger hash_out;

		switch (_state) {
		case 0:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_0 (line 61)
				t = 0x0; // (line 61)
			
				_state = 1;
				return;
			}
			break;
		case 1:
			if (msg.available()) {
				int local_t = 0;
				msg_in = msg.peekInt(); // (line 62)
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x10;
			}
			if (isSchedulable) {
				// action SHA256_1_a (line 62)
				int local_msg = 0;
				int local_t = 0;
				int local_t0 = 0;
				int local_W = 0;
				int local_t1 = 0;
				int local_t2 = 0;
				msg_in = msg.readInt(); // (line 62)
				local_msg = msg_in; // (line 62)
				local_t = t; // (line 62)
				W[((local_t) & 0x3f)] = local_msg; // (line 62)
				local_t0 = t; // (line 63)
				local_t1 = t; // (line 63)
				local_W = W[((local_t1) & 0x3f)]; // (line 63)
				System.out.println(this + ": " + "W[" + "0x" + Integer.toHexString(local_t0) + "] = " + "0x" + Integer.toHexString(local_W)); // (line 63)
				local_t2 = t; // (line 0)
				t = ((((local_t2) & 0x7f) + 0x1) & 0x7f); // (line 61)
			
				_state = 1;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x10);
			}
			if (isSchedulable) {
				// action SHA256_1_b (line 65)
			
				_state = 2;
				return;
			}
			break;
		case 2:
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x40;
			}
			if (isSchedulable) {
				// action SHA256_2_a (line 67)
				int local_W = 0;
				int local_t = 0;
				int call_sigma1 = 0;
				int local_W0 = 0;
				int local_t0 = 0;
				int local_W1 = 0;
				int local_t1 = 0;
				int call_sigma0 = 0;
				int local_W2 = 0;
				int local_t2 = 0;
				int local_t3 = 0;
				int local_t4 = 0;
				local_t = t; // (line 67)
				local_W = W[((((local_t) & 0x7f) - 0x2) & 0x3f)]; // (line 67)
				call_sigma1 = sigma1(local_W); // (line 67)
				local_t0 = t; // (line 67)
				local_W0 = W[((((local_t0) & 0x7f) - 0x7) & 0x3f)]; // (line 67)
				local_t1 = t; // (line 67)
				local_W1 = W[((((local_t1) & 0x7f) - 0xf) & 0x3f)]; // (line 67)
				call_sigma0 = sigma0(local_W1); // (line 67)
				local_t2 = t; // (line 67)
				local_W2 = W[((((local_t2) & 0x7f) - 0x10) & 0x3f)]; // (line 67)
				local_t3 = t; // (line 67)
				W[((local_t3) & 0x3f)] = ((int) ((((long) ((((long) ((((long) (call_sigma1)) & 0xffffffffL) + (((long) (local_W0)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_sigma0)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_W2)) & 0xffffffffL)) & 0xffffffff); // (line 67)
				local_t4 = t; // (line 0)
				t = ((((local_t4) & 0x7f) + 0x1) & 0x7f); // (line 66)
			
				_state = 2;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_2_b (line 69)
				int local_H_i = 0;
				int local_H_i0 = 0;
				int local_H_i1 = 0;
				int local_H_i2 = 0;
				int local_H_i3 = 0;
				int local_H_i4 = 0;
				int local_H_i5 = 0;
				int local_H_i6 = 0;
				H_i[0x0] = 0x6a09e667; // (line 70)
				H_i[0x1] = 0xbb67ae85; // (line 71)
				H_i[0x2] = 0x3c6ef372; // (line 72)
				H_i[0x3] = 0xa54ff53a; // (line 73)
				H_i[0x4] = 0x510e527f; // (line 74)
				H_i[0x5] = 0x9b05688c; // (line 75)
				H_i[0x6] = 0x1f83d9ab; // (line 76)
				H_i[0x7] = 0x5be0cd19; // (line 77)
				local_H_i = H_i[0x0]; // (line 79)
				a = local_H_i; // (line 79)
				local_H_i0 = H_i[0x1]; // (line 80)
				b = local_H_i0; // (line 80)
				local_H_i1 = H_i[0x2]; // (line 81)
				c = local_H_i1; // (line 81)
				local_H_i2 = H_i[0x3]; // (line 82)
				d = local_H_i2; // (line 82)
				local_H_i3 = H_i[0x4]; // (line 83)
				e = local_H_i3; // (line 83)
				local_H_i4 = H_i[0x5]; // (line 84)
				f = local_H_i4; // (line 84)
				local_H_i5 = H_i[0x6]; // (line 85)
				g = local_H_i5; // (line 85)
				local_H_i6 = H_i[0x7]; // (line 86)
				h = local_H_i6; // (line 86)
				t = 0x0; // (line 88)
			
				_state = 3;
				return;
			}
			break;
		case 3:
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x40;
			}
			if (isSchedulable) {
				// action SHA256_3_a (line 89)
				int T1 = 0;
				int local_h = 0;
				int local_e = 0;
				int call_sigmaBig1 = 0;
				int local_e0 = 0;
				int local_f = 0;
				int local_g = 0;
				int call_Ch = 0;
				int local_K = 0;
				int local_t = 0;
				int local_W = 0;
				int local_t0 = 0;
				int T2 = 0;
				int local_a = 0;
				int call_sigmaBig0 = 0;
				int local_a0 = 0;
				int local_b = 0;
				int local_c = 0;
				int call_Maj = 0;
				int local_g0 = 0;
				int local_f0 = 0;
				int local_e1 = 0;
				int local_d = 0;
				int local_c0 = 0;
				int local_b0 = 0;
				int local_a1 = 0;
				int local_t1 = 0;
				local_h = h; // (line 89)
				local_e = e; // (line 89)
				call_sigmaBig1 = sigmaBig1(local_e); // (line 89)
				local_e0 = e; // (line 89)
				local_f = f; // (line 89)
				local_g = g; // (line 89)
				call_Ch = Ch(local_e0, local_f, local_g); // (line 89)
				local_t = t; // (line 89)
				local_K = K[((local_t) & 0x3f)]; // (line 89)
				local_t0 = t; // (line 89)
				local_W = W[((local_t0) & 0x3f)]; // (line 89)
				T1 = ((int) ((((long) ((((long) ((((long) ((((long) (local_h)) & 0xffffffffL) + (((long) (call_sigmaBig1)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_Ch)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_K)) & 0xffffffffL))) & 0x7ffffffffL) + (((long) (local_W)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_a = a; // (line 90)
				call_sigmaBig0 = sigmaBig0(local_a); // (line 90)
				local_a0 = a; // (line 90)
				local_b = b; // (line 90)
				local_c = c; // (line 90)
				call_Maj = Maj(local_a0, local_b, local_c); // (line 90)
				T2 = ((int) ((((long) (call_sigmaBig0)) & 0xffffffffL) + (((long) (call_Maj)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_g0 = g; // (line 91)
				h = local_g0; // (line 91)
				local_f0 = f; // (line 92)
				g = local_f0; // (line 92)
				local_e1 = e; // (line 93)
				f = local_e1; // (line 93)
				local_d = d; // (line 94)
				e = ((int) ((((long) (local_d)) & 0xffffffffL) + (((long) (T1)) & 0xffffffffL)) & 0xffffffff); // (line 94)
				local_c0 = c; // (line 95)
				d = local_c0; // (line 95)
				local_b0 = b; // (line 96)
				c = local_b0; // (line 96)
				local_a1 = a; // (line 97)
				b = local_a1; // (line 97)
				a = ((int) ((((long) (T1)) & 0xffffffffL) + (((long) (T2)) & 0xffffffffL)) & 0xffffffff); // (line 98)
				local_t1 = t; // (line 0)
				t = ((((local_t1) & 0x7f) + 0x1) & 0x7f); // (line 88)
			
				_state = 3;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_3_b (line 100)
				int local_H_i = 0;
				int local_a = 0;
				int local_H_i0 = 0;
				int local_b = 0;
				int local_H_i1 = 0;
				int local_c = 0;
				int local_H_i2 = 0;
				int local_d = 0;
				int local_H_i3 = 0;
				int local_e = 0;
				int local_H_i4 = 0;
				int local_f = 0;
				int local_H_i5 = 0;
				int local_g = 0;
				int local_H_i6 = 0;
				int local_h = 0;
				int local_H_i7 = 0;
				int local_H_i8 = 0;
				int local_H_i9 = 0;
				int local_H_i10 = 0;
				int local_H_i11 = 0;
				int local_H_i12 = 0;
				int local_H_i13 = 0;
				int local_H_i14 = 0;
				int local_H_i15 = 0;
				int local_H_i16 = 0;
				int local_H_i17 = 0;
				int local_H_i18 = 0;
				int local_H_i19 = 0;
				int local_H_i20 = 0;
				int local_H_i21 = 0;
				int local_H_i22 = 0;
				local_H_i = H_i[0x0]; // (line 0)
				local_a = a; // (line 0)
				H_i[0x0] = ((int) ((((long) (local_H_i)) & 0xffffffffL) + (((long) (local_a)) & 0xffffffffL)) & 0xffffffff); // (line 101)
				local_H_i0 = H_i[0x1]; // (line 0)
				local_b = b; // (line 0)
				H_i[0x1] = ((int) ((((long) (local_H_i0)) & 0xffffffffL) + (((long) (local_b)) & 0xffffffffL)) & 0xffffffff); // (line 102)
				local_H_i1 = H_i[0x2]; // (line 0)
				local_c = c; // (line 0)
				H_i[0x2] = ((int) ((((long) (local_H_i1)) & 0xffffffffL) + (((long) (local_c)) & 0xffffffffL)) & 0xffffffff); // (line 103)
				local_H_i2 = H_i[0x3]; // (line 0)
				local_d = d; // (line 0)
				H_i[0x3] = ((int) ((((long) (local_H_i2)) & 0xffffffffL) + (((long) (local_d)) & 0xffffffffL)) & 0xffffffff); // (line 104)
				local_H_i3 = H_i[0x4]; // (line 0)
				local_e = e; // (line 0)
				H_i[0x4] = ((int) ((((long) (local_H_i3)) & 0xffffffffL) + (((long) (local_e)) & 0xffffffffL)) & 0xffffffff); // (line 105)
				local_H_i4 = H_i[0x5]; // (line 0)
				local_f = f; // (line 0)
				H_i[0x5] = ((int) ((((long) (local_H_i4)) & 0xffffffffL) + (((long) (local_f)) & 0xffffffffL)) & 0xffffffff); // (line 106)
				local_H_i5 = H_i[0x6]; // (line 0)
				local_g = g; // (line 0)
				H_i[0x6] = ((int) ((((long) (local_H_i5)) & 0xffffffffL) + (((long) (local_g)) & 0xffffffffL)) & 0xffffffff); // (line 107)
				local_H_i6 = H_i[0x7]; // (line 0)
				local_h = h; // (line 0)
				H_i[0x7] = ((int) ((((long) (local_H_i6)) & 0xffffffffL) + (((long) (local_h)) & 0xffffffffL)) & 0xffffffff); // (line 108)
				local_H_i7 = H_i[0x0]; // (line 110)
				System.out.println(this + ": " + "H_i[0] = " + "0x" + Integer.toHexString(local_H_i7)); // (line 110)
				local_H_i8 = H_i[0x1]; // (line 111)
				System.out.println(this + ": " + "H_i[1] = " + "0x" + Integer.toHexString(local_H_i8)); // (line 111)
				local_H_i9 = H_i[0x2]; // (line 112)
				System.out.println(this + ": " + "H_i[2] = " + "0x" + Integer.toHexString(local_H_i9)); // (line 112)
				local_H_i10 = H_i[0x3]; // (line 113)
				System.out.println(this + ": " + "H_i[3] = " + "0x" + Integer.toHexString(local_H_i10)); // (line 113)
				local_H_i11 = H_i[0x4]; // (line 114)
				System.out.println(this + ": " + "H_i[4] = " + "0x" + Integer.toHexString(local_H_i11)); // (line 114)
				local_H_i12 = H_i[0x5]; // (line 115)
				System.out.println(this + ": " + "H_i[5] = " + "0x" + Integer.toHexString(local_H_i12)); // (line 115)
				local_H_i13 = H_i[0x6]; // (line 116)
				System.out.println(this + ": " + "H_i[6] = " + "0x" + Integer.toHexString(local_H_i13)); // (line 116)
				local_H_i14 = H_i[0x7]; // (line 117)
				System.out.println(this + ": " + "H_i[7] = " + "0x" + Integer.toHexString(local_H_i14)); // (line 117)
				local_H_i15 = H_i[0x0]; // (line 120)
				local_H_i16 = H_i[0x1]; // (line 121)
				local_H_i17 = H_i[0x2]; // (line 122)
				local_H_i18 = H_i[0x3]; // (line 123)
				local_H_i19 = H_i[0x4]; // (line 124)
				local_H_i20 = H_i[0x5]; // (line 125)
				local_H_i21 = H_i[0x6]; // (line 126)
				local_H_i22 = H_i[0x7]; // (line 127)
				hash_out = (BigInteger.valueOf(local_H_i15).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xe0)).or(BigInteger.valueOf(local_H_i16).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xc0)).or(BigInteger.valueOf(local_H_i17).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xa0)).or(BigInteger.valueOf(local_H_i18).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x80)).or(BigInteger.valueOf(local_H_i19).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x60)).or(BigInteger.valueOf(local_H_i20).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x40)).or(BigInteger.valueOf(((((long) (local_H_i21)) & 0xffffffffL) << 0x20)).and(new BigInteger(new byte[] {0, -1, -1, -1, -1, -1, -1, -1, -1}))).or(BigInteger.valueOf(local_H_i22).and(new BigInteger(new byte[] {0, -1, -1, -1, -1}))); // (line 119)
				hash.write(hash_out);
			
				_state = 0;
				return;
			}
			break;
		}
	}

	int Ch(int x, int y, int z) {
		return (x & y) ^ (~x & z);
	}
	int Maj(int x, int y, int z) {
		return ((x & y) ^ (x & z)) ^ (y & z);
	}
	int sigmaBig0(int x) {
		return ((int) ((((((long) (x >>> 0x2)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x1e))) ^ (((long) ((((long) (x >>> 0xd)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x13)))) & 0x7ffffffffffffL)) ^ (((long) ((((long) (x >>> 0x16)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xa)))) & 0x3ffffffffffL)) & 0xffffffff);
	}
	int sigmaBig1(int x) {
		return ((int) ((((((long) (x >>> 0x6)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x1a))) ^ (((long) ((((long) (x >>> 0xb)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x15)))) & 0x1fffffffffffffL)) ^ (((long) ((((long) (x >>> 0x19)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x7)))) & 0x7fffffffffL)) & 0xffffffff);
	}
	int sigma0(int x) {
		return ((int) ((((((long) (x >>> 0x7)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0x19))) ^ (((long) ((((long) (x >>> 0x12)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xe)))) & 0x3fffffffffffL)) ^ (((long) (x >>> 0x3)) & 0xffffffffL)) & 0xffffffff);
	}
	int sigma1(int x) {
		return ((int) ((((((long) (x >>> 0x11)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xf))) ^ (((long) ((((long) (x >>> 0x13)) & 0xffffffffL) | (((((long) (x)) & 0xffffffffL) << 0xd)))) & 0x1fffffffffffL)) ^ (((long) (x >>> 0xa)) & 0xffffffffL)) & 0xffffffff);
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { msg };
	}

	public Port getMsg() {
		return msg;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { hash };
	}

	public Port getHash() {
		return hash;
	}

	@Override
	public String toString() {
		return _name;
	}

}
