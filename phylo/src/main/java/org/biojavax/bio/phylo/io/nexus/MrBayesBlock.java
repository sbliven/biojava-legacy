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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Represents Nexus mrbayes blocks.
 * 
 * @author Bruno P. Kinoshita
 * @since 1.8.2
 */
public class MrBayesBlock extends NexusBlock.Abstract {

	// Using basic variables from: 
	// http://mrbayes.sourceforge.net/wiki/index.php/FAQ#How_do_I_run_MrBayes_in_batch_mode.3F
	
	private Boolean autoclose = Boolean.FALSE;
	private Boolean nowarn = Boolean.FALSE;
	private String execute = null;
	
	private List<NexusComment> comments = new ArrayList<NexusComment>();
	
	/**
	 * A constant representing the name of MrBayes blocks.
	 */
	public static final String MRBAYES_BLOCK = "MRBAYES";
	
	/**
	 * Delegates to NexusBlock.Abstract constructor using MrBayesBlock.MRBAYES_BLOCK
	 * as the name.
	 */
	public MrBayesBlock() {
		super(MrBayesBlock.MRBAYES_BLOCK);
	}

	@Override
	protected void writeBlockContents(Writer writer) throws IOException {
		for (final Iterator<NexusComment> i = this.comments.iterator(); i.hasNext();) {
			(i.next()).writeObject(writer);
			writer.write(NexusFileFormat.NEW_LINE);
		}
		
		writer.write(" mrbayes" + NexusFileFormat.NEW_LINE);
		
		// autoclose
		writer.write('\t');
		writer.write("set autoclose=");
		writer.write((this.autoclose != null && this.autoclose == Boolean.TRUE) ? "yes" : "no");
		
		// nowarn
		writer.write(' ');
		writer.write("nowarn=");
		writer.write((this.nowarn != null && this.nowarn == Boolean.TRUE) ? "yes" : "no");
		writer.write(';');
		
		writer.write(NexusFileFormat.NEW_LINE);
		
		// execute
		if( this.execute != null && this.execute.trim().length() > 0 ) {
			writer.write('\t');
			writer.write("execute ");
			writer.write(this.execute);
			writer.write(NexusFileFormat.NEW_LINE);
		}
		
	}
	
	/**
	 * Adds a comment.
	 * 
	 * @param comment
	 *            the comment to add.
	 */
	public void addComment(final NexusComment comment) {
		this.comments.add(comment);
	}
	
	/**
	 * Removes a comment.
	 * 
	 * @param comment
	 *            the comment to remove.
	 */
	public void removeComment(final NexusComment comment) {
		this.comments.remove(comment);
	}
	
	/**
	 * Returns all comments.
	 * 
	 * @return all the selected comments.
	 */
	public List<NexusComment> getComments() {
		return this.comments;
	}
	
	public Boolean getAutoclose() {
		return autoclose;
	}
	
	public void setAutoclose(Boolean autoclose) {
		this.autoclose = autoclose;
	}
	
	public Boolean getNowarn() {
		return nowarn;
	}
	
	public void setNowarn(Boolean nowarn) {
		this.nowarn = nowarn;
	}
	
	public String getExecute() {
		return execute;
	}
	
	public void setExecute(String execute) {
		this.execute = execute;
	}

}
