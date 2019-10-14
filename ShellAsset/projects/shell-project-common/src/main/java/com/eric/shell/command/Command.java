/**
 * Command interface that follows GO4 Command Pattern.  .execute() & .undo()
 * are required behavior methods.
 * 
 * @author eric
 * 
 */
package com.eric.shell.command;

import com.eric.shell.domain.context.ShellContext;



public interface Command
{
	/**
     * Execute concrete behavior, main command method, to get-r-done!
     * @return <CODE>true</CODE> if command successful.  
     * 
     *  If false, check the message collection.
     * 
     * <CODE>false</CODE> otherwise
     */	
	public boolean execute();

	/**
     * UNDO changes by execute method.  Restores context and object-graph 
     * to original form.
     * @return <CODE>true</CODE> if UNDO successful. 
     * <CODE>false</CODE> otherwise
     */
	public boolean undo();

	/**
     * Required to add the context to the command instance.
     * @param ShellContext
     * 
     * @return <CODE>true</CODE> if UNDO successful. 
     * <CODE>false</CODE> otherwise
     */
	public void setContext(ShellContext context);
}
