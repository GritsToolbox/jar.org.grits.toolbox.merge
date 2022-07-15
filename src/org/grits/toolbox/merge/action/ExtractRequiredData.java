package org.grits.toolbox.merge.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.grits.toolbox.merge.om.data.ExperimentAnnotation;
import org.grits.toolbox.merge.om.data.ExtGlycanFeature;
import org.grits.toolbox.merge.om.data.ExtPeak;
import org.grits.toolbox.merge.om.data.Interval;
import org.grits.toolbox.merge.om.data.MergeSettings;
import org.grits.toolbox.merge.om.data.ReportRow;
import org.grits.toolbox.ms.annotation.utils.AnnotationRowExtraction;
import org.grits.toolbox.ms.om.data.Annotation;
import org.grits.toolbox.ms.om.data.CustomExtraData;
import org.grits.toolbox.ms.om.data.Data;
import org.grits.toolbox.ms.om.data.Feature;
import org.grits.toolbox.ms.om.data.FeatureSelection;
import org.grits.toolbox.ms.om.data.Annotation;
import org.grits.toolbox.ms.om.data.Method;
import org.grits.toolbox.ms.om.data.Peak;
import org.grits.toolbox.ms.om.data.Scan;
import org.grits.toolbox.ms.om.data.ScanFeatures;
import org.grits.toolbox.ms.om.io.xml.AnnotationReader;

public class ExtractRequiredData {
	private HashMap<Double,Interval> intervals = new HashMap<Double,Interval>();
	private HashMap<Integer,HashMap<Integer,List<Annotation>>> allAnnotations = new HashMap<Integer,HashMap<Integer,List<Annotation>>>();
	private HashMap<Integer,HashMap<Integer,List<ExtGlycanFeature>>> allExtGlycanFeatures = 
			new HashMap<Integer,HashMap<Integer,List<ExtGlycanFeature>>>();
	private MergeSettings settings = null;

	public ExtractRequiredData(MergeSettings settings){
		this.settings = settings;
	}

	// This currently only works for Direct Infusion where all the data has been merged into a single scan (assuming first MS 1 scan
	// For us to support LC-MS, would we merge based on retention time??? This is where a feature-centric view would work -> merge the feature ids

	private Scan getFirstMS1Scan(Data data) {
		for(Integer scanId : data.getScans().keySet()){
			Scan scan = data.getScans().get(scanId);
			if( scan.getMsLevel() == 1 ) 
				return scan;
		}
		return null;
	}

	private List<Peak> peakSetToList(Set<Peak> peakSet) {
		List<Peak> lPeaks = new ArrayList<Peak>();
		for (Peak peak : peakSet ) {
			if( peak == null) {
				continue;
			}
			lPeaks.add(peak);
		}		
		Collections.sort(lPeaks);
		return lPeaks;
	}
	
