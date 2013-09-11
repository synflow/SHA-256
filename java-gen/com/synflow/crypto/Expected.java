package com.synflow.crypto;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;

@SuppressWarnings("all")
final public class Expected implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(Expected.class, args).run();
	}

	private final String _name;

	// constants

	// state variables

	private int _state;

	// ports
	private Port hash;

	/**
	 * constructor
	 */
	public Expected(String name, int flags) {
		this._name = name;
		hash = new Port(this, "hash", 256, Port.SYNC | flags);
	}

	@Override
	public void commit() {
	}

	@Override
	public void connect(Port... ports) {
		this.hash = ports[0];

		hash.connect();
	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		BigInteger hash_in;

		switch (_state) {
		case 0:
			if (hash.available()) {
				hash_in = hash.peekBigInteger(); // (line 12)
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action Expected_0 (line 12)
				BigInteger dut_hash = BigInteger.ZERO;
				BigInteger local_hash = BigInteger.ZERO;
				hash_in = hash.readBigInteger(); // (line 12)
				local_hash = hash_in; // (line 12)
				dut_hash = local_hash; // (line 0)
				System.out.println(this + ": " + "read hash from dut: " + "0x" + dut_hash.toString(16)); // (line 13)
				assert dut_hash.compareTo(new BigInteger(new byte[] {0, -70, 120, 22, -65, -113, 1, -49, -22, 65, 65, 64, -34, 93, -82, 34, 35, -80, 3, 97, -93, -106, 23, 122, -100, -76, 16, -1, 97, -14, 0, 21, -83})) == 0 : "dut_hash.compareTo(new BigInteger(new byte[] {0, -70, 120, 22, -65, -113, 1, -49, -22, 65, 65, 64, -34, 93, -82, 34, 35, -80, 3, 97, -93, -106, 23, 122, -100, -76, 16, -1, 97, -14, 0, 21, -83})) == 0"; // (line 14)
			
				_state = 1;
				return;
			}
			break;
		case 1:
			break;
		}
	}


	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { hash };
	}

	public Port getHash() {
		return hash;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] {  };
	}


	@Override
	public String toString() {
		return _name;
	}

}
