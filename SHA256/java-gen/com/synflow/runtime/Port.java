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
package com.synflow.runtime;

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;

/**
 * This class defines a port for a task or a network. A port can be read,
 * peeked, written.
 * 
 * @author Matthieu Wipliez
 * 
 */
final public class Port {

	/**
	 * when this flag is set, reads/writes are checked
	 */
	public static final int CHECK = 1 << 1;

	/**
	 * when this flag is set, this port uses an additional 'send' variable to
	 * indicate if its contents are valid.
	 */
	public static final int SYNC = 1 << 0;

	/**
	 * when this flag is set, creates a VCD
	 */
	public static final int VCD = 1 << 2;

	private final boolean check;

	private final Entity container;

	/**
	 * current and next data as a BigInteger (if {@link #size} &gt; 64)
	 */
	private BigInteger currentDataBigInteger = ZERO, nextDataBigInteger = ZERO;

	/**
	 * current and next data as a int (if {@link #size} &lt;= 32)
	 */
	private int currentDataInt, nextDataInt;

	/**
	 * current and next data as a long (if 32 &lt; {@link #size} &lt;= 64)
	 */
	private long currentDataLong, nextDataLong;

	private boolean currentSend, nextSend;

	private final String name;

	/**
	 * number of consumers (used when checking)
	 */
	private int numConsumers;

	/**
	 * number of remaining reads (used when checking)
	 */
	private int remainingReads;

	/**
	 * size in bits of this port (used when committing)
	 */
	private final int size;

	private final boolean sync;

	private final boolean vcd;

	/**
	 * Creates a new port.
	 * 
	 * @param container
	 *            task/design containing this port.
	 * @param name
	 *            name of this port
	 * @param size
	 *            size in bits
	 * @param flags
	 *            flags
	 */
	public Port(Entity container, String name, int size, int flags) {
		this.container = container;
		this.name = name;

		// set sync flag
		sync = (flags & SYNC) != 0;
		vcd = (flags & VCD) != 0;

		// set check flag (non-synchronized ports are never checked)
		check = sync && (flags & CHECK) != 0;

		this.size = size;
	}

	/**
	 * Returns <code>true</code> if data is available. This is always
	 * <code>true</code> for non-synchronized ports.
	 * 
	 * @return <code>true</code> if data is available
	 */
	public boolean available() {
		return !sync || currentSend;
	}

	/**
	 * If {@link #check} is <code>true</code>, checks that the data read is
	 * still valid.
	 */
	private void checkReadIfNeeded() {
		if (check) {
			if (remainingReads > 0) {
				String message = "trying to read stale data on " + this;
				throw new RuntimeException(message);
			}

			remainingReads--;
		}
	}

	/**
	 * If {@link #check} is <code>true</code>, checks that new data can be
	 * written.
	 */
	private void checkWriteIfNeeded() {
		if (check) {
			if (remainingReads != 0) {
				String message = "trying to overwrite data on " + this;
				throw new RuntimeException(message);
			}
			remainingReads = numConsumers;
		}
	}

	/**
	 * Commits the value that was last written to this port, so it is available
	 * to readers. If no data needs to be committed, this method does nothing.
	 * 
	 * <p>
	 * If #{check} is true, this method asserts that there are no pending reads
	 * to be done on this port in the current cycle.
	 * </p>
	 * 
	 * @see #commitUnsafe()
	 */
	public void commit() {
		if (sync) {
			currentSend = nextSend;
			if (!nextSend) {
				return;
			}
			nextSend = false;

			checkWriteIfNeeded();
		}

		if (size > 64) {
			if (vcd && currentDataBigInteger != nextDataBigInteger) {
				VcdWriter.getInstance().addValue(this, nextDataBigInteger);
			}
			currentDataBigInteger = nextDataBigInteger;
		} else if (size > 32) {
			if (vcd && currentDataLong != nextDataLong) {
				VcdWriter.getInstance().addValue(this, nextDataLong);
			}
			currentDataLong = nextDataLong;
		} else {
			if (vcd && currentDataInt != nextDataInt) {
				VcdWriter.getInstance().addValue(this, nextDataInt);
			}
			currentDataInt = nextDataInt;
		}
	}

