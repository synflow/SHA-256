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

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class defines an option parser.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OptionParser {

	private boolean binaryMode;

	private boolean checkPorts;

	private boolean createVcd;

	private int numCycles;

	private boolean printCycles;

	public OptionParser(String[] args) {
		Iterator<String> it = Arrays.asList(args).iterator();
		while (it.hasNext()) {
			String argument = it.next();
			if ("-n".equals(argument)) {
				numCycles = parseInt("-n", it);
			} else if ("-b".equals(argument)) {
				binaryMode = true;
			} else if ("-h".equals(argument)) {
				printUsage();
			} else if ("-print-cycles".equals(argument)) {
				printCycles = true;
			} else if ("-check-ports".equals(argument)) {
				checkPorts = true;
			} else if ("-create-vcd".equals(argument)) {
				createVcd = true;
			}
		}
	}

	/**
	 * Returns the number of cycles to run.
	 * 
	 * @return the number of cycles to run
	 */
	public int getNumCycles() {
		return numCycles;
	}

	/**
	 * Returns <code>true</code> if the stimulus/expected should be read/written
	 * in binary mode.
	 * 
	 * @return <code>true</code> if file I/O should be binary
	 */
	public boolean isBinaryMode() {
		return binaryMode;
	}

	/**
	 * Returns <code>true</code> if production/consumption of ports should be
	 * checked.
	 * 
	 * @return <code>true</code> if ports should be checked
	 */
	public boolean isCheckPorts() {
		return checkPorts;
	}

	/**
	 * Returns <code>true</code> if the simulator must create a VCD file.
	 * 
	 * @return <code>true</code> if the simulator must create a VCD file
	 */
	public boolean isCreateVcd() {
		return createVcd;
	}

	/**
	 * Returns <code>true</code> if we should print cycles.
	 * 
	 * @return <code>true</code> if we should print cycles
	 */
	public boolean isPrintCycles() {
		return printCycles;
	}

	/**
	 * Parses an integer argument for the option with the given name.
	 * 
	 * @param option
	 *            name of an option
	 * @param it
	 *            iterator on main's arguments
	 * @return an integer
	 */
	private int parseInt(String option, Iterator<String> it) {
		if (it.hasNext()) {
			String value = it.next();
			NumberFormat formatter = NumberFormat.getInstance();
			ParsePosition pos = new ParsePosition(0);
			Number num = formatter.parse(value, pos);
			if (pos.getIndex() == 0) {
				System.err.println("'" + value + "' is not a valid integer.");
			} else {
				return num.intValue();
			}
		} else {
			System.err.println("Expected argument for option " + option + "");
		}

		return 0;
	}

	/**
	 * Prints usage.
	 */
	private void printUsage() {
		System.err.println("Usage: program <options>");
		System.err.println("    -n <numCycles>    defines the number of cycles");
		System.err.println("    -b                reads test files in binary mode (.bin extension)");
		System.exit(0);
	}

}
