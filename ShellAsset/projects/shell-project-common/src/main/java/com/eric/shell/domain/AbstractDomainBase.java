package com.eric.shell.domain;
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


import java.io.Serializable;
/**
 *  Domain base class that pre-loads an ID 
 * 
 * @author em32459 
 * 
 *  
 */
public abstract class AbstractDomainBase implements Serializable
{
	private static final long serialVersionUID = 1L;	

	private String ID = null;
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);

	public AbstractDomainBase()
	{
		this.initID();
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	public String getID()
	{
		return (this.ID);
	}

	public void setID(String newValue)
	{
		this.ID = newValue;
	}
	/*
	 * Generate the initial ID by default.  This can be over-rode if desired,
	 * say from a persistent store.
	 */
	private void initID()
	{
		this.setID( Integer.toHexString(System.identityHashCode( this )) );		
		
		return;
	}	

	
}