	/**
	 * Increases the number of consumers of this port. Used when connecting the
	 * network. Returns <code>this</code> for convenience.
	 * 
	 * @return <code>this</code>
	 */
	public Port connect() {
		numConsumers++;
		return this;
	}

	/**
	 * Returns this port's name.
	 * 
	 * @return this port's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this port's size in bits.
	 * 
	 * @return this port's size in bits
	 */
	public int getSize() {
		return size;
	}

	public void initialize(BigInteger value) {
		currentDataBigInteger = value;
	}

	public void initialize(boolean value) {
		currentDataInt = value ? 1 : 0;
	}

	public void initialize(int value) {
		currentDataInt = value;
	}

	public void initialize(long value) {
		currentDataLong = value;
	}

	/**
	 * Returns true if this port is 'sync', false otherwise.
	 * 
	 * @return a boolean indicating if this port is 'sync' or not
	 */
	public boolean isSync() {
		return sync;
	}

	public BigInteger peekBigInteger() {
		return currentDataBigInteger;
	}

	public boolean peekBoolean() {
		return currentDataInt != 0;
	}

	public int peekInt() {
		return currentDataInt;
	}

	public long peekLong() {
		return currentDataLong;
	}

	/**
	 * Calls {@link #checkReadIfNeeded()} and then returns the current value of
	 * this port.
	 * 
	 * @return a big integer value
	 */
	public BigInteger readBigInteger() {
		checkReadIfNeeded();
		return currentDataBigInteger;
	}

	/**
	 * Equivalent to <code>readInt() != 0</code>.
	 * 
	 * @return a boolean value
	 */
	public boolean readBoolean() {
		checkReadIfNeeded();
		return currentDataInt != 0;
	}

	/**
	 * Calls {@link #checkReadIfNeeded()} and then returns the current value of
	 * this port.
	 * 
	 * @return an integer value
	 */
	public int readInt() {
		checkReadIfNeeded();
		return currentDataInt;
	}

	/**
	 * Calls {@link #checkReadIfNeeded()} and then returns the current value of
	 * this port.
	 * 
	 * @return a long value
	 */
	public long readLong() {
		checkReadIfNeeded();
		return currentDataLong;
	}

	@Override
	public String toString() {
		return container.getName() + "_" + name;
	}

	/**
	 * Writes the given value on this port. The value is only available to
	 * readers <strong>after</strong> this port has been <em>committed</em> (see
	 * {@link #commit()} method).
	 * 
	 * @param data
	 *            a big integer value
	 * @see #commit()
	 */
	public void write(BigInteger data) {
		nextDataBigInteger = data;
		if (sync) {
			nextSend = true;
		}
	}

	/**
	 * Equivalent to <code>write(data ? 1 : 0)</code>.
	 * 
	 * @param data
	 *            a boolean
	 */
	public void write(boolean data) {
		nextDataInt = data ? 1 : 0;
		if (sync) {
			nextSend = true;
		}
	}

	/**
	 * Writes the given value on this port. The value is only available to
	 * readers <strong>after</strong> this port has been <em>committed</em> (see
	 * {@link #commit()} method).
	 * 
	 * @param data
	 *            an integer value
	 * @see #commit()
	 */
	public void write(int data) {
		nextDataInt = data;
		if (sync) {
			nextSend = true;
		}
	}

	/**
	 * Writes the given value on this port. The value is only available to
	 * readers <strong>after</strong> this port has been <em>committed</em> (see
	 * {@link #commit()} method).
	 * 
	 * @param data
	 *            a long value
	 * @see #commit()
	 */
	public void write(long data) {
		nextDataLong = data;
		if (sync) {
			nextSend = true;
		}
	}

}
