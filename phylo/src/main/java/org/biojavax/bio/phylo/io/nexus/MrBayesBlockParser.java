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

import org.biojava.bio.seq.io.ParseException;

/**
 * Parses Nexus mrbayes blocks.
 * 
 * @author Bruno P. Kinoshita
 * @since 1.8.2
 */
public class MrBayesBlockParser extends NexusBlockParser.Abstract {

	private boolean expectingAutoclose;
	private boolean expectingAutocloseEquals;
	private boolean expectingAutocloseValue;
	private boolean expectingNowarn;
	private boolean expectingNowarnEquals;
	private boolean expectingNowarnValue;
	private boolean expectingExecute;
	private boolean expectingExecuteValue;
	
	/**
	 * Delegates to CharactersBlockParser.
	 * 
	 * @param blockListener
	 *            the listener to send parse events to.
	 */
	public MrBayesBlockParser(MrBayesBlockListener blockListener) {
		super(blockListener);
	}

	@Override
	protected void resetStatus() {
		this.expectingAutoclose = false;
		this.expectingAutocloseEquals = false;
		this.expectingAutocloseValue = false;
		this.expectingNowarn = false;
		this.expectingNowarnEquals = false;
		this.expectingNowarnValue = false;
		this.expectingExecute = true;
		this.expectingExecuteValue = false;
	}

	@Override
	public void parseToken(String token) throws ParseException {
		if (token.trim().length() == 0)
			return;
		
		if("set".equalsIgnoreCase(token)) {
			this.expectingAutoclose = true;
			this.expectingNowarn = true;
			this.expectingExecute = false;
		}
		
		// autoclose
		else if(expectingAutoclose && token.toLowerCase().startsWith("autoclose")) {
			this.expectingAutoclose = false;
			if (token.indexOf("=") >= 0) {
				final String parts[] = token.split("=");
				String autocloseValue = parts[1];
				if(autocloseValue.equalsIgnoreCase("yes")) {
					((MrBayesBlockListener)this.getBlockListener()).setAutoclose(Boolean.TRUE);
				} else {
					((MrBayesBlockListener)this.getBlockListener()).setAutoclose(Boolean.FALSE);
				}
				this.expectingExecute = true;
			} else {
				this.expectingAutocloseEquals = true;
			}
		} else if (this.expectingAutocloseEquals && token.startsWith("=")) {
			this.expectingAutocloseEquals = false;
			final String parts[] = token.split("=");
			if (parts.length > 1) {
				String autocloseValue = parts[1];
				if(autocloseValue.equalsIgnoreCase("yes")) {
					((MrBayesBlockListener)this.getBlockListener()).setAutoclose(Boolean.TRUE);
				} else {
					((MrBayesBlockListener)this.getBlockListener()).setAutoclose(Boolean.FALSE);
				}
				this.expectingExecute = true;
			} else {
				this.expectingAutocloseValue = true; 
			}
		} else if (this.expectingAutocloseValue) {
			String autocloseValue = token;
			if(autocloseValue.equalsIgnoreCase("yes")) {
				((MrBayesBlockListener)this.getBlockListener()).setAutoclose(Boolean.TRUE);
			} else {
				((MrBayesBlockListener)this.getBlockListener()).setAutoclose(Boolean.FALSE);
			}
			this.expectingAutocloseValue = false;
			this.expectingExecute = true;
		}
		
		// nowarn
		else if(expectingNowarn && token.toLowerCase().startsWith("nowarn")) {
			this.expectingNowarn = false;
			if (token.indexOf("=") >= 0) {
				final String parts[] = token.split("=");
				String nowarnValue = parts[1];
				if(nowarnValue.equalsIgnoreCase("yes")) {
					((MrBayesBlockListener)this.getBlockListener()).setNowarn(Boolean.TRUE);
				} else {
					((MrBayesBlockListener)this.getBlockListener()).setNowarn(Boolean.FALSE);
				}
				this.expectingExecute = true;
			} else {
				this.expectingNowarnEquals = true;
			}
		} else if (this.expectingNowarnEquals && token.startsWith("=")) {
			this.expectingNowarnEquals = false;
			final String parts[] = token.split("=");
			if (parts.length > 1) {
				String nowarnValue = parts[1];
				if(nowarnValue.equalsIgnoreCase("yes")) {
					((MrBayesBlockListener)this.getBlockListener()).setNowarn(Boolean.TRUE);
				} else {
					((MrBayesBlockListener)this.getBlockListener()).setNowarn(Boolean.FALSE);
				}
				this.expectingExecute = true;
			} else {
				this.expectingNowarnValue = true; 
			}
		} else if (this.expectingNowarnValue) {
			String nowarnValue = token;
			if(nowarnValue.equalsIgnoreCase("yes")) {
				((MrBayesBlockListener)this.getBlockListener()).setNowarn(Boolean.TRUE);
			} else {
				((MrBayesBlockListener)this.getBlockListener()).setNowarn(Boolean.FALSE);
			}
			this.expectingNowarnValue = false;
			this.expectingExecute = true;
		}
		
		// execute
		else if (this.expectingExecute && "execute".equalsIgnoreCase(token)) {
			this.expectingExecute = false;
			expectingExecuteValue = true;
		} else if(this.expectingExecuteValue) {
			expectingExecuteValue = false;
			((MrBayesBlockListener)this.getBlockListener()).setExecute(token);
		}
	}

}
