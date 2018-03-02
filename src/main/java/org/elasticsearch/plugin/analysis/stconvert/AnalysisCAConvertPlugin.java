package org.elasticsearch.plugin.analysis.stconvert;

import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class AnalysisCAConvertPlugin extends Plugin implements AnalysisPlugin {

	@Override
	public Map<String, AnalysisModule.AnalysisProvider<CharFilterFactory>> getCharFilters() {
		Map<String, AnalysisModule.AnalysisProvider<CharFilterFactory>> extra = new HashMap<>();
		extra.put("caconvert", CAConvertCharFilterFactory::new);
		return extra;
	}

}
