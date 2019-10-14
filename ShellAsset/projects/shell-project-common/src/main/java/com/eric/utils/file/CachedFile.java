/**
 * COPYRIGHT NOTICE
 * Copyright (c) 2012 by Citigroup, Inc.
 * All rights reserved. These materials are internal and proprietary to Citigroup,
 * Inc.  No part of this code may be reproduced, published in any form by any means
 * (electronic or mechanical, including photocopy or any information storage or
 * retrieval system), nor may the materials be disclosed to third parties, or
 * used in derivative works without the express written authorization of Citigroup, Inc.
 */
package com.eric.utils.file;

import java.util.Properties;

public class CachedFile
{
	private long lastModifiedTime_;
	private Properties props_;

	public CachedFile(long modifyTime, Properties props)
	{
		lastModifiedTime_ = modifyTime;
		props_ = props;
	}

	public long getLastModifiedTime()
	{
		return lastModifiedTime_;
	}

	public Properties getProperties()
	{
		return props_;
	}
};