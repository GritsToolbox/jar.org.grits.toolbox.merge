package org.grits.toolbox.merge.om.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.grits.toolbox.ms.om.data.CustomExtraData;

/**
 * The top-level Merge report object. Contains the meta-data and all of the ReportRows
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 * 
 */
@XmlRootElement(name="report")
public class MergeReport {
	@XmlElement(name="settings")
	private MergeSettings settings;
	@XmlElement(name="row")
	private List<ReportRow> rows = new ArrayList<ReportRow>();

    // This list of CED details the additional data for which each Feature may provide a value (in Feature hashmaps)
    @XmlElement(name="annotationCustomExtraData")
    private List<CustomExtraData> m_annotationCustomExtraData = new ArrayList<CustomExtraData>();

    // This list of CED details the additional data for which each Feature may provide a value (in Feature hashmaps)
    @XmlElement(name="featureCustomExtraData")
    private List<CustomExtraData> m_featureCustomExtraData = new ArrayList<CustomExtraData>();

    // This list of CED details the additional data for which each Peak may provide a value (in Peak hashmaps)
    @XmlElement(name="peakCustomExtraData")
    private List<CustomExtraData> m_peakCustomExtraData = new ArrayList<CustomExtraData>();
    
    @XmlTransient
    public List<CustomExtraData> getFeatureCustomExtraData() {
		return m_featureCustomExtraData;
	}   
    public void setFeatureCustomExtraData(List<CustomExtraData> a_featureCustomExtraData) {
		this.m_featureCustomExtraData = a_featureCustomExtraData;
	}    	

    @XmlTransient
    public List<CustomExtraData> getAnnotationCustomExtraData() {
		return m_annotationCustomExtraData;
	}   
    public void setAnnotationCustomExtraData(List<CustomExtraData> a_annotationCustomExtraData) {
		this.m_annotationCustomExtraData = a_annotationCustomExtraData;
	}    	

    @XmlTransient
    public List<CustomExtraData> getPeakCustomExtraData() {
		return m_peakCustomExtraData;
	}   
    public void setPeakCustomExtraData(List<CustomExtraData> a_peakCustomExtraData) {
		this.m_peakCustomExtraData = a_peakCustomExtraData;
	}    	
    
	@XmlTransient
	public List<ReportRow> getRows() {
		return rows;
	}

	public void setRows(List<ReportRow> rows) {
		this.rows = rows;
	}
	 @XmlTransient
	public MergeSettings getSettings() {
		return settings;
	}

	public void setSettings(MergeSettings settings) {
		this.settings = settings;
	}

}
