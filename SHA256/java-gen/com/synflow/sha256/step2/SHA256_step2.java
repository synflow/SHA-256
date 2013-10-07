package com.synflow.sha256.step2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import static com.synflow.sha256.SHACommon.*;

@SuppressWarnings("all")
final public class SHA256_step2 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(SHA256_step2.class, args).run();
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

	private int _state;

	// ports
	private Port W;
	private Port Kin;
	private final Port hash;

	/**
	 * constructor
	 */
	public SHA256_step2(String name, int _flags) {
		this._name = name;
		W = new Port(this, "W", 32, Port.SYNC | _flags);
		Kin = new Port(this, "Kin", 32, _flags);
		hash = new Port(this, "hash", 256, Port.SYNC | _flags);
	}


	@Override
	public void commit() {
		hash.commit();
	
		if (!H_i_written.isEmpty()) {
			H_i_written.clear();
		}
	}

	@Override
	public void connect(Port... ports) {
		this.W = ports[0];
		this.Kin = ports[1];

		W.connect();
		Kin.connect();
	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		int W_in;
		int Kin_in;
		BigInteger hash_out;
	
		switch (_state) {
		case 0:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_step2_0 (line 21)
				H_i_written.add(0x0);
				H_i[0x0] = 0x6a09e667; // (line 21)
				H_i_written.add(0x1);
				H_i[0x1] = 0xbb67ae85; // (line 22)
				H_i_written.add(0x2);
				H_i[0x2] = 0x3c6ef372; // (line 23)
				H_i_written.add(0x3);
				H_i[0x3] = 0xa54ff53a; // (line 24)
				H_i_written.add(0x4);
				H_i[0x4] = 0x510e527f; // (line 25)
				H_i_written.add(0x5);
				H_i[0x5] = 0x9b05688c; // (line 26)
				H_i_written.add(0x6);
				H_i[0x6] = 0x1f83d9ab; // (line 27)
				H_i_written.add(0x7);
				H_i[0x7] = 0x5be0cd19; // (line 28)
			
				_state = 1;
				return;
			}
			break;
		case 1:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_step2_1 (line 32)
				int local_H_i = 0;
				int local_H_i0 = 0;
				int local_H_i1 = 0;
				int local_H_i2 = 0;
				int local_H_i3 = 0;
				int local_H_i4 = 0;
				int local_H_i5 = 0;
				int local_H_i6 = 0;
				assert !H_i_written.contains(0x0) : "trying to read H_i[0x0] before write has been committed";
				local_H_i = H_i[0x0]; // (line 32)
				a = local_H_i; // (line 32)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i0 = H_i[0x1]; // (line 33)
				b = local_H_i0; // (line 33)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i1 = H_i[0x2]; // (line 34)
				c = local_H_i1; // (line 34)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i2 = H_i[0x3]; // (line 35)
				d = local_H_i2; // (line 35)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i3 = H_i[0x4]; // (line 36)
				e = local_H_i3; // (line 36)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i4 = H_i[0x5]; // (line 37)
				f = local_H_i4; // (line 37)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i5 = H_i[0x6]; // (line 38)
				g = local_H_i5; // (line 38)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i6 = H_i[0x7]; // (line 39)
				h = local_H_i6; // (line 39)
				t = 0x0; // (line 41)
			
				_state = 2;
				return;
			}
			break;
		case 2:
			if (Kin.available() && W.available()) {
				int local_t = 0;
				Kin_in = Kin.peekInt(); // (line 42)
				W_in = W.peekInt(); // (line 42)
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x40;
			}
			if (isSchedulable) {
				// action SHA256_step2_2_a (line 42)
				int k_l = 0;
				int local_Kin = 0;
				int local_t = 0;
				int T1_l = 0;
				int local_h = 0;
				int local_e = 0;
				int call_ucSigma1 = 0;
				int local_e0 = 0;
				int local_f = 0;
				int local_g = 0;
				int call_Ch = 0;
				int local_W = 0;
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
				int local_t0 = 0;
				Kin_in = Kin.readInt(); // (line 42)
				W_in = W.readInt(); // (line 42)
				local_Kin = Kin_in; // (line 42)
				k_l = local_Kin; // (line 0)
				local_t = t; // (line 43)
				System.out.println(this + ": " + "K[" + "0x" + Integer.toHexString(local_t) + "] = " + "0x" + Integer.toHexString(k_l)); // (line 43)
				local_h = h; // (line 44)
				local_e = e; // (line 44)
				call_ucSigma1 = ucSigma1(local_e); // (line 44)
				local_e0 = e; // (line 44)
				local_f = f; // (line 44)
				local_g = g; // (line 44)
				call_Ch = Ch(local_e0, local_f, local_g); // (line 44)
				local_W = W_in; // (line 44)
				T1_l = ((int) ((((long) ((((long) ((((long) ((((long) (local_h)) & 0xffffffffL) + (((long) (call_ucSigma1)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_Ch)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (k_l)) & 0xffffffffL))) & 0x7ffffffffL) + (((long) (local_W)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_a = a; // (line 45)
				call_ucSigma0 = ucSigma0(local_a); // (line 45)
				local_a0 = a; // (line 45)
				local_b = b; // (line 45)
				local_c = c; // (line 45)
				call_Maj = Maj(local_a0, local_b, local_c); // (line 45)
				T2_l = ((int) ((((long) (call_ucSigma0)) & 0xffffffffL) + (((long) (call_Maj)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_g0 = g; // (line 46)
				h = local_g0; // (line 46)
				local_f0 = f; // (line 47)
				g = local_f0; // (line 47)
				local_e1 = e; // (line 48)
				f = local_e1; // (line 48)
				local_d = d; // (line 49)
				e = ((int) ((((long) (local_d)) & 0xffffffffL) + (((long) (T1_l)) & 0xffffffffL)) & 0xffffffff); // (line 49)
				local_c0 = c; // (line 50)
				d = local_c0; // (line 50)
				local_b0 = b; // (line 51)
				c = local_b0; // (line 51)
				local_a1 = a; // (line 52)
				b = local_a1; // (line 52)
				a = ((int) ((((long) (T1_l)) & 0xffffffffL) + (((long) (T2_l)) & 0xffffffffL)) & 0xffffffff); // (line 53)
				local_t0 = t; // (line 0)
				t = ((((local_t0) & 0x7f) + 0x1) & 0x7f); // (line 41)
			
				_state = 2;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_step2_2_b (line 55)
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
				H_i[0x0] = ((int) ((((long) (local_H_i)) & 0xffffffffL) + (((long) (local_a)) & 0xffffffffL)) & 0xffffffff); // (line 56)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i0 = H_i[0x1]; // (line 0)
				local_b = b; // (line 0)
				H_i_written.add(0x1);
				H_i[0x1] = ((int) ((((long) (local_H_i0)) & 0xffffffffL) + (((long) (local_b)) & 0xffffffffL)) & 0xffffffff); // (line 57)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i1 = H_i[0x2]; // (line 0)
				local_c = c; // (line 0)
				H_i_written.add(0x2);
				H_i[0x2] = ((int) ((((long) (local_H_i1)) & 0xffffffffL) + (((long) (local_c)) & 0xffffffffL)) & 0xffffffff); // (line 58)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i2 = H_i[0x3]; // (line 0)
				local_d = d; // (line 0)
				H_i_written.add(0x3);
				H_i[0x3] = ((int) ((((long) (local_H_i2)) & 0xffffffffL) + (((long) (local_d)) & 0xffffffffL)) & 0xffffffff); // (line 59)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i3 = H_i[0x4]; // (line 0)
				local_e = e; // (line 0)
				H_i_written.add(0x4);
				H_i[0x4] = ((int) ((((long) (local_H_i3)) & 0xffffffffL) + (((long) (local_e)) & 0xffffffffL)) & 0xffffffff); // (line 60)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i4 = H_i[0x5]; // (line 0)
				local_f = f; // (line 0)
				H_i_written.add(0x5);
				H_i[0x5] = ((int) ((((long) (local_H_i4)) & 0xffffffffL) + (((long) (local_f)) & 0xffffffffL)) & 0xffffffff); // (line 61)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i5 = H_i[0x6]; // (line 0)
				local_g = g; // (line 0)
				H_i_written.add(0x6);
				H_i[0x6] = ((int) ((((long) (local_H_i5)) & 0xffffffffL) + (((long) (local_g)) & 0xffffffffL)) & 0xffffffff); // (line 62)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i6 = H_i[0x7]; // (line 0)
				local_h = h; // (line 0)
				H_i_written.add(0x7);
				H_i[0x7] = ((int) ((((long) (local_H_i6)) & 0xffffffffL) + (((long) (local_h)) & 0xffffffffL)) & 0xffffffff); // (line 63)
			
				_state = 3;
				return;
			}
			break;
		case 3:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_step2_3 (line 67)
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
				local_H_i = H_i[0x0]; // (line 67)
				System.out.println(this + ": " + "H_i[0] = " + "0x" + Integer.toHexString(local_H_i)); // (line 67)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i0 = H_i[0x1]; // (line 68)
				System.out.println(this + ": " + "H_i[1] = " + "0x" + Integer.toHexString(local_H_i0)); // (line 68)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i1 = H_i[0x2]; // (line 69)
				System.out.println(this + ": " + "H_i[2] = " + "0x" + Integer.toHexString(local_H_i1)); // (line 69)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i2 = H_i[0x3]; // (line 70)
				System.out.println(this + ": " + "H_i[3] = " + "0x" + Integer.toHexString(local_H_i2)); // (line 70)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i3 = H_i[0x4]; // (line 71)
				System.out.println(this + ": " + "H_i[4] = " + "0x" + Integer.toHexString(local_H_i3)); // (line 71)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i4 = H_i[0x5]; // (line 72)
				System.out.println(this + ": " + "H_i[5] = " + "0x" + Integer.toHexString(local_H_i4)); // (line 72)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i5 = H_i[0x6]; // (line 73)
				System.out.println(this + ": " + "H_i[6] = " + "0x" + Integer.toHexString(local_H_i5)); // (line 73)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i6 = H_i[0x7]; // (line 74)
				System.out.println(this + ": " + "H_i[7] = " + "0x" + Integer.toHexString(local_H_i6)); // (line 74)
				assert !H_i_written.contains(0x0) : "trying to read H_i[0x0] before write has been committed";
				local_H_i7 = H_i[0x0]; // (line 77)
				assert !H_i_written.contains(0x1) : "trying to read H_i[0x1] before write has been committed";
				local_H_i8 = H_i[0x1]; // (line 78)
				assert !H_i_written.contains(0x2) : "trying to read H_i[0x2] before write has been committed";
				local_H_i9 = H_i[0x2]; // (line 79)
				assert !H_i_written.contains(0x3) : "trying to read H_i[0x3] before write has been committed";
				local_H_i10 = H_i[0x3]; // (line 80)
				assert !H_i_written.contains(0x4) : "trying to read H_i[0x4] before write has been committed";
				local_H_i11 = H_i[0x4]; // (line 81)
				assert !H_i_written.contains(0x5) : "trying to read H_i[0x5] before write has been committed";
				local_H_i12 = H_i[0x5]; // (line 82)
				assert !H_i_written.contains(0x6) : "trying to read H_i[0x6] before write has been committed";
				local_H_i13 = H_i[0x6]; // (line 83)
				assert !H_i_written.contains(0x7) : "trying to read H_i[0x7] before write has been committed";
				local_H_i14 = H_i[0x7]; // (line 84)
				hash_out = (BigInteger.valueOf(local_H_i7).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xe0)).or(BigInteger.valueOf(local_H_i8).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xc0)).or(BigInteger.valueOf(local_H_i9).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xa0)).or(BigInteger.valueOf(local_H_i10).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x80)).or(BigInteger.valueOf(local_H_i11).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x60)).or(BigInteger.valueOf(local_H_i12).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x40)).or(BigInteger.valueOf(((((long) (local_H_i13)) & 0xffffffffL) << 0x20)).and(new BigInteger(new byte[] {0, -1, -1, -1, -1, -1, -1, -1, -1}))).or(BigInteger.valueOf(local_H_i14).and(new BigInteger(new byte[] {0, -1, -1, -1, -1}))); // (line 76)
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
		return new Port[] { W, Kin };
	}

	public Port getW() {
		return W;
	}
	public Port getKin() {
		return Kin;
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