	public void extractIntervals(){
		AnnotationReader reader = new AnnotationReader();
		boolean flag = false;

		for(ExperimentAnnotation expAnnotations : settings.getExperimentList()){
			String sReportFile = expAnnotations.getAnnotationFileArchive();
			Data data = reader.readDataWithoutFeatures(sReportFile);
			Scan firstMS1Scan = getFirstMS1Scan(data);
			ScanFeatures scanFeatures = reader.readScanAnnotation(sReportFile, firstMS1Scan.getScanNo());
			int iPeakCnt = 1;

			HashMap<String, List<Feature>> htPeakToFeatures = null;		
			htPeakToFeatures = AnnotationRowExtraction.createRowIdToFeatureHash(scanFeatures);
			boolean bNeedsConvert = false;
			if( htPeakToFeatures.isEmpty() ) { // no features by row ids? Is this an old project? try the old way
				htPeakToFeatures = AnnotationRowExtraction.createPeakIdToFeatureHash(scanFeatures);
				bNeedsConvert = true;
			}
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> htParentScanToParentPeaksToSubScan = AnnotationRowExtraction.createParentScanToParentPeaksToSubScanHash(data);
			if( data.getDataHeader().getMethod().getMsType().equals(Method.MS_TYPE_INFUSION) ) {
				AnnotationRowExtraction.updateParentScanToParentPeaksToSubScanHashForDirectInfusion(htParentScanToParentPeaksToSubScan, data) ;
			}	

			List<Peak> sortedPeaks = peakSetToList(scanFeatures.getScanPeaks());
			for (Peak peak : sortedPeaks ) {
				if( peak == null) {
					continue;
				}
				ArrayList<Scan> precursorScans = AnnotationRowExtraction.getPrecursorScan(data,
						firstMS1Scan.getScanNo(), peak.getId(), htParentScanToParentPeaksToSubScan);
				if( bNeedsConvert ) {
					AnnotationRowExtraction.convertPeakIdsToRowIds(data, scanFeatures, firstMS1Scan.getScanNo(), 
							peak.getId(), precursorScans, htPeakToFeatures );
				}
				// sorting to guarantee same intervals between creation of reports
				Collections.sort(precursorScans);
				for( Scan precursorScan : precursorScans ) {
					String sRowId = Feature.getRowId(peak.getId(), precursorScan.getScanNo(), scanFeatures.getUsesComplexRowId());
					if( htPeakToFeatures.containsKey(sRowId) ) {    
						flag = false;
						ExtPeak precursor = new ExtPeak();
						//precursor = scan.getPrecursor();
						precursor.setExpAnnotationId(expAnnotations.getAnnotationEntryId());
						precursor.setScanId(precursorScan.getScanNo()); // TODO: get scan num, but peak may produce multiple scans
						precursor.setSourcePeakId(peak.getId());
						precursor.setExtPeakId(iPeakCnt++);
						precursor.setMz(peak.getMz());
						precursor.setIntensity(peak.getIntensity());
						precursor.setRelativeIntensity(peak.getRelativeIntensity());
						precursor.setPrecursorIntensity(peak.getPrecursorIntensity());
						precursor.setPrecursorMz(peak.getPrecursorMz());
						precursor.setIntegerProp(peak.getIntegerProp());
						precursor.setDoubleProp(peak.getDoubleProp());
						precursor.setPrecursorCharge(peak.getPrecursorCharge());
						precursor.setCharge(peak.getCharge());
						for(Double mz : intervals.keySet()){
							double tol = 0.0;
							if(settings.getToleranceType().equals("Ppm"))
								tol = (settings.getTolerance()/1000000)* precursor.getMz();
							else
								tol = settings.getTolerance();
							if(Math.abs(precursor.getMz()-mz)<=tol){
								//							precursor.setExtPeakId(intervals.get(mz).getPeaks().size()+1);
								intervals.get(mz).getPeaks().add((ExtPeak) precursor);

								flag = true;
								break;
							}
						}
						if(!flag){
							Interval interval = new Interval();
							interval.setMz(precursor.getMz());
							//						precursor.setExtPeakId(1);
							interval.getPeaks().add((ExtPeak) precursor);
							intervals.put(interval.getMz(), interval);
						}				
					}
				}
			}					
		}

	}

	public void extractAnnotations(){
		AnnotationReader reader = new AnnotationReader();
		for(ExperimentAnnotation expAnnotations : settings.getExperimentList()){
			String sReportFile = expAnnotations.getAnnotationFileArchive();
			Data data = reader.readDataWithoutFeatures(sReportFile);
			HashMap<Integer,List<Annotation>> dataAnnotations = new HashMap<Integer,List<Annotation>>();
			for(Annotation ann : data.getAnnotation()){
				for(String scanId : ann.getScores().keySet()){
					int temp = Integer.parseInt(scanId);
					if(dataAnnotations.get(temp)!=null){
						dataAnnotations.get(temp).add((Annotation)ann);
					}else{
						List<Annotation> anns = new ArrayList<Annotation>();
						anns.add((Annotation)ann);
						dataAnnotations.put(temp, anns);
					}
				}
			}
			allAnnotations.put(expAnnotations.getAnnotationEntryId(), dataAnnotations);
		}
	}

	public List<CustomExtraData> extractFeatureCustomExtraData(){
		List<CustomExtraData> lCED = new ArrayList<>();
		AnnotationReader reader = new AnnotationReader();
		for(ExperimentAnnotation expAnnotations : settings.getExperimentList()){
			String sReportFile = expAnnotations.getAnnotationFileArchive();
			Data data = reader.readDataWithoutFeatures(sReportFile);
			if( data.getDataHeader().getFeatureCustomExtraData() != null ) {
				for( CustomExtraData ced : data.getDataHeader().getFeatureCustomExtraData() ) {
					if( ! lCED.contains(ced) ) {
						lCED.add(ced);
					}
				}
			}
		}
		return lCED;
	}

