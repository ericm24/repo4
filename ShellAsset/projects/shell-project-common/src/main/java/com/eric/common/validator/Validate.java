/**
 * Validate interface 
 * 
 * @author eric
 * 
 */

package com.eric.common.validator;

import com.eric.shell.domain.AbstractDomainBase;
import com.eric.shell.domain.common.message.MessageCollection;
import com.eric.shell.domain.context.ShellContext;

public interface Validate
{
	public MessageCollection validate(ShellContext context);
	public MessageCollection validate(AbstractDomainBase base);	
}
