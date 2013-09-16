/*
 * Copyright (c) 2013, Synflow SAS
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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.synflow.runtime.test.StimulusFile;
import com.synflow.runtime.test.TraceFile;

/**
 * This class runs an entity.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Runner {

	private final boolean binaryMode;

	private final boolean createVcd;

	private final Entity entity;

	private final String name;

	private final int numCycles;

	private final boolean printCycles;

	private final VcdWriter writer;

	/**
	 * Creates a runner instance for the given class.
	 * 
	 * @param clasz
	 *            class of an entity
	 * @param args
	 *            command line arguments
	 * @throws ReflectiveOperationException
	 */
	public Runner(Class<? extends Entity> clasz, String[] args)
			throws ReflectiveOperationException, IOException {
		final OptionParser parser = new OptionParser(args);
		numCycles = parser.getNumCycles();
		if (numCycles <= 0) {
			System.err
					.println("Number of cycles must be greater than zero, exiting.");
			System.exit(1);
		}
		System.out.println("Running simulation of " + numCycles + " cycles");

		binaryMode = parser.isBinaryMode();
		createVcd = parser.isCreateVcd();
		printCycles = parser.isPrintCycles();

		int _flags = 0;
		if (parser.isCheckPorts()) {
			_flags |= Port.CHECK;
		}
		if (createVcd) {
			_flags |= Port.VCD;
		}

		// creates entity
		name = clasz.getSimpleName();
		final Constructor<?>[] ctors = clasz.getConstructors();
		entity = (Entity) ctors[0].newInstance(name, _flags);

		// connects entity
		entity.connect(entity.getInputs());

		// creates VCD writer
		writer = VcdWriter.getInstance();
		if (createVcd) {
			writer.setFileName(name + ".vcd");
			writer.writeHeader(entity);
		}
	}

	private void doSimulationBinary() {
		// TODO Auto-generated method stub

	}

	private void doSimulationText() throws IOException {
		StimulusFile stimFile = null;
		if (Files.exists(Paths.get("stimSigNames.txt"))) {
			stimFile = new StimulusFile("stimSigNames.txt",
					"stimSigValues.txt", entity.getInputs());
		} else {
			System.out.println("Running without stimulus...");
		}

		StimulusFile expectedFile = null;
		TraceFile traceFile = null;
		if (Files.exists(Paths.get("traceSigNames.txt"))) {
			expectedFile = new StimulusFile("traceSigNames.txt",
					"traceSigValues.txt", entity.getOutputs());
		} else {
			traceFile = new TraceFile("traceSigNames.txt",
					"traceSigValues.txt", entity.getOutputs());
		}

		for (int _cycle = 0; _cycle < numCycles; _cycle++) {
			if (stimFile != null) {
				if (stimFile.hasNext()) {
					stimFile.readLine();
				}
				stimFile.commit();
			}

			if (createVcd) {
				writer.setTimestamp(_cycle);
			}

			if (printCycles) {
				System.out.println("-- cycle " + _cycle + " --");
			}
			entity.execute();
			entity.commit();

			if (expectedFile != null) {
				if (expectedFile.hasNext()) {
					expectedFile.checkLine();
				}
			} else {
				traceFile.writeLine();
			}
		}
	}

	public void run() throws IOException {
		// execute
		long t1 = System.currentTimeMillis();

		try {
			if (binaryMode) {
				doSimulationBinary();
			} else {
				doSimulationText();
			}
		} finally {
			if (createVcd) {
				writer.flush();
			}
		}

		long t2 = System.currentTimeMillis();
		System.out
				.println("Finished simulation of " + numCycles + " cycles of "
						+ entity.getName() + " in " + (t2 - t1) + " ms");
	}

}
