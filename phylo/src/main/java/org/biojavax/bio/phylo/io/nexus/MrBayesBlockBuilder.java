/*
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

/**
 * Builds Nexus mrbayes blocks.
 * 
 * @author Bruno P. Kinoshita
 * @since 1.8.2
 */
public class MrBayesBlockBuilder extends NexusBlockBuilder.Abstract implements MrBayesBlockListener {

	private MrBayesBlock block;

	public void endBlock() {
		// Nothing to do.
	}

	public void endTokenGroup() {
		// Nothing to do.
		
	}

	@Override
	protected void addComment(NexusComment comment) {
		this.block.addComment(comment);
	}

	@Override
	protected NexusBlock startBlockObject() {
		this.block = new MrBayesBlock();
		return this.block;
	}

	public void setAutoclose(Boolean autoclose) {
		this.block.setAutoclose(autoclose);
	}

	public void setNowarn(Boolean nowarn) {
		this.block.setNowarn(nowarn);
	}

	public void setExecute(String execute) {
		this.block.setExecute(execute);
	}
}
