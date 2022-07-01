import java.util.Properties;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.boomi.execution.ExecutionUtil;

List<String> partnersList = new ArrayList<String>();

for( int i = 0; i < dataContext.getDataCount(); i++ ) {
    InputStream is = dataContext.getStream(i);
    Properties props = dataContext.getProperties(i);
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    String line = reader.readLine();
    String refmes = line.substring(277,287);
    partnersList.add(refmes);
    props.setProperty("document.dynamic.userdefined.DDP_Partner",refmes);
    
    dataContext.storeStream(dataContext.getStream(i), props);
}

List<String> uniquePartnersList = partnersList.stream().distinct().collect(Collectors.toList());
String strList = "";
String delimiter = "\",\"";
for (String partner : uniquePartnersList) {
    strList += strList.equals("") ? partner : delimiter + partner;
}
strList = "[\""+strList+"\"]";
ExecutionUtil.setDynamicProcessProperty("DPP_Partners", strList, false);
