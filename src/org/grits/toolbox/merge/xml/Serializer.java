package org.grits.toolbox.merge.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.grits.toolbox.merge.om.data.MergeReport;

public class Serializer {
	
	public void serialize(MergeReport report,String path){
		try{
			JAXBContext jaxbContextAnn = JAXBContext.newInstance(MergeReport.class);		    
		    Marshaller jaxbMarshallerAnn = jaxbContextAnn.createMarshaller();
		    jaxbMarshallerAnn.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    File output = new File(path);
		    jaxbMarshallerAnn.marshal(report,output);
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}

}
