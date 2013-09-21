package com.synflow.sha256.step2;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import static com.synflow.sha256.SHACommon.*;

@SuppressWarnings("all")
final public class ComputeW implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(ComputeW.class, args).run();
	}

	private final String _name;

	// constants

	// state variables
	private int words[] = new int[16];
	private int t;


	// ports
	private Port start;
	private Port msg;
	private final Port W;

	/**
	 * constructor
	 */
	public ComputeW(String name, int flags) {
		this._name = name;
		start = new Port(this, "start", 1, flags);
		msg = new Port(this, "msg", 32, flags);
		W = new Port(this, "W", 32, Port.SYNC | flags);
	}

	@Override
	public void commit() {
		W.commit();
	}

	@Override
	public void connect(Port... ports) {
		this.start = ports[0];
		this.msg = ports[1];

		start.connect();
		msg.connect();
	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		boolean start_in;
		int msg_in;
		int W_out;

		if (start.available()) {
			boolean local_start = false;
			start_in = start.peekBoolean(); // (line 19)
			local_start = start_in; // (line 19)
			isSchedulable = !local_start;
		}
		if (isSchedulable) {
			// action ComputeW_0_a (line 19)
			start_in = start.readBoolean(); // (line 19)
		
			return;
		}
		if (msg.available() && start.available()) {
			boolean local_start = false;
			msg_in = msg.peekInt(); // (line 20)
			start_in = start.peekBoolean(); // (line 20)
			local_start = start_in; // (line 19)
			isSchedulable = !!local_start;
		}
		if (isSchedulable) {
			// action ComputeW_0_b (line 20)
			int m = 0;
			int local_msg = 0;
			int temp = 0;
			int local_t = 0;
			long tmp_if = 0;
			int local_words = 0;
			int call_lcSigma1 = 0;
			int local_words0 = 0;
			int local_words1 = 0;
			int call_lcSigma0 = 0;
			int local_words2 = 0;
			int local_t0 = 0;
			int local_t1 = 0;
			int i = 0;
			int local_words3 = 0;
			msg_in = msg.readInt(); // (line 20)
			start_in = start.readBoolean(); // (line 20)
			local_msg = msg_in; // (line 21)
			m = local_msg; // (line 0)
			local_t = t; // (line 22)
			if (local_t < 0x10) {
				tmp_if = (((long) (m)) & 0xffffffffL); // (line 0)
			} else {
				local_words = words[0x1]; // (line 22)
				call_lcSigma1 = lcSigma1(local_words); // (line 22)
				local_words0 = words[0x6]; // (line 22)
				local_words1 = words[0xe]; // (line 22)
				call_lcSigma0 = lcSigma0(local_words1); // (line 22)
				local_words2 = words[0xf]; // (line 22)
				tmp_if = (((long) ((((long) ((((long) (call_lcSigma1)) & 0xffffffffL) + (((long) (local_words0)) & 0xffffffffL))) & 0x1ffffffffL) + (((long) (call_lcSigma0)) & 0xffffffffL))) & 0x3ffffffffL) + (((long) (local_words2)) & 0xffffffffL); // (line 0)
			}
			temp = ((int) (tmp_if) & 0xffffffff); // (line 0)
			local_t0 = t; // (line 23)
			System.out.println(this + ": " + "W[" + "0x" + Integer.toHexString(local_t0) + "] = " + "0x" + Integer.toHexString(temp)); // (line 23)
			W_out = temp; // (line 24)
			local_t1 = t; // (line 0)
			t = ((((local_t1) & 0x3f) + 0x1) & 0x3f); // (line 25)
			i = 0x0; // (line 0)
			while (i < 0xf) {
				local_words3 = words[((0xe - ((i) & 0x1f)) & 0xf)]; // (line 29)
				words[((0xf - ((i) & 0x1f)) & 0xf)] = local_words3; // (line 29)
				i = ((((i) & 0x1f) + 0x1) & 0x1f); // (line 0)
			}
			words[0x0] = temp; // (line 31)
			W.write(W_out);
		
			return;
		}
	}


	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { start, msg };
	}

	public Port getStart() {
		return start;
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
		return new Port[] { W };
	}

	public Port getW() {
		return W;
	}

	@Override
	public String toString() {
		return _name;
	}

}
