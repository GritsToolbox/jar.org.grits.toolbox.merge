package org.grits.toolbox.merge.om.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Stores the settings for determining the m/z interval and the list of MS Annotation entries to be merged.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class MergeSettings {
	private double tolerance;
	private String toleranceType;
	
	@XmlElement(name="experimentList")
	private List<ExperimentAnnotation> experimentList = null;
	
	public MergeSettings(){}
	
	public MergeSettings(double tolerance){
		this.tolerance = tolerance;
	}

	public double getTolerance() {
		return tolerance;
	}
	
	@XmlAttribute(name="tolerance")
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public String getToleranceType() {
		return toleranceType;
	}

	 @XmlAttribute(name="toleranceType")
	public void setToleranceType(String toleranceType) {
		this.toleranceType = toleranceType;
	}
	
	@XmlTransient
	public List<ExperimentAnnotation> getExperimentList() {
		return experimentList;
	}
	
	public void setExperimentList(List<ExperimentAnnotation> experimentList) {
		this.experimentList = experimentList;
	}

}
