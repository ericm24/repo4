/**
 * 
 * @author eric 
 *
 */
package com.eric.shell.domain.common.message;

import com.eric.shell.domain.AbstractDomainBase;


public abstract class AbstractMessage<E extends Enum<E>> extends AbstractDomainBase
{
	private final E e;

	public AbstractMessage(E e)
	{
		this.e = e;
	}

	public E getLevel()
	{
		return (e);
	}

}
