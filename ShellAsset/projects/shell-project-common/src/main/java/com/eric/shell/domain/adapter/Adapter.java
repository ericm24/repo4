package com.eric.shell.domain.adapter;

import java.util.List;

import com.eric.shell.domain.common.message.Message;
import com.eric.shell.domain.context.ShellContext;

public interface Adapter
{
	public List<Message> getMessages();

	public ShellContext toContext(String[] params);

}
