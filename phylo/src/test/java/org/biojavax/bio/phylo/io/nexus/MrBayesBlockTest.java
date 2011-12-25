/**
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 */

package org.biojavax.bio.phylo.io.nexus;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test for MrBayes parsing.
 * 
 * @author Bruno P. Kinoshita
 */
public class MrBayesBlockTest extends TestCase {

	NexusFile nexus1;
	NexusFile nexus2;
	NexusFile nexus3;
	NexusFile nexus4;

	public MrBayesBlockTest(String name) {
		super(name);
	}

	protected void setUp() {
		try {
			NexusFileBuilder builder = new NexusFileBuilder();
			NexusFileFormat.parseInputStream(builder, this.getClass()
					.getResourceAsStream("mrbayesblock1.nex"));
			nexus1 = builder.getNexusFile();
			NexusFileFormat.parseInputStream(builder, this.getClass()
					.getResourceAsStream("mrbayesblock2.nex"));
			nexus2 = builder.getNexusFile();
			NexusFileFormat.parseInputStream(builder, this.getClass()
					.getResourceAsStream("mrbayesblock3.nex"));
			nexus3 = builder.getNexusFile();
			NexusFileFormat.parseInputStream(builder, this.getClass()
					.getResourceAsStream("primates.nex"));
			nexus4 = builder.getNexusFile();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private MrBayesBlock getMrBayesBlock(NexusFile nexus) {
		Iterator<?> it = nexus.blockIterator();
		NexusBlock block;
		while (it.hasNext()) {
			block = (NexusBlock) it.next();
			if (block.getBlockName().equals(MrBayesBlock.MRBAYES_BLOCK)) {
				return (MrBayesBlock) block;
			}
		}
		return null;
	}

	public void testAutocloseYesNowarnNoWithSpacesAndExecute() {
		MrBayesBlock block = getMrBayesBlock(nexus1);

		assertNotNull(block);

		assertTrue(block.getAutoclose());
		assertFalse(block.getNowarn());
		assertNotNull(block.getExecute());
	}

	public void testAutocloseNoNowarnYesWithSpaces() {
		MrBayesBlock block = getMrBayesBlock(nexus2);

		assertNotNull(block);

		assertFalse(block.getAutoclose());
		assertTrue(block.getNowarn());
	}

	public void testWithOnlyANotInterpretedToken() {
		MrBayesBlock block = getMrBayesBlock(nexus3);

		assertNotNull(block);

		assertFalse(block.getAutoclose());
		assertFalse(block.getNowarn());
		assertNull(block.getExecute());
	}
	
	public void testPrimatesComplex() {
		MrBayesBlock block = getMrBayesBlock(nexus4);

		assertNotNull(block);

		assertTrue(block.getAutoclose());
		assertFalse(block.getNowarn());
		assertNull(block.getExecute());
	}

}
