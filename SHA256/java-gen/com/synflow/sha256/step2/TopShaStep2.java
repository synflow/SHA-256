package com.synflow.sha256.step2;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.sha256.step2.CounterT;
import com.synflow.sha256.step2.ComputeW;
import com.synflow.mem.ROM;
import com.synflow.sha256.step2.SHA256_step2;

@SuppressWarnings("all")
final public class TopShaStep2 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(TopShaStep2.class, args).run();
	}

	private final String _name;

	// ports
	private Port msg;

	private final CounterT counterT;
	private final ComputeW computeW;
	private final ROM rom;
	private final SHA256_step2 sHA256_step2;

	/**
	 * constructor
	 */
	public TopShaStep2(String name, int _flags) {
		this._name = name;

		// create input ports
		msg = new Port(this, "msg", 32, Port.SYNC | _flags);

		// create instances
		counterT = new CounterT("counterT", _flags);
		computeW = new ComputeW("computeW", _flags);
		rom = new ROM("rom", _flags, 0x20, new int[] {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0xfc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x6ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2});
		sHA256_step2 = new SHA256_step2("sHA256_step2", _flags);
	}

	@Override
	public void commit() {
		counterT.commit();
		computeW.commit();
		rom.commit();
		sHA256_step2.commit();
		
	}

	@Override
	public void connect(Port... ports) {
		this.msg = ports[0];

		counterT.connect(msg);
		computeW.connect(counterT.getStart_o(), counterT.getMsg_o());
		rom.connect(counterT.getT_o());
		sHA256_step2.connect(computeW.getW(), rom.getQ());
	}

	@Override
	public void execute() {
		counterT.execute();
		computeW.execute();
		rom.execute();
		sHA256_step2.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { counterT, computeW, rom, sHA256_step2 };
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { msg };
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { sHA256_step2.getHash() };
	}

	public Port getHash() {
		return sHA256_step2.getHash();
	}

	@Override
	public String toString() {
		return _name;
	}

}
