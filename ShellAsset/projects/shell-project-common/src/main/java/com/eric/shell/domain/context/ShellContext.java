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
package com.eric.shell.domain.context;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.eric.shell.domain.AbstractDomainBase;
import com.eric.shell.domain.common.constant.BaseConstants;
import com.eric.shell.domain.common.message.MessageCollection;

public class ShellContext extends AbstractDomainBase 
{

	private Map<String, Object> params = null;
	private MessageCollection messages = null;

	public Map<String, Object> getParams()
	{
		return params;
	}

	public MessageCollection getMessages()
	{
		if (messages == null)
		{
			messages = new MessageCollection();
		}

		return messages;
	}

	public void setMessages(MessageCollection newMessages)
	{
		if (newMessages != null)
		{
			this.messages = newMessages;
		}
	}

	public void setParams(Map<String, Object> params)
	{
		if (params != null)
		{
			this.params = params;
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = Integer.parseInt( this.getID() );
		result = prime * result
				+ ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if ( obj == null )
		{
			return false;
		}
		
		if (this == obj)
		{
			return true;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		ShellContext other = (ShellContext) obj;
		
		if (messages == null)
		{
			if (other.messages != null)
			{
				return false;
			}
		}		
		else if (!messages.equals(other.messages))
		{
			return false;
		}
		
		if (params == null)
		{
			if (other.params != null)
			{
				return false;
			}
		}
		else if (!params.equals(other.params))
		{
			return false;
		}
		
		return true;
	}

	@Override
	public String toString()
	{
		Map<String, Object> params	= null;
		Set<String> propsSettings	= null;
		Iterator<String> itr		= null;		
		String key					= null;
		String value				= null;
		String returnValue			= null;
		StringBuilder sb			= null;

		sb = new StringBuilder();

		sb.append("[ ShellContext: " +
                	                " ID = |" + this.getID() +
                	"|, serialVersionUID = |" + getSerialversionuid() + "| " +
                	 BaseConstants.FIVE_SPACES + 
                	"[ Params: "
				  );
		
		
		// First check and see what we have for parameters...
		
		params = this.getParams();
		
		if ( params != null )
		{		
			propsSettings 	= params.keySet();			
			itr 			= propsSettings.iterator();
			
			
			while( itr.hasNext() ) 
			{
				key  		= (String) itr.next();
				value 		= (String) params.get( key ).toString();
				
				sb.append(    " key = |" + key +
						  "|, value = |" + value +
						  "|"   );					
			}	
		
		}
		else
		{
			sb.append(BaseConstants.NULL_DELIMITED);
		}
		
		// Second check messages.... 
		
		if ( messages != null )
		{
			sb.append( messages.toString() );
		}
		else
		{
			sb.append(BaseConstants.NULL_DELIMITED);
		}
			
		sb.append(" ] ");
		
		returnValue = sb.toString();
		
		return( returnValue );
		
	}

}
