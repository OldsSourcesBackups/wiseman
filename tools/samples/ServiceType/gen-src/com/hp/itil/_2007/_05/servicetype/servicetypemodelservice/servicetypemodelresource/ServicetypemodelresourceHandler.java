
package com.hp.itil._2007._05.servicetype.servicetypemodelservice.servicetypemodelresource;

import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.Management;
import com.sun.ws.management.framework.handlers.ResourceHandler;
import com.sun.ws.management.server.EnumerationSupport;
import com.sun.ws.management.server.HandlerContext;

import java.util.logging.Logger;

/**
 * ServicetypemodelresourceHandler delegate is responsible for processing enumeration actions.
 *
 * @GENERATED
 */
public class ServicetypemodelresourceHandler extends ResourceHandler {

    public static final String resourceURI = "http://www.hp.com/itil/2007/05/ServiceType/ServiceTypeModelService/ServiceTypeModelResource";
       
    // Log for logging messages
    @SuppressWarnings("unused")
    private final Logger log;

    public ServicetypemodelresourceHandler() {
        super();
        
        // Initialize our member variables
        log = Logger.getLogger(ServicetypemodelresourceHandler.class.getName());
        try {
            // Register the IteratorFactory with EnumerationSupport
            EnumerationSupport.registerIteratorFactory("http://www.hp.com/itil/2007/05/ServiceType/ServiceTypeModelService/ServiceTypeModelResource",
                                                       new ServicetypemodelresourceIteratorFactory("http://www.hp.com/itil/2007/05/ServiceType/ServiceTypeModelService/ServiceTypeModelResource"));
        } catch (Exception e) {
            throw new InternalErrorFault(e);
        }
    }
    public void Get(HandlerContext context, Management request, Management response) {
        // TODO: Implement Get here and remove the following call to super
        super.get(context, request, response);
    }

    public void RenewSubscriptionOp(HandlerContext context, Eventing request, Eventing response) {
        // TODO: For subscribe:
        //       Call EventSupport.subscribe() & save the UUID returned (use ContextListener to detect subscribe/unsubscribes)
        //       Start sending events to EventSupport.sendEvent(uuid, event)
        
        // TODO: For unsubscribe:
        //       Call EventSupport.unsubscribe()
        //       Stop sending events for this UUID
        super.renewSubscription(context, request, response);
    } 

    public void ReleaseOp(HandlerContext context, Enumeration request, Enumeration response) {
        super.release(context, request, response);
    }    
    public void Delete(HandlerContext context, Management request, Management response) {
        // TODO: Implement Delete here and remove the following call to super
        super.delete(context, request, response);
    }

    public void SubscribeOp(HandlerContext context, Eventing request, Eventing response) {
        // TODO: For subscribe:
        //       Call EventSupport.subscribe() & save the UUID returned (use ContextListener to detect subscribe/unsubscribes)
        //       Start sending events to EventSupport.sendEvent(uuid, event)
        
        // TODO: For unsubscribe:
        //       Call EventSupport.unsubscribe()
        //       Stop sending events for this UUID
        super.subscribe(context, request, response);
    } 

    public void EnumerateOp(HandlerContext context, Enumeration request, Enumeration response) {
        super.enumerate(context, request, response);
    }    
    public void Create(HandlerContext context, Management request, Management response) {
        // TODO: Implement Create here and remove the following call to super
        super.create(context, request, response);
    }

    public void UnsubscribeOp(HandlerContext context, Eventing request, Eventing response) {
        // TODO: For subscribe:
        //       Call EventSupport.subscribe() & save the UUID returned (use ContextListener to detect subscribe/unsubscribes)
        //       Start sending events to EventSupport.sendEvent(uuid, event)
        
        // TODO: For unsubscribe:
        //       Call EventSupport.unsubscribe()
        //       Stop sending events for this UUID
        super.unsubscribe(context, request, response);
    } 
    public void Put(HandlerContext context, Management request, Management response) {
        // TODO: Implement Put here and remove the following call to super
        super.put(context, request, response);
    }

    public void PullOp(HandlerContext context, Enumeration request, Enumeration response) {
        super.pull(context, request, response);
    }    

    public void GetStatusOp(HandlerContext context, Enumeration request, Enumeration response) {
        super.getStatus(context, request, response);
    }    

    public void RenewOp(HandlerContext context, Enumeration request, Enumeration response) {
        super.renew(context, request, response);
    }    

    public void GetSubscriptionStatusOp(HandlerContext context, Eventing request, Eventing response) {
        // TODO: For subscribe:
        //       Call EventSupport.subscribe() & save the UUID returned (use ContextListener to detect subscribe/unsubscribes)
        //       Start sending events to EventSupport.sendEvent(uuid, event)
        
        // TODO: For unsubscribe:
        //       Call EventSupport.unsubscribe()
        //       Stop sending events for this UUID
        super.getSubscriptionStatus(context, request, response);
    } 
}