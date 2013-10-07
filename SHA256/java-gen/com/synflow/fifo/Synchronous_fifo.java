/*
 * Copyright (c) 2012-2013, Synflow SAS
 * All rights reserved.
 * 
 * REDISTRIBUTION of this file in source and binary forms, with or without
 * modification, is NOT permitted in any way.
 *
 * The use of this file in source and binary forms, with or without
 * modification, is permitted if you have a valid commercial license of
 * Synflow Studio.
 * If you do NOT have a valid license of Synflow Studio: you are NOT allowed
 * to use this file.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE
 */
package com.synflow.fifo;

import com.synflow.mem.AbstractRam;
import com.synflow.runtime.Port;

/**
 * This class defines a generic FIFO.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Synchronous_fifo extends AbstractRam {

	private final Port almost_full;

	private int count;

	private final Port empty;

	private final Port full;

	private int head;

	private int mask;

	private Port ready;

	private int size;

	public Synchronous_fifo(String name, int flags, int depth, int width) {
		super(name, Port.SYNC | flags, depth, width);
		this.size = 1 << depth;
		this.mask = size - 1;

		empty = new Port(this, "empty", 1, flags);
		empty.initialize(true);
		full = new Port(this, "full", 1, flags);
		almost_full = new Port(this, "almost_full", 1, flags);
	}

	@Override
	public void commit() {
		super.commit();

		empty.commit();
		full.commit();
		almost_full.commit();
	}

	@Override
	public void connect(Port... ports) {
		this.data = ports[0];
		this.ready = ports[1];
		
		data.connect();
		ready.connect();
	}

	@Override
	public void execute() {
		if (data.available()) {
			if (!isFull()) {
				writeDataToArray((head + count) & mask);
				count++;
			}
		}

		if (ready.readBoolean()) {
			if (!isEmpty()) {
				readDataFromArray(head);
				head = (head + 1) & mask;
				count--;
			}
		}

		empty.write(isEmpty());
		full.write(isFull());
		almost_full.write(isAlmostFull());
	}

	public Port getAlmost_full() {
		return almost_full;
	}

	public Port getDout() {
		return q;
	}

	public Port getEmpty() {
		return empty;
	}

	public Port getFull() {
		return full;
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { data, ready };
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { q, empty, full, almost_full };
	}

	private boolean isAlmostFull() {
		return count >= size / 4;
	}

	private boolean isEmpty() {
		return count == 0;
	}

	private boolean isFull() {
		return count == size;
	}

}