/*
 * Copyright (c) 2009-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.empire.api;

import com.clarkparsia.empire.ds.Alias;
import com.clarkparsia.empire.ds.DataSourceFactory;
import com.clarkparsia.empire.ds.DataSource;
import com.clarkparsia.empire.ds.DataSourceException;

import java.util.Map;

import java.io.File;

import com.complexible.common.openrdf.model.ExtGraph;
import com.complexible.common.openrdf.model.Graphs;

/**
 * <p>DataSourceFactory implementation to create a DataSource used for testing</p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.6.3
 */
@Alias("test")
public final class TestDataSourceFactory implements DataSourceFactory {

	/**
	 * @inheritDoc
	 */
    @Override
	public boolean canCreate(final Map<String, Object> theMap) {
		return true;
	}

	/**
	 * @inheritDoc
	 */
    @Override
	public DataSource create(final Map<String, Object> theMap) throws DataSourceException {
		ExtGraph aGraph = Graphs.extend(Graphs.newGraph());

		if (theMap.containsKey("files")) {
			for (String aFile : theMap.get("files").toString().split(",")) {
				try {
					aGraph.read(new File(aFile.trim()));
				}
				catch (Exception e) {
					throw new DataSourceException(e);
				}
			}
		}

		return new TestDataSource(aGraph);
	}
}
