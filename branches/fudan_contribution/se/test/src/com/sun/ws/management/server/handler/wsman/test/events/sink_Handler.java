package com.sun.ws.management.server.handler.wsman.test.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.ws.management.Management;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.framework.eventing.WSEventingSinkHandler;
import com.sun.ws.management.server.ContextListener;
import com.sun.ws.management.server.HandlerContext;
import com.sun.ws.management.server.WSEnumerationSupport;
import com.sun.ws.management.server.WSEventingSupport;
import com.sun.ws.management.server.message.WSManagementRequest;
import com.sun.ws.management.server.message.WSManagementResponse;

public class sink_Handler extends WSEventingSinkHandler {
	
	private static final Logger LOG = Logger.getLogger(sink_Handler.class.getName());
	
	static final class SubscriptionHandler implements ContextListener {
		
		private final List<UUID> subscribers = new ArrayList<UUID>();

		public void contextBound(HandlerContext requestContext, UUID context) {
			LOG.info("Context bound: " + context.toString());
			synchronized (subscribers) {
				subscribers.add(context);
			}
		}

		public void contextUnbound(HandlerContext requestContext, UUID context) {
			LOG.info("Context unbound: " + context.toString());
			synchronized (subscribers) {
				subscribers.remove(context);
			}
		}
		
		void forwardEvent(final WSManagementRequest request) {
			
			// Forward the message as an event on to any subscribers.
			final UUID[] list;
			synchronized (subscribers) {
				list = new UUID[subscribers.size()];
				subscribers.toArray(list);
			}
			LOG.info("Forwarding event to sink subscribers: " + list.length);
			for (int i = 0; i < list.length; i++) {
				final UUID uuid = list[i];
				try {
					LOG.info("Calling sendEvent() for UUID: " + uuid.toString());
					WSEventingSupport.sendEvent(uuid, request.getPayload(null));
				} catch (Exception e) {
					LOG.log(Level.SEVERE, "Unexpected exception: ", e);
					// Ignore the error. 
					// WSEventingSupport will remove the subscriber automatically.
				}
			}
		}
	}
	
	// Needs to be static or we may lose our subscriptions
	static final SubscriptionHandler subscriptionHandler = new SubscriptionHandler();

	public void handleEvents(final String resource,
			final HandlerContext context,
			final WSManagementRequest request,
			final WSManagementResponse response) throws Exception {

		try {
			final String action = request.getActionURI().toString();
			LOG.info("Event action: " + action);
			if (Eventing.SUBSCRIBE_ACTION_URI.equals(action)) {
				response.setAction(Eventing.SUBSCRIBE_RESPONSE_URI);
				WSEventingSupport.subscribe(context,
						request, response, false, 1024, subscriptionHandler, null);
			} else if (Eventing.RENEW_ACTION_URI.equals(action)) {
				response.setAction(Eventing.RENEW_RESPONSE_URI);
				WSEventingSupport.renew(context, request, response);
			} else if (Eventing.UNSUBSCRIBE_ACTION_URI.equals(action)) {
				response.setAction(Eventing.UNSUBSCRIBE_RESPONSE_URI);
				WSEventingSupport.unsubscribe(context, request, response);
			} else if (Enumeration.PULL_ACTION_URI.equals(action)) {
				response.setAction(Enumeration.PULL_RESPONSE_URI);
	            WSEnumerationSupport.pull(context, request, response);
	        } else if (Enumeration.RELEASE_ACTION_URI.equals(action)) {
	        	response.setAction(Enumeration.RELEASE_RESPONSE_URI);
	            WSEnumerationSupport.release(context, request, response);
	        } else {
	        	LOG.info("Event received.");
				// Treat everything else as an event. Forward it on to any subscribers.
				subscriptionHandler.forwardEvent(request);
				LOG.info("Event forwarded.");
		        if (request.isAckRequested() == false) {
		        	// TODO: Don't send the SOAP response.
		        } else {
		        	response.setAction(Management.ACK_URI);
		        	// MessageID, RelatesTo & To are set by the Wiseman servlet or JAX-WS
		        }
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Unexpected exception: ", e);
			// SOAP Fault message will be automatically built by the Wiseman framework.
			throw e;
		}
	}
}