package com.synflow.sha256.step2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;

@SuppressWarnings("all")
final public class CounterT implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(CounterT.class, args).run();
	}


	private final String _name;

	// fields
	private int t;


	// ports
	private Port msg_i;
	private final Port start_o;
	private final Port msg_o;
	private final Port t_o;

	/**
	 * constructor
	 */
	public CounterT(String name, int flags) {
		this._name = name;
		msg_i = new Port(this, "msg_i", 32, Port.SYNC | flags);
		start_o = new Port(this, "start_o", 1, flags);
		msg_o = new Port(this, "msg_o", 32, flags);
		t_o = new Port(this, "t_o", 6, flags);
	}

	@Override
	public void commit() {
		start_o.commit();
		msg_o.commit();
		t_o.commit();
	
	}

	@Override
	public void connect(Port... ports) {
		this.msg_i = ports[0];

		msg_i.connect();
	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		int msg_i_in;
		boolean start_o_out;
		int msg_o_out;
		int t_o_out;
	
		if (msg_i.available()) {
			int local_t = 0;
			msg_i_in = msg_i.peekInt(); // (line 18)
			local_t = t; // (line 0)
			isSchedulable = local_t <= 0x10;
		}
		if (isSchedulable) {
			// action CounterT_0_a (line 18)
			int local_msg_i = 0;
			int local_t = 0;
			int local_t0 = 0;
			msg_i_in = msg_i.readInt(); // (line 18)
			local_msg_i = msg_i_in; // (line 19)
			msg_o_out = local_msg_i; // (line 19)
			start_o_out = true; // (line 20)
			local_t = t; // (line 21)
			t_o_out = ((((local_t) & 0x7f) - 0x1) & 0x3f); // (line 21)
			local_t0 = t; // (line 0)
			t = ((((local_t0) & 0x7f) + 0x1) & 0x7f); // (line 22)
			msg_o.write(msg_o_out);
			start_o.write(start_o_out);
			t_o.write(t_o_out);
		
			return;
		}
		if (true) {
			int local_t = 0;
			local_t = t; // (line 0)
			isSchedulable = local_t <= 0x40;
		}
		if (isSchedulable) {
			// action CounterT_0_b (line 23)
			int local_t = 0;
			int local_t0 = 0;
			local_t = t; // (line 24)
			t_o_out = ((((local_t) & 0x7f) - 0x1) & 0x3f); // (line 24)
			local_t0 = t; // (line 0)
			t = ((((local_t0) & 0x7f) + 0x1) & 0x7f); // (line 25)
			t_o.write(t_o_out);
		
			return;
		}
		if (true) {
			isSchedulable = true;
		}
		if (isSchedulable) {
			// action CounterT_0_c (line 0)
			t = 0x0; // (line 27)
			start_o_out = false; // (line 28)
			start_o.write(start_o_out);
		
			return;
		}
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { msg_i };
	}

	public Port getMsg_i() {
		return msg_i;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { start_o, msg_o, t_o };
	}

	public Port getStart_o() {
		return start_o;
	}
	public Port getMsg_o() {
		return msg_o;
	}
	public Port getT_o() {
		return t_o;
	}

	@Override
	public String toString() {
		return _name;
	}

}
