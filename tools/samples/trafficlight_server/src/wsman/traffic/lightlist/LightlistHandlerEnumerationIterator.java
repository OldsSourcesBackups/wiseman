
package wsman.traffic.lightlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;

import org.publicworks.light.model.ui.TrafficLight;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

import wsman.traffic.light.LightHandler;

import com.sun.traffic.light.types.TrafficLightType;
import com.sun.ws.management.framework.transfer.TransferSupport;
import com.sun.ws.management.server.EnumerationItem;
import com.sun.ws.management.server.EnumerationIterator;
import com.sun.ws.management.server.NamespaceMap;

/**
 * The class to be presented by a data source that would like to be
 * enumerated.
 * 
 * Implementations of this class are specific to the data structure being
 * enumerated.
 *  
 * @see EnumerationIterator
 */
public class LightlistHandlerEnumerationIterator implements EnumerationIterator
{
    //Log for logging messages
    private Logger m_log = Logger.getLogger(LightlistHandlerEnumerationIterator.class.getName());

    private boolean m_cancelled = false;
	/*************************** Implementation  Specific **************************/
    public static final org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory addressingFactory = new org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory();
    public static final org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory managementFactory = new org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory();
	/******************************************************************************/
   
    /**
     * Supply the next few elements of the iteration. This is invoked to
     * satisfy a {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull}
     * request. The operation must return within the
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull#getMaxTime timeout}
     * specified in the
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request,
     * otherwise {@link #cancel cancel} will
     * be invoked and the current thread interrupted. When cancelled,
     * the implementation can return the results currently
     * accumulated (in which case no
     * {@link com.sun.ws.management.soap.FaultException Fault} is generated) or it can
     * return {@code null} in which case a
     * {@link com.sun.ws.management.enumeration.TimedOutFault TimedOutFault}
     * is returned.
     *
     * @param db A document builder that can be used to create documents into 
     * which the returned items will be placed. Note that each item must be
     * placed as the root element of a new Document for XPath filtering to work
     * properly.
     *
     * @param context The client context that was specified to
     * {@link com.sun.ws.management.server.EnumerationSupport#enumerate enumerate} is returned.
     *
     * @param startPos The starting position (cursor) for this
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request.
     *
     * @param count The number of items desired in this
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request.
     *
     * @return a List of {@link org.w3c.dom.Element Elements} that will be
     * returned in the
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse PullResponse}.
     */    
//    public List<EnumerationItem> next(final DocumentBuilder db, final Object context, final int startPos, final int count)
	public List<EnumerationItem> next(DocumentBuilder db, Object context, boolean includeItem, boolean includeEPR, int startPos, int count) {
    	/*************************** Implementation ************************************/
    	// Create a set of elements to be returned
        // In this case each will be an EPR starting in the
        // list from startPos totalling count items
    	Map<String,TrafficLight> lights=(Map<String,TrafficLight>)context;
        int returnCount = Math.min(count, lights.size() - startPos);
        List<EnumerationItem> items = new ArrayList(returnCount);
        Set<String> keysSet = lights.keySet();
        Object[] keys = keysSet.toArray();
        for (int i = 0; i < returnCount && !m_cancelled; i++)
        {
            String lightName = keys[startPos + i].toString();
            TrafficLight light = lights.get(lightName);
            Map<String, String> selectors = new HashMap<String, String>();
            selectors.put("name", light.getName());
            try
            {
            	EndpointReferenceType epr = TransferSupport.createEpr(null, "wsman:traffic/light", selectors);
            	
            	// Make a traffic light type to support epr or element enum
            	Element lightElement = LightHandler.createLightElement(LightHandler.createLightType(light));
           
            	EnumerationItem item = new EnumerationItem(lightElement,epr);
                items.add(item);
            }
            catch (JAXBException e)
            {
                throw new RuntimeException(e);
            }

        }
        return items;
    	/******************************************************************************/
    }
    /**
     * Indicates if there are more elements remaining in the iteration.
     *
     * @param context The client context that was specified to
     * {@link com.sun.ws.management.server.EnumerationSupport#enumerate enumerate} is returned.
     *
     * @param startPos The starting position (cursor) for this
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull} request.
     *
     * @return {@code true} if there are more elements in the iteration,
     * {@code false} otherwise.
     */
	public boolean hasNext(final Object context, final int startPos)
    {
    	/*************************** Implementation ************************************/
    	Map<String,TrafficLight> lights=(Map<String,TrafficLight>)context;
       if (startPos >= lights.size())
        {
            return false;
        }
        else
        {
            return true;
        }
   	/******************************************************************************/
    }
    /**
     * Invoked when a {@link #next next} call exceeds the
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull#getMaxTime timeout}
     * specified in the
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull}
     * request. An implementation is expected to set a flag that
     * causes the currently-executing {@link #next next} operation to return
     * gracefully.
     *
     * @param context The client context that was specified to
     * {@link com.sun.ws.management.server.EnumerationSupport#enumerate enumerate} is returned.
     */
    public void cancel(final Object context)
    {
        m_cancelled = true;
    }
	public int estimateTotalItems(Object context) {
	   	Map<String,TrafficLight> lights=(Map<String,TrafficLight>)context;
		return lights.size();
	}
	public NamespaceMap getNamespaces() {
		return null;
	}
//	public List<EnumerationItem> next(DocumentBuilder db, Object context, boolean includeItem, boolean includeEPR, int startPos, int count) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
