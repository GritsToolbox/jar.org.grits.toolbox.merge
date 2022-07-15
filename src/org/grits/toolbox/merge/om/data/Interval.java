package org.grits.toolbox.merge.om.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Stores the m/z value that represent the peak(s) from the MS Annotation entries that have been "merged". The ExtPeaks associated
 * with the m/z are added, too.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class Interval {
	private double mz;
	@XmlTransient
	private List<ExtPeak> peaks = new ArrayList<ExtPeak>();
	
	@XmlElement(name="peaks")
	public List<ExtPeak> getPeaks() {
		return peaks;
	}
	public void setPeaks(List<ExtPeak> peaks) {
		this.peaks = peaks;
	}
	public double getMz() {
		return mz;
	}
	@XmlAttribute(name="mz")
	public void setMz(double mz) {
		this.mz = mz;
	}
	

}