	public List<CustomExtraData> extractAnnotationCustomExtraData(){
		List<CustomExtraData> lCED = new ArrayList<>();
		AnnotationReader reader = new AnnotationReader();
		for(ExperimentAnnotation expAnnotations : settings.getExperimentList()){
			String sReportFile = expAnnotations.getAnnotationFileArchive();
			Data data = reader.readDataWithoutFeatures(sReportFile);
			if( data.getDataHeader().getAnnotationCustomExtraData() != null ) {
				for( CustomExtraData ced : data.getDataHeader().getAnnotationCustomExtraData() ) {
					if( ! lCED.contains(ced) ) {
						lCED.add(ced);
					}
				}
			}
		}
		return lCED;
	}

	public List<CustomExtraData> extractPeakCustomExtraData(){
		List<CustomExtraData> lCED = new ArrayList<>();
		AnnotationReader reader = new AnnotationReader();
		for(ExperimentAnnotation expAnnotations : settings.getExperimentList()){
			String sReportFile = expAnnotations.getAnnotationFileArchive();
			Data data = reader.readDataWithoutFeatures(sReportFile);
			if( data.getDataHeader().getPeakCustomExtraData() != null ) {
				for( CustomExtraData ced : data.getDataHeader().getPeakCustomExtraData() ) {
					if( ! lCED.contains(ced) ) {
						lCED.add(ced);
					}
				}
			}
		}
		return lCED;
	}

	public void extractFeatures(){
		AnnotationReader reader = new AnnotationReader();
		for(ExperimentAnnotation expAnnotations : settings.getExperimentList()){
			String sReportFile = expAnnotations.getAnnotationFileArchive();
			Data data = reader.readDataWithoutFeatures(sReportFile);
			Scan firstMS1Scan = getFirstMS1Scan(data);
			ScanFeatures scanFeatures = reader.readScanAnnotation(sReportFile, firstMS1Scan.getScanNo());
			
			HashMap<String, List<Feature>> htPeakToFeatures = null;		
			htPeakToFeatures = AnnotationRowExtraction.createRowIdToFeatureHash(scanFeatures);
			boolean bNeedsConvert = false;
			if( htPeakToFeatures.isEmpty() ) { // no features by row ids? Is this an old project? try the old way
				htPeakToFeatures = AnnotationRowExtraction.createPeakIdToFeatureHash(scanFeatures);
				bNeedsConvert = true;
			}

			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> htParentScanToParentPeaksToSubScan = 
					AnnotationRowExtraction.createParentScanToParentPeaksToSubScanHash(data);
			if( data.getDataHeader().getMethod().getMsType().equals(Method.MS_TYPE_INFUSION) ) {
				AnnotationRowExtraction.updateParentScanToParentPeaksToSubScanHashForDirectInfusion(htParentScanToParentPeaksToSubScan, data) ;
			}	
			
			HashMap<Integer,List<ExtGlycanFeature>> extGlyFeatures = new HashMap<Integer,List<ExtGlycanFeature>>();			
			for (Interval interval : intervals.values()) {
				for (ExtPeak peak : interval.getPeaks()) {
					if( peak.getExpAnnotationId() != expAnnotations.getAnnotationEntryId() ) 
						continue;			
					ArrayList<Scan> precursorScans = AnnotationRowExtraction.getPrecursorScan(data,
							firstMS1Scan.getScanNo(), peak.getSourcePeakId(), htParentScanToParentPeaksToSubScan);
					if( bNeedsConvert ) {
						AnnotationRowExtraction.convertPeakIdsToRowIds(data, scanFeatures, firstMS1Scan.getScanNo(), 
								peak.getSourcePeakId(), precursorScans, htPeakToFeatures );
					}
					// sorting to guarantee same intervals between creation of reports
					
					String sRowId = Feature.getRowId(peak.getSourcePeakId(), peak.getScanId(),scanFeatures.getUsesComplexRowId());
					if( htPeakToFeatures.containsKey(sRowId) ) {    
						List<Feature> alFeatures = htPeakToFeatures.get(sRowId);
						for( Feature feature : alFeatures ) {  
							FeatureSelection fs = Feature.getFeatureSelection(feature, sRowId);
							if( ! fs.getSelected() )
								continue;

							// I don't like doing this because it makes merge Glycan "GWB" specific
							// The annotation sequence and the sequenceGWB should be switched me thinks
							Annotation parentAnnotation = (Annotation) AnnotationRowExtraction.getAnnotation(data, feature.getAnnotationId());
							ExtGlycanFeature extGlyFeature = getNewExtGlycanFeature(peak, parentAnnotation, feature);

							if(extGlyFeatures.get(peak.getSourcePeakId())!=null){
								List<ExtGlycanFeature> extFeatures = extGlyFeatures.get(peak.getSourcePeakId());
								if( ! extFeatures.contains(extGlyFeature) ) {
									extFeatures.add(extGlyFeature);
								}
							}else{
								List<ExtGlycanFeature> extFeatures = new ArrayList<ExtGlycanFeature>();
								extFeatures.add(extGlyFeature);
								extGlyFeatures.put(peak.getSourcePeakId(), extFeatures);
							}
						}
					}

				}
			}
			allExtGlycanFeatures.put(expAnnotations.getAnnotationEntryId(), extGlyFeatures);
		}
	}

