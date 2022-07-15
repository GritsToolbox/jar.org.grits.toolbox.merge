package org.grits.toolbox.merge.om.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Stores the ExtGlycanFeatures info for each Interval that represent a row in a merge report.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class ReportRow {
	@XmlElement(name="interval")
	private Interval interval;
	private List<ExtGlycanFeature> annotations = new ArrayList<ExtGlycanFeature>();
	
	@XmlElement(name="annotation")
	public List<ExtGlycanFeature> getAnnotations() {
		return annotations;
	}
	
	public void setAnnotations(List<ExtGlycanFeature> annotations) {
		this.annotations = annotations;
	}
	@XmlTransient
	public Interval getInterval() {
		return interval;
	}
	public void setInterval(Interval interval) {
		this.interval = interval;
	}

}
