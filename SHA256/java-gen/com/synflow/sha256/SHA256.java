package com.synflow.sha256;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import static com.synflow.sha256.SHACommon.*;

@SuppressWarnings("all")
final public class SHA256 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(SHA256.class, args).run();
	}

	private final String _name;

	// fields
	private int H_i[] = new int[8];
	private Set<Integer> H_i_written = new HashSet<>();
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
	private Set<Integer> W_written = new HashSet<>();
	private static final int K[] = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0xfc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x6ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

	private int _state;

	// ports
	private Port msg;
	private final Port hash;

	/**
	 * constructor
	 */
	public SHA256(String name, int _flags) {
		this._name = name;
		msg = new Port(this, "msg", 32, Port.SYNC | _flags);
		hash = new Port(this, "hash", 256, Port.SYNC | _flags);
	}


	@Override
	public void commit() {
		hash.commit();
	
		if (!H_i_written.isEmpty()) {
			H_i_written.clear();
		}
		if (!W_written.isEmpty()) {
			W_written.clear();
		}
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
				// action SHA256_0 (line 34)
				t = 0x0; // (line 34)
			
				_state = 1;
				return;
			}
			break;
		case 1:
			if (msg.available()) {
				int local_t = 0;
				msg_in = msg.peekInt(); // (line 35)
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x10;
			}
			if (isSchedulable) {
				// action SHA256_1_a (line 35)
				int m_l = 0;
				int local_msg = 0;
				int local_t = 0;
				int local_t0 = 0;
				int local_t1 = 0;
				msg_in = msg.readInt(); // (line 35)
				local_msg = msg_in; // (line 35)
				m_l = local_msg; // (line 0)
				local_t = t; // (line 36)
				W_written.add(((local_t) & 0x7f));
				W[((local_t) & 0x3f)] = m_l; // (line 36)
				local_t0 = t; // (line 37)
				System.out.println(this + ": " + "W[" + "0x" + Integer.toHexString(local_t0) + "] = " + "0x" + Integer.toHexString(m_l)); // (line 37)
				local_t1 = t; // (line 0)
				t = ((((local_t1) & 0x7f) + 0x1) & 0x7f); // (line 34)
			
				_state = 1;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x10);
			}
			if (isSchedulable) {
				// action SHA256_1_b (line 39)
			
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
				// action SHA256_2_a (line 41)
				int temp_l = 0;
				int local_W = 0;
				int local_t = 0;
				int call_lcSigma1 = 0;
				int local_W0 = 0;
				int local_t0 = 0;
				int local_W1 = 0;
				int local_t1 = 0;
				int call_lcSigma0 = 0;
				int local_W2 = 0;
				int local_t2 = 0;
				int local_t3 = 0;
				int local_t4 = 0;
				int local_t5 = 0;
				local_t = t; // (line 41)
				assert !W_written.contains(((((local_t) & 0x7f) - 0x2) & 0xff)) : "trying to read W[((((local_t) & 0x7f) - 0x2) & 0x3f)] before write has been committed";
				local_W = W[((((local_t) & 0x7f) - 0x2) & 0x3f)]; // (line 41)
				call_lcSigma1 = lcSigma1(local_W); // (line 41)
				local_t0 = t; // (line 41)
				assert !W_written.contains(((((local_t0) & 0x7f) - 0x7) & 0xff)) : "trying to read W[((((local_t0) & 0x7f) - 0x7) & 0x3f)] before write has been committed";
				local_W0 = W[((((local_t0) & 0x7f) - 0x7) & 0x3f)]; // (line 41)
				local_t1 = t; // (line 41)
				assert !W_written.contains(((((local_t1) & 0x7f) - 0xf) & 0xff)) : "trying to read W[((((local_t1) & 0x7f) - 0xf) & 0x3f)] before write has been committed";
				local_W1 = W[((((local_t1) & 0x7f) - 0xf) & 0x3f)]; // (line 41)
				call_lcSigma0 = lcSigma0(local_W1); // (line 41)
				local_t2 = t; // (line 41)
				assert !W_written.contains(((((local_t2) & 0x7f) - 0x10) & 0xff)) : "trying to read W[((((local_t2) & 0x7f) - 0x10) & 0x3f)] before write has been committed";
				local_W2 = W[((((local_t2) & 0x7f) - 0x10) & 0x3f)]; // (line 41)
				temp_l = ((int) ((((long) ((((long) ((((long) (call_lcSigma1)) & 0xffffffffL) + (((long) (local_W0)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_lcSigma0)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_W2)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_t3 = t; // (line 42)
				W_written.add(((local_t3) & 0x7f));
				W[((local_t3) & 0x3f)] = temp_l; // (line 42)
				local_t4 = t; // (line 43)
				System.out.println(this + ": " + "W[" + "0x" + Integer.toHexString(local_t4) + "] = " + "0x" + Integer.toHexString(temp_l)); // (line 43)
				local_t5 = t; // (line 0)
				t = ((((local_t5) & 0x7f) + 0x1) & 0x7f); // (line 40)
			
				_state = 2;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_2_b (line 45)
				H_i_written.add(0x0);
				H_i[0x0] = 0x6a09e667; // (line 46)
				H_i_written.add(0x1);
				H_i[0x1] = 0xbb67ae85; // (line 47)
				H_i_written.add(0x2);
				H_i[0x2] = 0x3c6ef372; // (line 48)
				H_i_written.add(0x3);
				H_i[0x3] = 0xa54ff53a; // (line 49)
				H_i_written.add(0x4);
				H_i[0x4] = 0x510e527f; // (line 50)
				H_i_written.add(0x5);
				H_i[0x5] = 0x9b05688c; // (line 51)
				H_i_written.add(0x6);
				H_i[0x6] = 0x1f83d9ab; // (line 52)
				H_i_written.add(0x7);
				H_i[0x7] = 0x5be0cd19; // (line 53)
			
				_state = 3;
				return;
			}
			break;
		case 3:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_3 (line 57)
				int local_H_i = 0;
				int local_H_i0 = 0;
				int local_H_i1 = 0;
				int local_H_i2 = 0;
				int local_H_i3 = 0;
				int local_H_i4 = 0;
				int local_H_i5 = 0;
				int local_H_i6 = 0;
				assert !H_i_written.contains(0x0) : "trying to read H_i[0x0] before write has been committed";
				local_H_i = H_i[0x0]; // (line 57)
				a = local_H_i; // (line 57)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i0 = H_i[0x1]; // (line 58)
				b = local_H_i0; // (line 58)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i1 = H_i[0x2]; // (line 59)
				c = local_H_i1; // (line 59)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i2 = H_i[0x3]; // (line 60)
				d = local_H_i2; // (line 60)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i3 = H_i[0x4]; // (line 61)
				e = local_H_i3; // (line 61)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i4 = H_i[0x5]; // (line 62)
				f = local_H_i4; // (line 62)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i5 = H_i[0x6]; // (line 63)
				g = local_H_i5; // (line 63)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i6 = H_i[0x7]; // (line 64)
				h = local_H_i6; // (line 64)
				t = 0x0; // (line 66)
			
				_state = 4;
				return;
			}
			break;
		case 4:
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x40;
			}
			if (isSchedulable) {
				// action SHA256_4_a (line 67)
				int T1_l = 0;
				int local_h = 0;
				int local_e = 0;
				int call_ucSigma1 = 0;
				int local_e0 = 0;
				int local_f = 0;
				int local_g = 0;
				int call_Ch = 0;
				int local_K = 0;
				int local_t = 0;
				int local_W = 0;
				int local_t0 = 0;
				int T2_l = 0;
				int local_a = 0;
				int call_ucSigma0 = 0;
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
				local_h = h; // (line 67)
				local_e = e; // (line 67)
				call_ucSigma1 = ucSigma1(local_e); // (line 67)
				local_e0 = e; // (line 67)
				local_f = f; // (line 67)
				local_g = g; // (line 67)
				call_Ch = Ch(local_e0, local_f, local_g); // (line 67)
				local_t = t; // (line 67)
				local_K = K[((local_t) & 0x3f)]; // (line 67)
				local_t0 = t; // (line 67)
				assert !W_written.contains(((local_t0) & 0x7f)) : "trying to read W[((local_t0) & 0x3f)] before write has been committed";
				local_W = W[((local_t0) & 0x3f)]; // (line 67)
				T1_l = ((int) ((((long) ((((long) ((((long) ((((long) (local_h)) & 0xffffffffL) + (((long) (call_ucSigma1)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_Ch)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_K)) & 0xffffffffL))) & 0x7ffffffffL) + (((long) (local_W)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_a = a; // (line 68)
				call_ucSigma0 = ucSigma0(local_a); // (line 68)
				local_a0 = a; // (line 68)
				local_b = b; // (line 68)
				local_c = c; // (line 68)
				call_Maj = Maj(local_a0, local_b, local_c); // (line 68)
				T2_l = ((int) ((((long) (call_ucSigma0)) & 0xffffffffL) + (((long) (call_Maj)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_g0 = g; // (line 69)
				h = local_g0; // (line 69)
				local_f0 = f; // (line 70)
				g = local_f0; // (line 70)
				local_e1 = e; // (line 71)
				f = local_e1; // (line 71)
				local_d = d; // (line 72)
				e = ((int) ((((long) (local_d)) & 0xffffffffL) + (((long) (T1_l)) & 0xffffffffL)) & 0xffffffff); // (line 72)
				local_c0 = c; // (line 73)
				d = local_c0; // (line 73)
				local_b0 = b; // (line 74)
				c = local_b0; // (line 74)
				local_a1 = a; // (line 75)
				b = local_a1; // (line 75)
				a = ((int) ((((long) (T1_l)) & 0xffffffffL) + (((long) (T2_l)) & 0xffffffffL)) & 0xffffffff); // (line 76)
				local_t1 = t; // (line 0)
				t = ((((local_t1) & 0x7f) + 0x1) & 0x7f); // (line 66)
			
				_state = 4;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_4_b (line 78)
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
				assert !H_i_written.contains(0x0) : "trying to read H_i[0x0] before write has been committed";
				local_H_i = H_i[0x0]; // (line 0)
				local_a = a; // (line 0)
				H_i_written.add(0x0);
				H_i[0x0] = ((int) ((((long) (local_H_i)) & 0xffffffffL) + (((long) (local_a)) & 0xffffffffL)) & 0xffffffff); // (line 79)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i0 = H_i[0x1]; // (line 0)
				local_b = b; // (line 0)
				H_i_written.add(0x1);
				H_i[0x1] = ((int) ((((long) (local_H_i0)) & 0xffffffffL) + (((long) (local_b)) & 0xffffffffL)) & 0xffffffff); // (line 80)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i1 = H_i[0x2]; // (line 0)
				local_c = c; // (line 0)
				H_i_written.add(0x2);
				H_i[0x2] = ((int) ((((long) (local_H_i1)) & 0xffffffffL) + (((long) (local_c)) & 0xffffffffL)) & 0xffffffff); // (line 81)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i2 = H_i[0x3]; // (line 0)
				local_d = d; // (line 0)
				H_i_written.add(0x3);
				H_i[0x3] = ((int) ((((long) (local_H_i2)) & 0xffffffffL) + (((long) (local_d)) & 0xffffffffL)) & 0xffffffff); // (line 82)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i3 = H_i[0x4]; // (line 0)
				local_e = e; // (line 0)
				H_i_written.add(0x4);
				H_i[0x4] = ((int) ((((long) (local_H_i3)) & 0xffffffffL) + (((long) (local_e)) & 0xffffffffL)) & 0xffffffff); // (line 83)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i4 = H_i[0x5]; // (line 0)
				local_f = f; // (line 0)
				H_i_written.add(0x5);
				H_i[0x5] = ((int) ((((long) (local_H_i4)) & 0xffffffffL) + (((long) (local_f)) & 0xffffffffL)) & 0xffffffff); // (line 84)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i5 = H_i[0x6]; // (line 0)
				local_g = g; // (line 0)
				H_i_written.add(0x6);
				H_i[0x6] = ((int) ((((long) (local_H_i5)) & 0xffffffffL) + (((long) (local_g)) & 0xffffffffL)) & 0xffffffff); // (line 85)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i6 = H_i[0x7]; // (line 0)
				local_h = h; // (line 0)
				H_i_written.add(0x7);
				H_i[0x7] = ((int) ((((long) (local_H_i6)) & 0xffffffffL) + (((long) (local_h)) & 0xffffffffL)) & 0xffffffff); // (line 86)
			
				_state = 5;
				return;
			}
			break;
		case 5:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_5 (line 90)
				int local_H_i = 0;
				int local_H_i0 = 0;
				int local_H_i1 = 0;
				int local_H_i2 = 0;
				int local_H_i3 = 0;
				int local_H_i4 = 0;
				int local_H_i5 = 0;
				int local_H_i6 = 0;
				int local_H_i7 = 0;
				int local_H_i8 = 0;
				int local_H_i9 = 0;
				int local_H_i10 = 0;
				int local_H_i11 = 0;
				int local_H_i12 = 0;
				int local_H_i13 = 0;
				int local_H_i14 = 0;
				assert !H_i_written.contains(0x0) : "trying to read H_i[0x0] before write has been committed";
				local_H_i = H_i[0x0]; // (line 90)
				System.out.println(this + ": " + "H_i[0] = " + "0x" + Integer.toHexString(local_H_i)); // (line 90)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i0 = H_i[0x1]; // (line 91)
				System.out.println(this + ": " + "H_i[1] = " + "0x" + Integer.toHexString(local_H_i0)); // (line 91)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i1 = H_i[0x2]; // (line 92)
				System.out.println(this + ": " + "H_i[2] = " + "0x" + Integer.toHexString(local_H_i1)); // (line 92)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i2 = H_i[0x3]; // (line 93)
				System.out.println(this + ": " + "H_i[3] = " + "0x" + Integer.toHexString(local_H_i2)); // (line 93)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i3 = H_i[0x4]; // (line 94)
				System.out.println(this + ": " + "H_i[4] = " + "0x" + Integer.toHexString(local_H_i3)); // (line 94)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i4 = H_i[0x5]; // (line 95)
				System.out.println(this + ": " + "H_i[5] = " + "0x" + Integer.toHexString(local_H_i4)); // (line 95)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i5 = H_i[0x6]; // (line 96)
				System.out.println(this + ": " + "H_i[6] = " + "0x" + Integer.toHexString(local_H_i5)); // (line 96)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i6 = H_i[0x7]; // (line 97)
				System.out.println(this + ": " + "H_i[7] = " + "0x" + Integer.toHexString(local_H_i6)); // (line 97)
				assert !H_i_written.contains(0x0) : "trying to read H_i[0x0] before write has been committed";
				local_H_i7 = H_i[0x0]; // (line 100)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i8 = H_i[0x1]; // (line 101)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i9 = H_i[0x2]; // (line 102)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i10 = H_i[0x3]; // (line 103)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i11 = H_i[0x4]; // (line 104)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i12 = H_i[0x5]; // (line 105)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i13 = H_i[0x6]; // (line 106)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i14 = H_i[0x7]; // (line 107)
				hash_out = (BigInteger.valueOf(local_H_i7).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xe0)).or(BigInteger.valueOf(local_H_i8).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xc0)).or(BigInteger.valueOf(local_H_i9).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xa0)).or(BigInteger.valueOf(local_H_i10).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x80)).or(BigInteger.valueOf(local_H_i11).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x60)).or(BigInteger.valueOf(local_H_i12).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x40)).or(BigInteger.valueOf(((((long) (local_H_i13)) & 0xffffffffL) << 0x20)).and(new BigInteger(new byte[] {0, -1, -1, -1, -1, -1, -1, -1, -1}))).or(BigInteger.valueOf(local_H_i14).and(new BigInteger(new byte[] {0, -1, -1, -1, -1}))); // (line 99)
				hash.write(hash_out);
			
				_state = 0;
				return;
			}
			break;
		}
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
