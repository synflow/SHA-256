package com.synflow.sha256;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;

@SuppressWarnings("all")
final public class Source implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(Source.class, args).run();
	}

	private final String _name;

	// fields
	private int Source_0_i_l;

	private int _state;

	// ports
	private final Port stimulus;

	/**
	 * constructor
	 */
	public Source(String name, int _flags) {
		this._name = name;
		stimulus = new Port(this, "stimulus", 32, Port.SYNC | _flags);
	}


	@Override
	public void commit() {
		stimulus.commit();
	
	}

	@Override
	public void connect(Port... ports) {

	}

	@Override
	public void execute() {
		boolean isSchedulable = false;
		int stimulus_out;
	
		switch (_state) {
		case 0:
			if (true) {
				isSchedulable = true;
			}
			if (isSchedulable) {
				// action Source_0 (line 12)
				stimulus_out = 0x61626380; // (line 12)
				Source_0_i_l = 0x0; // (line 14)
				stimulus.write(stimulus_out);
			
				_state = 1;
				return;
			}
			break;
		case 1:
			if (true) {
				int local_i_l = 0;
				local_i_l = Source_0_i_l; // (line 0)
				isSchedulable = local_i_l < 0xe;
			}
			if (isSchedulable) {
				// action Source_1_a (line 15)
				int local_i_l = 0;
				stimulus_out = 0x0; // (line 15)
				local_i_l = Source_0_i_l; // (line 0)
				Source_0_i_l = ((((local_i_l) & 0x3f) + 0x1) & 0x3f); // (line 14)
				stimulus.write(stimulus_out);
			
				_state = 1;
				return;
			}
			if (true) {
				int local_i_l = 0;
				local_i_l = Source_0_i_l; // (line 0)
				isSchedulable = !(local_i_l < 0xe);
			}
			if (isSchedulable) {
				// action Source_1_b (line 17)
				stimulus_out = 0x18; // (line 18)
				stimulus.write(stimulus_out);
			
				_state = 2;
				return;
			}
			break;
		case 2:
			break;
		}
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	@Override
	public Port[] getInputs() {
		return new Port[] {  };
	}


	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { stimulus };
	}

	public Port getStimulus() {
		return stimulus;
	}

	@Override
	public String toString() {
		return _name;
	}

}
