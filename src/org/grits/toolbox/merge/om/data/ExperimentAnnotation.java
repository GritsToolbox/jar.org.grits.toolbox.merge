package org.grits.toolbox.merge.om.data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Stores the information about an MS Annotation entry that is needed for the merge report.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class ExperimentAnnotation {
	
	private Integer annotationEntryId = null;
	private String annotationFileArchive = null;
	private String annotationDisplayName = null;
	private String annotationShortName = null;
	
	public Integer getAnnotationEntryId() {
		return annotationEntryId;
	}
	@XmlAttribute(name="entryId")
	public void setAnnotationEntryId(Integer annotationEntryId) {
		this.annotationEntryId = annotationEntryId;
	}
	
	public String getAnnotationDisplayName() {
		return annotationDisplayName;
	}
	
	@XmlAttribute(name="name")
	public void setAnnotationDisplayName(String annotationDisplayName) {
		this.annotationDisplayName = annotationDisplayName;
	}
	public String getAnnotationShortName() {
		return annotationShortName;
	}
	
	@XmlAttribute(name="shortName")
	public void setAnnotationShortName(String annotationShortName) {
		this.annotationShortName = annotationShortName;
	}
	public String getAnnotationFileArchive() {
		return annotationFileArchive;
	}
	
	@XmlAttribute(name="annotationFileArchive")
	public void setAnnotationFileArchive(String annotationFileArchive) {
		this.annotationFileArchive = annotationFileArchive;
	}
}
