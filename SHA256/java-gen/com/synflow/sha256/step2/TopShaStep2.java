package com.synflow.sha256.step2;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.sha256.step2.CounterT;
import com.synflow.sha256.step1.RomK;
import com.synflow.sha256.step2.ComputeW;
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
	private final RomK romK;
	private final ComputeW computeW;
	private final SHA256_step2 sHA256_step2;

	/**
	 * constructor
	 */
	public TopShaStep2(String name, int flags) {
		this._name = name;

		// create input ports
		msg = new Port(this, "msg", 32, Port.SYNC | flags);

		// create instances
		counterT = new CounterT("counterT", flags);
		romK = new RomK("romK", flags);
		computeW = new ComputeW("computeW", flags);
		sHA256_step2 = new SHA256_step2("sHA256_step2", flags);
	}

	@Override
	public void commit() {
		counterT.commit();
		romK.commit();
		computeW.commit();
		sHA256_step2.commit();
		
	}

	@Override
	public void connect(Port... ports) {
		this.msg = ports[0];

		counterT.connect(msg);
		romK.connect(counterT.getT_o());
		computeW.connect(counterT.getStart_o(), counterT.getMsg_o());
		sHA256_step2.connect(computeW.getW(), romK.getDout());
	}

	@Override
	public void execute() {
		counterT.execute();
		romK.execute();
		computeW.execute();
		sHA256_step2.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { counterT, romK, computeW, sHA256_step2 };
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
