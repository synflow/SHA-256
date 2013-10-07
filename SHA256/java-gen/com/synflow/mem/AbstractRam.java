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
package com.synflow.mem;

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.Arrays;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;

/**
 * This class defines an abstract RAM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractRam implements Entity {

	protected final BigInteger[] contentsBigInteger;

	protected final int[] contentsInt;

	protected final long[] contentsLong;

	protected Port data;

	private String name;

	protected final Port q;

	private final int size;

	/**
	 * Creates an abstract RAM with the given name, flags, depth and width.
	 * 
	 * @param name
	 *            name of this RAM
	 * @param flags
	 *            flags
	 * @param depth
	 *            depth
	 * @param width
	 *            width
	 */
	public AbstractRam(String name, int flags, int depth, int width) {
		this.name = name;
		size = width;

		q = new Port(this, "q", width, flags);
		if (size > 64) {
			contentsBigInteger = new BigInteger[1 << depth];
			Arrays.fill(contentsBigInteger, ZERO);
			contentsLong = null;
			contentsInt = null;
		} else if (width > 32) {
			contentsBigInteger = null;
			contentsLong = new long[1 << depth];
			contentsInt = null;
		} else {
			contentsBigInteger = null;
			contentsLong = null;
			contentsInt = new int[1 << depth];
		}
	}

	@Override
	public void commit() {
		q.commit();
	}

	@Override
	public abstract void execute();

	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the <code>q</code> port.
	 * 
	 * @return the <code>q</code> port
	 */
	public final Port getQ() {
		return q;
	}

	/**
	 * Reads data from one of the contents array at the given address.
	 * 
	 * @param addr
	 *            address at which this method should read data
	 */
	protected final void readDataFromArray(int addr) {
		if (size > 64) {
			q.write(contentsBigInteger[addr]);
		} else if (size > 32) {
			q.write(contentsLong[addr]);
		} else {
			q.write(contentsInt[addr]);
		}
	}

	@Override
	public final String toString() {
		return getName();
	}

	/**
	 * Writes data to one of the contents array at the given address.
	 * 
	 * @param addr
	 *            address at which this method should write data
	 */
	protected final void writeDataToArray(int addr) {
		if (size > 64) {
			contentsBigInteger[addr] = data.readBigInteger();
		} else if (size > 32) {
			contentsLong[addr] = data.readLong();
		} else {
			contentsInt[addr] = data.readInt();
		}
	}

}