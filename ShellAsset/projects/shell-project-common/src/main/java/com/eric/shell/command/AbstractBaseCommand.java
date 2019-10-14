/**
 * ---------------------------------------------------------------------------
 * COPYRIGHT NOTICE  Copyright (c) 2011 by Citigroup, Inc.  All rights reserved.
 * These materials are confidential and proprietary to  Citigroup, Inc.
 * No part of this code may be reproduced, published in
 * any form by any means (electronic or mechanical, including photocopy or
 * any information storage or retrieval system), nor may the materials be
 * disclosed to third parties, or used in derivative works without the
 * express written authorization of Citigroup, Inc.
 * ---------------------------------------------------------------------------
 */
package com.eric.shell.command;

import com.eric.shell.domain.context.ShellContext;


/**
 * Abstract-Default Implementation for Command interface.  
 * Allows for Abstract-Interface Pattern. (Combined w/Command Pattern). 
 * -Provides convenience implementation for set/get Context.
 * 
 * @author em32459
 * 
 */
public abstract class AbstractBaseCommand implements Command
{

	protected ShellContext context = null;

	public abstract boolean execute();

	public abstract boolean undo();
	/**
     * Setter for context implementation.   
     * @return VOID 
     * @param newContext - new context to attach to this command. 
     * 
     */	
	public void setContext(ShellContext newValue)
	{
		this.context = newValue;

		return;
	}

	/**
     * Getter for context implementation.   
     * @return Context - current context. 
     * 
     */	
	public ShellContext getContext()
	{
		return context;
	}

}
