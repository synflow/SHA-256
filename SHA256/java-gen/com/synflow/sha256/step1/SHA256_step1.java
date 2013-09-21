package com.synflow.sha256.step1;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import static com.synflow.sha256.SHACommon.*;

@SuppressWarnings("all")
final public class SHA256_step1 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(SHA256_step1.class, args).run();
	}

	private final String _name;

	// constants

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
	private Port Kin;
	private final Port hash;
	private final Port Kaddr;

	/**
	 * constructor
	 */
	public SHA256_step1(String name, int flags) {
		this._name = name;
		msg = new Port(this, "msg", 32, Port.SYNC | flags);
		Kin = new Port(this, "Kin", 32, flags);
		hash = new Port(this, "hash", 256, Port.SYNC | flags);
		Kaddr = new Port(this, "Kaddr", 6, flags);
	}

	@Override
	public void commit() {
		hash.commit();
		Kaddr.commit();
	}

	@Override
	public void connect(Port... ports) {
		this.msg = ports[0];
		this.Kin = ports[1];

		msg.connect();
		Kin.connect();
	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		int msg_in;
		int Kin_in;
		BigInteger hash_out;
		int Kaddr_out;

		switch (_state) {
		case 0:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_step1_0 (line 23)
				t = 0x0; // (line 23)
			
				_state = 1;
				return;
			}
			break;
		case 1:
			if (msg.available()) {
				int local_t = 0;
				msg_in = msg.peekInt(); // (line 24)
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x10;
			}
			if (isSchedulable) {
				// action SHA256_step1_1_a (line 24)
				int local_msg = 0;
				int local_t = 0;
				int local_t0 = 0;
				int local_W = 0;
				int local_t1 = 0;
				int local_t2 = 0;
				msg_in = msg.readInt(); // (line 24)
				local_msg = msg_in; // (line 24)
				local_t = t; // (line 24)
				W[((local_t) & 0x3f)] = local_msg; // (line 24)
				local_t0 = t; // (line 25)
				local_t1 = t; // (line 25)
				local_W = W[((local_t1) & 0x3f)]; // (line 25)
				System.out.println(this + ": " + "W[" + "0x" + Integer.toHexString(local_t0) + "] = " + "0x" + Integer.toHexString(local_W)); // (line 25)
				local_t2 = t; // (line 0)
				t = ((((local_t2) & 0x7f) + 0x1) & 0x7f); // (line 23)
			
				_state = 1;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x10);
			}
			if (isSchedulable) {
				// action SHA256_step1_1_b (line 27)
			
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
				// action SHA256_step1_2_a (line 29)
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
				local_t = t; // (line 29)
				local_W = W[((((local_t) & 0x7f) - 0x2) & 0x3f)]; // (line 29)
				call_lcSigma1 = lcSigma1(local_W); // (line 29)
				local_t0 = t; // (line 29)
				local_W0 = W[((((local_t0) & 0x7f) - 0x7) & 0x3f)]; // (line 29)
				local_t1 = t; // (line 29)
				local_W1 = W[((((local_t1) & 0x7f) - 0xf) & 0x3f)]; // (line 29)
				call_lcSigma0 = lcSigma0(local_W1); // (line 29)
				local_t2 = t; // (line 29)
				local_W2 = W[((((local_t2) & 0x7f) - 0x10) & 0x3f)]; // (line 29)
				local_t3 = t; // (line 29)
				W[((local_t3) & 0x3f)] = ((int) ((((long) ((((long) ((((long) (call_lcSigma1)) & 0xffffffffL) + (((long) (local_W0)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_lcSigma0)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_W2)) & 0xffffffffL)) & 0xffffffff); // (line 29)
				local_t4 = t; // (line 0)
				t = ((((local_t4) & 0x7f) + 0x1) & 0x7f); // (line 28)
			
				_state = 2;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_step1_2_b (line 31)
				H_i[0x0] = 0x6a09e667; // (line 32)
				H_i[0x1] = 0xbb67ae85; // (line 33)
				H_i[0x2] = 0x3c6ef372; // (line 34)
				H_i[0x3] = 0xa54ff53a; // (line 35)
				H_i[0x4] = 0x510e527f; // (line 36)
				H_i[0x5] = 0x9b05688c; // (line 37)
				H_i[0x6] = 0x1f83d9ab; // (line 38)
				H_i[0x7] = 0x5be0cd19; // (line 39)
				Kaddr_out = 0x0; // (line 40)
				Kaddr.write(Kaddr_out);
			
				_state = 3;
				return;
			}
			break;
		case 3:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action SHA256_step1_3 (line 44)
				int local_H_i = 0;
				int local_H_i0 = 0;
				int local_H_i1 = 0;
				int local_H_i2 = 0;
				int local_H_i3 = 0;
				int local_H_i4 = 0;
				int local_H_i5 = 0;
				int local_H_i6 = 0;
				local_H_i = H_i[0x0]; // (line 44)
				a = local_H_i; // (line 44)
				local_H_i0 = H_i[0x1]; // (line 45)
				b = local_H_i0; // (line 45)
				local_H_i1 = H_i[0x2]; // (line 46)
				c = local_H_i1; // (line 46)
				local_H_i2 = H_i[0x3]; // (line 47)
				d = local_H_i2; // (line 47)
				local_H_i3 = H_i[0x4]; // (line 48)
				e = local_H_i3; // (line 48)
				local_H_i4 = H_i[0x5]; // (line 49)
				f = local_H_i4; // (line 49)
				local_H_i5 = H_i[0x6]; // (line 50)
				g = local_H_i5; // (line 50)
				local_H_i6 = H_i[0x7]; // (line 51)
				h = local_H_i6; // (line 51)
				Kaddr_out = 0x1; // (line 52)
				t = 0x0; // (line 54)
				Kaddr.write(Kaddr_out);
			
				_state = 4;
				return;
			}
			break;
		case 4:
			if (Kin.available()) {
				int local_t = 0;
				Kin_in = Kin.peekInt(); // (line 55)
				local_t = t; // (line 0)
				isSchedulable = local_t < 0x40;
			}
			if (isSchedulable) {
				// action SHA256_step1_4_a (line 55)
				int T1 = 0;
				int local_h = 0;
				int local_e = 0;
				int call_ucSigma1 = 0;
				int local_e0 = 0;
				int local_f = 0;
				int local_g = 0;
				int call_Ch = 0;
				int local_Kin = 0;
				int local_W = 0;
				int local_t = 0;
				int T2 = 0;
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
				int local_t1 = 0;
				Kin_in = Kin.readInt(); // (line 55)
				local_h = h; // (line 55)
				local_e = e; // (line 55)
				call_ucSigma1 = ucSigma1(local_e); // (line 55)
				local_e0 = e; // (line 55)
				local_f = f; // (line 55)
				local_g = g; // (line 55)
				call_Ch = Ch(local_e0, local_f, local_g); // (line 55)
				local_Kin = Kin_in; // (line 55)
				local_t = t; // (line 55)
				local_W = W[((local_t) & 0x3f)]; // (line 55)
				T1 = ((int) ((((long) ((((long) ((((long) ((((long) (local_h)) & 0xffffffffL) + (((long) (call_ucSigma1)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_Ch)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_Kin)) & 0xffffffffL))) & 0x7ffffffffL) + (((long) (local_W)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_a = a; // (line 56)
				call_ucSigma0 = ucSigma0(local_a); // (line 56)
				local_a0 = a; // (line 56)
				local_b = b; // (line 56)
				local_c = c; // (line 56)
				call_Maj = Maj(local_a0, local_b, local_c); // (line 56)
				T2 = ((int) ((((long) (call_ucSigma0)) & 0xffffffffL) + (((long) (call_Maj)) & 0xffffffffL)) & 0xffffffff); // (line 0)
				local_g0 = g; // (line 57)
				h = local_g0; // (line 57)
				local_f0 = f; // (line 58)
				g = local_f0; // (line 58)
				local_e1 = e; // (line 59)
				f = local_e1; // (line 59)
				local_d = d; // (line 60)
				e = ((int) ((((long) (local_d)) & 0xffffffffL) + (((long) (T1)) & 0xffffffffL)) & 0xffffffff); // (line 60)
				local_c0 = c; // (line 61)
				d = local_c0; // (line 61)
				local_b0 = b; // (line 62)
				c = local_b0; // (line 62)
				local_a1 = a; // (line 63)
				b = local_a1; // (line 63)
				a = ((int) ((((long) (T1)) & 0xffffffffL) + (((long) (T2)) & 0xffffffffL)) & 0xffffffff); // (line 64)
				local_t0 = t; // (line 65)
				Kaddr_out = ((((local_t0) & 0x7f) + 0x2) & 0x3f); // (line 65)
				local_t1 = t; // (line 0)
				t = ((((local_t1) & 0x7f) + 0x1) & 0x7f); // (line 54)
				Kaddr.write(Kaddr_out);
			
				_state = 4;
				return;
			}
			if (true) {
				int local_t = 0;
				local_t = t; // (line 0)
				isSchedulable = !(local_t < 0x40);
			}
			if (isSchedulable) {
				// action SHA256_step1_4_b (line 67)
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
				H_i[0x0] = ((int) ((((long) (local_H_i)) & 0xffffffffL) + (((long) (local_a)) & 0xffffffffL)) & 0xffffffff); // (line 68)
				local_H_i0 = H_i[0x1]; // (line 0)
				local_b = b; // (line 0)
				H_i[0x1] = ((int) ((((long) (local_H_i0)) & 0xffffffffL) + (((long) (local_b)) & 0xffffffffL)) & 0xffffffff); // (line 69)
				local_H_i1 = H_i[0x2]; // (line 0)
				local_c = c; // (line 0)
				H_i[0x2] = ((int) ((((long) (local_H_i1)) & 0xffffffffL) + (((long) (local_c)) & 0xffffffffL)) & 0xffffffff); // (line 70)
				local_H_i2 = H_i[0x3]; // (line 0)
				local_d = d; // (line 0)
				H_i[0x3] = ((int) ((((long) (local_H_i2)) & 0xffffffffL) + (((long) (local_d)) & 0xffffffffL)) & 0xffffffff); // (line 71)
				local_H_i3 = H_i[0x4]; // (line 0)
				local_e = e; // (line 0)
				H_i[0x4] = ((int) ((((long) (local_H_i3)) & 0xffffffffL) + (((long) (local_e)) & 0xffffffffL)) & 0xffffffff); // (line 72)
				local_H_i4 = H_i[0x5]; // (line 0)
				local_f = f; // (line 0)
				H_i[0x5] = ((int) ((((long) (local_H_i4)) & 0xffffffffL) + (((long) (local_f)) & 0xffffffffL)) & 0xffffffff); // (line 73)
				local_H_i5 = H_i[0x6]; // (line 0)
				local_g = g; // (line 0)
				H_i[0x6] = ((int) ((((long) (local_H_i5)) & 0xffffffffL) + (((long) (local_g)) & 0xffffffffL)) & 0xffffffff); // (line 74)
				local_H_i6 = H_i[0x7]; // (line 0)
				local_h = h; // (line 0)
				H_i[0x7] = ((int) ((((long) (local_H_i6)) & 0xffffffffL) + (((long) (local_h)) & 0xffffffffL)) & 0xffffffff); // (line 75)
				local_H_i7 = H_i[0x0]; // (line 77)
				System.out.println(this + ": " + "H_i[0] = " + "0x" + Integer.toHexString(local_H_i7)); // (line 77)
				local_H_i8 = H_i[0x1]; // (line 78)
				System.out.println(this + ": " + "H_i[1] = " + "0x" + Integer.toHexString(local_H_i8)); // (line 78)
				local_H_i9 = H_i[0x2]; // (line 79)
				System.out.println(this + ": " + "H_i[2] = " + "0x" + Integer.toHexString(local_H_i9)); // (line 79)
				local_H_i10 = H_i[0x3]; // (line 80)
				System.out.println(this + ": " + "H_i[3] = " + "0x" + Integer.toHexString(local_H_i10)); // (line 80)
				local_H_i11 = H_i[0x4]; // (line 81)
				System.out.println(this + ": " + "H_i[4] = " + "0x" + Integer.toHexString(local_H_i11)); // (line 81)
				local_H_i12 = H_i[0x5]; // (line 82)
				System.out.println(this + ": " + "H_i[5] = " + "0x" + Integer.toHexString(local_H_i12)); // (line 82)
				local_H_i13 = H_i[0x6]; // (line 83)
				System.out.println(this + ": " + "H_i[6] = " + "0x" + Integer.toHexString(local_H_i13)); // (line 83)
				local_H_i14 = H_i[0x7]; // (line 84)
				System.out.println(this + ": " + "H_i[7] = " + "0x" + Integer.toHexString(local_H_i14)); // (line 84)
				local_H_i15 = H_i[0x0]; // (line 87)
				local_H_i16 = H_i[0x1]; // (line 88)
				local_H_i17 = H_i[0x2]; // (line 89)
				local_H_i18 = H_i[0x3]; // (line 90)
				local_H_i19 = H_i[0x4]; // (line 91)
				local_H_i20 = H_i[0x5]; // (line 92)
				local_H_i21 = H_i[0x6]; // (line 93)
				local_H_i22 = H_i[0x7]; // (line 94)
				hash_out = (BigInteger.valueOf(local_H_i15).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xe0)).or(BigInteger.valueOf(local_H_i16).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xc0)).or(BigInteger.valueOf(local_H_i17).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0xa0)).or(BigInteger.valueOf(local_H_i18).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x80)).or(BigInteger.valueOf(local_H_i19).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x60)).or(BigInteger.valueOf(local_H_i20).and(new BigInteger(new byte[] {0, -1, -1, -1, -1})).shiftLeft(0x40)).or(BigInteger.valueOf(((((long) (local_H_i21)) & 0xffffffffL) << 0x20)).and(new BigInteger(new byte[] {0, -1, -1, -1, -1, -1, -1, -1, -1}))).or(BigInteger.valueOf(local_H_i22).and(new BigInteger(new byte[] {0, -1, -1, -1, -1}))); // (line 86)
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
		return new Port[] { msg, Kin };
	}

	public Port getMsg() {
		return msg;
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
		return new Port[] { hash, Kaddr };
	}

	public Port getHash() {
		return hash;
	}
	public Port getKaddr() {
		return Kaddr;
	}

	@Override
	public String toString() {
		return _name;
	}

}