	public static ExtGlycanFeature getNewExtGlycanFeature( ExtPeak peak, Annotation parentAnnotation, Feature feature) {
		ExtGlycanFeature extGlyFeature = new ExtGlycanFeature();
		extGlyFeature.setExpAnotationId(peak.getExpAnnotationId());
		extGlyFeature.setAnnotationId(parentAnnotation.getId());
		extGlyFeature.setStringAnnotationId(parentAnnotation.getStringId());
		extGlyFeature.setSequenceGWB(feature.getSequence());
		extGlyFeature.setSequence(parentAnnotation.getSequence());
		extGlyFeature.setSequenceFormat(parentAnnotation.getSequenceFormat());
//		extGlyFeature.setReducingEnd(parentAnnotation.getReducingEnd());
		extGlyFeature.setFeatureDoubleProp(feature.getDoubleProp());
		extGlyFeature.setFeatureIntegerProp(feature.getIntegerProp());
		extGlyFeature.setFeatureStringProp(feature.getStringProp());
		extGlyFeature.setFeatureBooleanProp(feature.getBooleanProp());
		extGlyFeature.setAnnotationDoubleProp(parentAnnotation.getDoubleProp());
		extGlyFeature.setAnnotationIntegerProp(parentAnnotation.getIntegerProp());
		extGlyFeature.setAnnotationStringProp(parentAnnotation.getStringProp());
		extGlyFeature.setAnnotationBooleanProp(parentAnnotation.getBooleanProp());

		extGlyFeature.setIntensity(peak.getIntensity() != null ? peak.getIntensity() : 0.0d);
		extGlyFeature.setCharge(feature.getCharge() != null ? feature.getCharge() : -1);
		extGlyFeature.setFeatureMz(peak.getMz());
		extGlyFeature.setFeatureId(feature.getId());
		extGlyFeature.setPeakId(peak.getExtPeakId());
		
		return extGlyFeature;
	}
		
	public List<ReportRow> generateReportRows(){
		List<ReportRow> rows = new ArrayList<ReportRow>();
		for(Interval interval : intervals.values()){
			ReportRow row = new ReportRow();
			row.setInterval(interval);
			for(ExtPeak peak : interval.getPeaks()){
				if(allExtGlycanFeatures.get(peak.getExpAnnotationId()).get(peak.getSourcePeakId())!=null) {
					List<ExtGlycanFeature> extFeatures = allExtGlycanFeatures.get(peak.getExpAnnotationId()).get(peak.getSourcePeakId());
					for(ExtGlycanFeature extGlyFeature : extFeatures){
						if( ! row.getAnnotations().contains(extGlyFeature) ) {
							row.getAnnotations().add(extGlyFeature);
						}
					}
				}
			}
			rows.add(row);
		}
		return rows;
	}

	public HashMap<Double,Interval> getIntervals() {
		return intervals;
	}

	public void setIntervals(HashMap<Double,Interval> intervals) {
		this.intervals = intervals;
	}




}
