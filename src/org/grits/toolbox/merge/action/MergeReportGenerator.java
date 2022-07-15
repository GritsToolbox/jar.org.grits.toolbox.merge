package org.grits.toolbox.merge.action;

import java.util.List;

import org.grits.toolbox.merge.om.data.ExperimentAnnotation;
import org.grits.toolbox.merge.om.data.MergeReport;
import org.grits.toolbox.merge.om.data.MergeSettings;

public class MergeReportGenerator {
	private MergeReport report = new MergeReport();
	private MergeSettings settings = null;
	
	public MergeReportGenerator(List<ExperimentAnnotation> experimentList, double interval, String accuracyType) {
		this.settings = new MergeSettings(interval);
		this.settings.setToleranceType(accuracyType);
		this.settings.setExperimentList(experimentList);
	}
	
	public MergeReport generateReport(){
		ExtractRequiredData extractor = new ExtractRequiredData(settings);
		extractor.extractIntervals();
		extractor.extractFeatures();
		report.setRows(extractor.generateReportRows());
		report.setFeatureCustomExtraData(extractor.extractFeatureCustomExtraData());
		report.setAnnotationCustomExtraData(extractor.extractAnnotationCustomExtraData());
		report.setPeakCustomExtraData(extractor.extractPeakCustomExtraData());
		report.setSettings(settings);
		return report;
	}
	
	

}
