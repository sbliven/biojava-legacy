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
 * Listens to events that represent Nexus mrbayes blocks.
 * 
 * @author Bruno P. Kinoshita
 * @since 1.8.2
 */
public interface MrBayesBlockListener extends NexusBlockListener {

	public void setAutoclose(Boolean autoclose);
	
	public void setNowarn(Boolean nowarn);
	
	public void setExecute(String execute);
	
}
