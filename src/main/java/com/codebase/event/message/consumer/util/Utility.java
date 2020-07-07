package com.codebase.event.message.consumer.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codebase.event.message.consumer.model.EventMessage;
import com.codebase.event.message.consumer.model.EventRequest;

@Component
public class Utility {
	
	public EventRequest setEventRequest() {
		EventRequest eventRequest = new EventRequest();
		eventRequest.setEventID("101");
		eventRequest.setEventSubType("Eagle");
		eventRequest.setEventType("eagle-fund");
		eventRequest.setEventStartDate("06/24/2019 20:14:52");
		eventRequest.setEventEndDate("06/24/2019 20:14:52");
		eventRequest.setEventTimeZone("PST");
		eventRequest.setEventStatus("success");
		eventRequest.setEventCategory("");
		eventRequest.setEventCode("EVTUSB001");
		eventRequest.setEventSourceIP("success");
		
		List<EventMessage> eventMessageList = fetchEventMessage();
		eventRequest.setEventMessages(eventMessageList);;
		
		return eventRequest;
		
		
	}
	
	private List<EventMessage> fetchEventMessage() {
		List<EventMessage> eventMessageList = new ArrayList<EventMessage>();
		for(int i =0;  i<20; i++) {
			double d = Math.random();
		EventMessage eventMessage = new EventMessage();
		eventMessage.put("IMPORT_NAME" , "NUVEEN SEC YIELD CHECK UPLOADER"+d);
		eventMessage.put("FEED_NAME" , "SEC YIELD CHECK UPLOADER"+d);
		eventMessage.put("COMPLETION_DATETIME" , "06/24/2019 20:14:52 AM");
		eventMessage.put("FILE_LOCATION" , "/temp/");
		eventMessage.put("FILE_NAME" , "temp");
		eventMessage.put("SEQUENCE" , "loadIntoEagle");
		eventMessage.put("ENENT_ID" , "2020020369878to2020_2315");
		eventMessageList.add(eventMessage);
		}
		return eventMessageList;
		
	}

}
